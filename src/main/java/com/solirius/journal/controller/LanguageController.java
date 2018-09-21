package com.solirius.journal.controller;

import com.solirius.journal.Service.LanguageService;
import com.solirius.journal.model.Language;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    Gson gson;

    // GETs language
    @GetMapping(value = "{languagePath}")
    public ResponseEntity getLanguage(@PathVariable String languagePath) {
        Optional<Language> fetchedLanguage;
        try{
            int languageId = Integer.parseInt(languagePath);
            fetchedLanguage = languageService.getLanguage(languageId);
            if(!fetchedLanguage.isPresent()){
                return new ResponseEntity<>(new Message("Language with ID '" + languageId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedLanguage.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedLanguage = languageService.getLanguage(languagePath);
            if(!fetchedLanguage.isPresent()){
                return new ResponseEntity<>(new Message("Language with name '" + languagePath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedLanguage.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all languages
    @GetMapping(value = "")
    public ResponseEntity getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        if(languages.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get language list, language list is empty"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(languages,HttpStatus.ACCEPTED);
    }

    // POSTs new language
    @PostMapping
    public ResponseEntity postLanguage(@RequestBody Language reqBody) {

        if(languageService.getLanguage(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, language with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        Language newLanguage = languageService.createLanguage(reqBody);

        return new ResponseEntity<>(newLanguage,HttpStatus.ACCEPTED);
    }

    // Updates language
    @PutMapping(value = "{languagePath}")
    public ResponseEntity putLanguage(@PathVariable String languagePath, @RequestBody Language reqBody) {
        Optional<Language> languageToUpdate;
        try{
            int languageId = Integer.parseInt(languagePath);
            languageToUpdate = languageService.getLanguage(languageId);
            if(!languageToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, language with ID '" + languageId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            languageToUpdate = languageService.getLanguage(languagePath);
            if(!languageToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, language with name '" + languagePath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Language newLanguage = languageToUpdate.get();
        if(reqBody.getName() != null) {
            newLanguage.setName(reqBody.getName());
        }

        if(reqBody.getDescription() != null) {
            newLanguage.setDescription(reqBody.getDescription());
        }

        Language returnedLanguage = languageService.createLanguage(newLanguage);
        return new ResponseEntity<>(returnedLanguage, HttpStatus.ACCEPTED);
    }

    // DELs language
    @DeleteMapping(value = "{languagePath}")
    public ResponseEntity delLanguage(@PathVariable String languagePath) {
        Optional<Language> prevLanguage;
        try{
            int languageId = Integer.parseInt(languagePath);
            prevLanguage = languageService.getLanguage(languageId);

        } catch(NumberFormatException nfe){
            prevLanguage = languageService.getLanguage(languagePath);
        }
        if(!prevLanguage.isPresent()){
            return new ResponseEntity<>(new Message("Language '" + languagePath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Language languageToDelete = prevLanguage.get();
        gson = new Gson();
        Language deepCopy = gson.fromJson(gson.toJson(languageToDelete), Language.class);
        languageService.destroyLanguage(languageToDelete);
        return new ResponseEntity<>(deepCopy, HttpStatus.ACCEPTED);
    }
}
