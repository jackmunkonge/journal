package com.solirius.journal.controller;

import com.solirius.journal.Service.LanguageService;
import com.solirius.journal.controller.resource.LanguageCreateRequest;
import com.solirius.journal.domain.Language;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    // GETs language
    @GetMapping(value = "{languagePath}")
    public ResponseEntity getLanguage(@PathVariable String languagePath){
        Optional<Language> fetchedLanguage;
        try{
            int languageId = Integer.parseInt(languagePath);
            fetchedLanguage = languageService.getLanguage(languageId);
            if(!fetchedLanguage.isPresent()){
                System.out.println("Language not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity(fetchedLanguage.get(),HttpStatus.ACCEPTED);

        } catch(NumberFormatException nfe){
            fetchedLanguage = languageService.getLanguage(languagePath);
            if(!fetchedLanguage.isPresent()){
                System.out.println("Language not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity(fetchedLanguage.get(),HttpStatus.ACCEPTED);

        }
    }

    // GETs all languages
    @GetMapping(value = "")
    public ResponseEntity getAllLanguages(Model model) {
        List<Language> languages = languageService.getAllLanguages();
        if(languages.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("languages", languages);

        return new ResponseEntity(languages,HttpStatus.ACCEPTED);
    }

    // POSTs new language
    @PostMapping
    public ResponseEntity postLanguage(@Valid @RequestBody LanguageCreateRequest request){
        Language savedLanguage = new Language();
        savedLanguage.setName(request.getName());

        Language newLanguage = languageService.createLanguage(savedLanguage);

        return new ResponseEntity(newLanguage,HttpStatus.ACCEPTED);
    }

    // Updates language
    @PutMapping(value = "{languagePath}")
    public ResponseEntity putLanguage(@PathVariable String languagePath,@RequestBody LanguageCreateRequest languageReq) throws NotFoundException {
        Optional<Language> langToUpdate;
        try{
            int languageId = Integer.parseInt(languagePath);
            langToUpdate = languageService.getLanguage(languageId);
        } catch(NumberFormatException nfe){
            langToUpdate = languageService.getLanguage(languagePath);
        }
        if(!langToUpdate.isPresent()){
            System.out.println("Language not present.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Language newLanguage = langToUpdate.get();
        newLanguage.setName(languageReq.getName());

        Language returned = languageService.createLanguage(newLanguage);
        return new ResponseEntity(returned,HttpStatus.ACCEPTED);
    }

    // DELs language
    @DeleteMapping(value = "{languagePath}")
    public ResponseEntity delLanguage(@PathVariable String languagePath){
        Optional<Language> grabbedLanguage;
        try{
            int languageId = Integer.parseInt(languagePath);
            grabbedLanguage = languageService.getLanguage(languageId);

        } catch(NumberFormatException nfe){
            grabbedLanguage = languageService.getLanguage(languagePath);
        }
        if(!grabbedLanguage.isPresent()){
            System.out.println("Language not present.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Language langToDelete = grabbedLanguage.get();
        Language deleted = languageService.destroyLanguage(langToDelete);
        return new ResponseEntity(deleted,HttpStatus.ACCEPTED);
    }

}
