package com.solirius.journal.controller;

import com.solirius.journal.Service.FrameworkService;
import com.solirius.journal.Service.TagService;
import com.solirius.journal.model.Framework;
import com.solirius.journal.repository.FrameworkRepository;
import com.solirius.journal.repository.TagRepository;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/frameworks")
public class FrameworkController {


    private TagService tagService;

    private FrameworkRepository frameworkRepository;

    @Autowired
    public FrameworkController(TagService tagService, FrameworkRepository frameworkRepository) {
        this.frameworkRepository = frameworkRepository;
        this.tagService = new TagService(frameworkRepository);
    }

    Gson gson;

    // GETs framework
    @GetMapping(value = "{frameworkPath}")
    public ResponseEntity getFramework(@PathVariable String frameworkPath) {
        Optional fetchedFrame;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            fetchedFrame = tagService.getTag(frameworkId);
            if(!fetchedFrame.isPresent()){
                return new ResponseEntity<>(new Message("Framework with ID '" + frameworkId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity<>(fetchedFrame.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedFrame = tagService.getTag(frameworkPath);
            if(!fetchedFrame.isPresent()){
                return new ResponseEntity<>(new Message("Framework with name '" + frameworkPath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity<>(fetchedFrame.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all frameworks
    @GetMapping(value = "")
    public ResponseEntity getAllFrameworks() {
        List frameworks = tagService.getAllTags();
        if(frameworks.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get framework list, framework list is empty"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(frameworks,HttpStatus.ACCEPTED);
    }

    // POSTs new framework
    @PostMapping
    public ResponseEntity postFramework(@RequestBody Framework reqBody) {

        if(tagService.getTag(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, framework with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        Object newFramework = tagService.createTag(reqBody);

        return new ResponseEntity<>(newFramework,HttpStatus.ACCEPTED);
    }

    // Updates framework
    @PutMapping(value = "{frameworkPath}")
    public ResponseEntity putFramework(@PathVariable String frameworkPath, @RequestBody Framework reqBody) {
        Optional<Framework> frameworkToUpdate;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            frameworkToUpdate = tagService.getTag(frameworkId);
            if(!frameworkToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, framework with ID '" + frameworkId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            frameworkToUpdate = tagService.getTag(frameworkPath);
            if(!frameworkToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, framework with name '" + frameworkPath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Framework newFramework = frameworkToUpdate.get();
        if(reqBody.getName() != null) {
            newFramework.setName(reqBody.getName());
        }

        if(reqBody.getDescription() != null) {
            newFramework.setDescription(reqBody.getDescription());
        }

        Object returnedFramework = tagService.createTag(newFramework);
        return new ResponseEntity<>(returnedFramework, HttpStatus.ACCEPTED);
    }

    // DELs framework
    @DeleteMapping(value = "{frameworkPath}")
    public ResponseEntity delFramework(@PathVariable String frameworkPath) {
        Optional<Framework> prevFramework;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            prevFramework = tagService.getTag(frameworkId);

        } catch(NumberFormatException nfe){
            prevFramework = tagService.getTag(frameworkPath);
        }
        if(!prevFramework.isPresent()){
            return new ResponseEntity<>(new Message("Framework '" + frameworkPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Framework frameworkToDelete = prevFramework.get();
        gson = new Gson();
        Framework deepCopy = gson.fromJson(gson.toJson(frameworkToDelete), Framework.class);
        tagService.destroyTag(frameworkToDelete);
        return new ResponseEntity<>(deepCopy,HttpStatus.ACCEPTED);
    }
}
