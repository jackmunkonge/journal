package com.solirius.journal.controller;

import com.solirius.journal.Service.FrameworkService;
import com.solirius.journal.Service.LanguageService;
import com.solirius.journal.controller.createrequest.FrameworkCreateRequest;
import com.solirius.journal.model.Framework;
import com.solirius.journal.model.Language;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/frameworks")
public class FrameworkController {

    @Autowired
    private FrameworkService frameworkService;

    @Autowired
    private LanguageService languageService;

    // GETs framework
    @GetMapping(value = "{frameworkPath}")
    public ResponseEntity getFramework(@PathVariable String frameworkPath){
        Optional<Framework> fetchedFrame;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            fetchedFrame = frameworkService.getFramework(frameworkId);
            if(!fetchedFrame.isPresent()){
                return new ResponseEntity<>(new Message("Framework with ID '" + frameworkId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity<>(fetchedFrame.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedFrame = frameworkService.getFramework(frameworkPath);
            if(!fetchedFrame.isPresent()){
                return new ResponseEntity<>(new Message("Framework with name '" + frameworkPath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity<>(fetchedFrame.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all frameworks
    @GetMapping(value = "")
    public ResponseEntity getAllFrameworks(Model model) {
        List<Framework> frameworks = frameworkService.getAllFrameworks();
        if(frameworks.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get framework list, framework list is empty"),HttpStatus.NOT_FOUND);
        }

        model.addAttribute("frameworks", frameworks);

        return new ResponseEntity<>(frameworks,HttpStatus.ACCEPTED);
    }

    // GETs framework list by language
    @GetMapping(value = "/language/{languagePath}")
    public ResponseEntity getByLanguage(@PathVariable String languagePath) {
        Optional<Language> reqLanguage;
        try{
            int languageId = Integer.parseInt(languagePath);
            reqLanguage = languageService.getLanguage(languageId);

        } catch(NumberFormatException nfe){
            reqLanguage = languageService.getLanguage(languagePath);
        }

        if(!reqLanguage.isPresent()){
            return new ResponseEntity<>(new Message("Frameworks by language '" + languagePath + "' do not exist"),HttpStatus.NOT_FOUND);
        }

        List<Framework> langResources = frameworkService.getAllFrameworks(reqLanguage.get());
        return new ResponseEntity<>(langResources,HttpStatus.ACCEPTED);
    }

    // POSTs new framework
    @PostMapping
    public ResponseEntity postFramework(@RequestBody FrameworkCreateRequest request){
        Framework savedFramework = new Framework();
        savedFramework.setName(request.getName());

        Optional<Language> lang = languageService.getLanguage(request.getLanguageName());
        if(!lang.isPresent()){
            return new ResponseEntity<>(new Message("Cannot create framework, language with name '" + request.getLanguageName() + "' does not exist"),HttpStatus.BAD_REQUEST);
        }
        savedFramework.setLanguage(lang.get());
        Framework newFramework = frameworkService.createFramework(savedFramework);

        return new ResponseEntity<>(newFramework,HttpStatus.ACCEPTED);
    }

    // Updates framework
    @PutMapping(value = "{frameworkPath}")
    public ResponseEntity putFramework(@PathVariable String frameworkPath,@RequestBody FrameworkCreateRequest frameworkReq) throws NotFoundException {
        Optional<Framework> frameToUpdate;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            frameToUpdate = frameworkService.getFramework(frameworkId);
        } catch(NumberFormatException nfe){
            frameToUpdate = frameworkService.getFramework(frameworkPath);
        }
        if(!frameToUpdate.isPresent()){
            return new ResponseEntity<>(new Message("Cannot update, framework '" + frameworkPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Framework newFrame = frameToUpdate.get();
        if (frameworkReq.getName() != null) {
            newFrame.setName(frameworkReq.getName());
        }
        if(frameworkReq.getLanguageName() != null){
            Optional<Language> newLanguage = languageService.getLanguage(frameworkReq.getLanguageName());
            if(!newLanguage.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, language by name '" + frameworkReq.getLanguageName() + "' does not exist"),HttpStatus.NOT_FOUND);
            } else {
                newFrame.setLanguage(newLanguage.get());
            }
        } else {
            newFrame.setLanguage(null);
        }

        Framework returned = frameworkService.createFramework(newFrame);
        return new ResponseEntity<>(returned,HttpStatus.ACCEPTED);
    }

    // DELs framework
    @DeleteMapping(value = "{frameworkPath}")
    public ResponseEntity delFramework(@PathVariable String frameworkPath){
        Optional<Framework> grabbedFrame;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            grabbedFrame = frameworkService.getFramework(frameworkId);

        } catch(NumberFormatException nfe){
            grabbedFrame = frameworkService.getFramework(frameworkPath);
        }
        if(!grabbedFrame.isPresent()){
            return new ResponseEntity<>(new Message("Framework '" + frameworkPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Framework frameToDelete = grabbedFrame.get();
        Framework deleted = frameworkService.destroyFramework(frameToDelete);
        return new ResponseEntity<>(deleted,HttpStatus.ACCEPTED);
    }
}
