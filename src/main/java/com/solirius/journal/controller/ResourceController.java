package com.solirius.journal.controller;

import com.solirius.journal.Service.FrameworkService;
import com.solirius.journal.Service.LanguageService;
import com.solirius.journal.Service.ResourceService;
import com.solirius.journal.controller.resource.ResourceCreateRequest;
import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.domain.Resource;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private FrameworkService frameworkService;

    @Autowired
    private LanguageService languageService;

    // GETs resource
    @GetMapping(value = "{resource}")
    public ResponseEntity getResource(@PathVariable String resource){
        Optional<Resource> testResource;
        try{
            int resourceId = Integer.parseInt(resource);
            testResource = resourceService.getResource(resourceId);
            if(!testResource.isPresent()){
                System.out.println("Resource not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(testResource.get(),HttpStatus.ACCEPTED);
            }
        } catch(NumberFormatException nfe){
            testResource = resourceService.getResource(resource);
            if(!testResource.isPresent()){
                System.out.println("Resource not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(testResource.get(),HttpStatus.ACCEPTED);
            }
        }
    }

    // GETs all resources
    @GetMapping(value = "")
    public ResponseEntity getResources(Model model) {
        List<Resource> resources = resourceService.getAllResources();
        if(resources.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("resources", resources);

        return new ResponseEntity(resources,HttpStatus.ACCEPTED);
    }

    // GETs resource list by language
    @GetMapping(value = "/language/{languagePath}")
    public ResponseEntity getByLanguage(@PathVariable String languagePath) {
        Optional<Language> reqLanguage;
        try{
            int languageId = Integer.parseInt(languagePath);
            reqLanguage = languageService.getLanguage(languageId);
            if(!reqLanguage.isPresent()){
                System.out.println("Language not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                List<Resource> langResources = resourceService.getAllResources(reqLanguage.get());
                return new ResponseEntity(langResources,HttpStatus.ACCEPTED);
            }
        } catch(NumberFormatException nfe){
            reqLanguage = languageService.getLanguage(languagePath);
            if(!reqLanguage.isPresent()){
                System.out.println("Language not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                List<Resource> langResources = resourceService.getAllResources(reqLanguage.get());
                return new ResponseEntity(langResources,HttpStatus.ACCEPTED);
            }
        }
    }

    // GETs resource list by framework
    @GetMapping(value = "/framework/{frameworkPath}")
    public ResponseEntity getByFramework(@PathVariable String frameworkPath) {
        Optional<Framework> reqFramework;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            reqFramework = frameworkService.getFramework(frameworkId);
            if(!reqFramework.isPresent()){
                System.out.println("Framework not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                List<Resource> frameResources = resourceService.getAllResources(reqFramework.get());
                return new ResponseEntity(frameResources,HttpStatus.ACCEPTED);
            }
        } catch(NumberFormatException nfe){
            reqFramework = frameworkService.getFramework(frameworkPath);
            if(!reqFramework.isPresent()){
                System.out.println("Framework not present.");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                List<Resource> frameResources = resourceService.getAllResources(reqFramework.get());
                return new ResponseEntity(frameResources,HttpStatus.ACCEPTED);
            }
        }
    }

    // POSTs resource
    @PostMapping
    public ResponseEntity postResource(@RequestBody ResourceCreateRequest request){
        Resource savedResource = new Resource();

        if(resourceService.getResource(request.getName()).isPresent()){
            return new ResponseEntity("Resource already exists",HttpStatus.BAD_REQUEST);
        }

        savedResource.setName(request.getName());
        savedResource.setUrl(request.getUrl());

        if(request.getFrameworkName() != null && !request.getFrameworkName().isEmpty()){
            Optional<Framework> frame = frameworkService.getFramework(request.getFrameworkName());
            if(!frame.isPresent()){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            savedResource.setFramework(frame.get());
        }

        if(request.getLanguageName() != null && !request.getLanguageName().isEmpty()){
            Optional<Language> lang = languageService.getLanguage(request.getLanguageName());
            if(!lang.isPresent()){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            savedResource.setLanguage(lang.get());
        }

        Resource resource = resourceService.createResource(savedResource);

        return new ResponseEntity(resource,HttpStatus.ACCEPTED);
    }

    // Updates resource
    @PutMapping(value = "{resourcePath}")
    public ResponseEntity putResource(@PathVariable String resourcePath,@RequestBody ResourceCreateRequest resource) throws NotFoundException {
        Optional<Resource> resToUpdate;
        try{
            int resourceId = Integer.parseInt(resourcePath);
            resToUpdate = resourceService.getResource(resourceId);
        } catch(NumberFormatException nfe){
            resToUpdate = resourceService.getResource(resourcePath);
        }
        if(!resToUpdate.isPresent()){
            System.out.println("Resource not present.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }


        Resource newres = resToUpdate.get();
        if (resource.getName() != null) {
           newres.setName(resource.getName());
        }
        if(resource.getUrl() != null) {
            newres.setUrl(resource.getUrl());
        }
        if(resource.getLanguageName() != null){
            Language newLanguage = languageService.getLanguage(resource.getLanguageName())
                    .orElseThrow(()-> new EntityNotFoundException());
            newres.setLanguage(newLanguage);
        } else {
            newres.setLanguage(null);
        }

        if(resource.getFrameworkName() != null){
            Framework newFramework = frameworkService.getFramework(resource.getFrameworkName())
                    .orElseThrow(()-> new EntityNotFoundException());
            newres.setFramework(newFramework);
        } else {
            newres.setFramework(null);
        }

        Resource returned = resourceService.createResource(newres);
        return new ResponseEntity(returned,HttpStatus.ACCEPTED);

    }

    // DELs resource
    @DeleteMapping(value = "{resourcePath}")
    public ResponseEntity delResource(@PathVariable String resourcePath){
        Optional<Resource> testResource;
        try{
            int resourceId = Integer.parseInt(resourcePath);
            testResource = resourceService.getResource(resourceId);

        } catch(NumberFormatException nfe){
            testResource = resourceService.getResource(resourcePath);
        }
        if(!testResource.isPresent()){
            System.out.println("Resource not present.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Resource toDelete = testResource.get();
        toDelete = resourceService.destroyResource(toDelete);
        return new ResponseEntity(toDelete,HttpStatus.ACCEPTED);
    }

}
