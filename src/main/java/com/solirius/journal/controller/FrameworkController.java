package com.solirius.journal.controller;

import com.solirius.journal.Service.FrameworkService;
import com.solirius.journal.model.Framework;
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

    @Autowired
    private FrameworkService frameworkService;

    Gson gson;

    // GETs framework
    @GetMapping(value = "{frameworkPath}")
    public ResponseEntity getFramework(@PathVariable String frameworkPath) {
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
    public ResponseEntity getAllFrameworks() {
        List<Framework> frameworks = frameworkService.getAllFrameworks();
        if(frameworks.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get framework list, framework list is empty"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(frameworks,HttpStatus.ACCEPTED);
    }

    // POSTs new framework
    @PostMapping
    public ResponseEntity postFramework(@RequestBody Framework reqBody) {

        if(frameworkService.getFramework(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, framework with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        Framework newFramework = frameworkService.createFramework(reqBody);

        return new ResponseEntity<>(newFramework,HttpStatus.ACCEPTED);
    }

    // Updates framework
    @PutMapping(value = "{frameworkPath}")
    public ResponseEntity putFramework(@PathVariable String frameworkPath, @RequestBody Framework reqBody) {
        Optional<Framework> frameworkToUpdate;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            frameworkToUpdate = frameworkService.getFramework(frameworkId);
            if(!frameworkToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, framework with ID '" + frameworkId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            frameworkToUpdate = frameworkService.getFramework(frameworkPath);
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

        Framework returnedFramework = frameworkService.createFramework(newFramework);
        return new ResponseEntity<>(returnedFramework, HttpStatus.ACCEPTED);
    }

    // DELs framework
    @DeleteMapping(value = "{frameworkPath}")
    public ResponseEntity delFramework(@PathVariable String frameworkPath) {
        Optional<Framework> prevFramework;
        try{
            int frameworkId = Integer.parseInt(frameworkPath);
            prevFramework = frameworkService.getFramework(frameworkId);

        } catch(NumberFormatException nfe){
            prevFramework = frameworkService.getFramework(frameworkPath);
        }
        if(!prevFramework.isPresent()){
            return new ResponseEntity<>(new Message("Framework '" + frameworkPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Framework frameworkToDelete = prevFramework.get();
        gson = new Gson();
        Framework deepCopy = gson.fromJson(gson.toJson(frameworkToDelete), Framework.class);
        frameworkService.destroyFramework(frameworkToDelete);
        return new ResponseEntity<>(deepCopy,HttpStatus.ACCEPTED);
    }
}
