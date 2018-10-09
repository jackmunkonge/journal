package com.solirius.journal.controller;

import com.solirius.journal.Service.PrincipleService;
import com.solirius.journal.model.Principle;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/principles")
public class PrincipleController {

    @Autowired
    private PrincipleService principleService;

    Gson gson;

    // GETs principle
    @GetMapping(value = "{principlePath}")
    public ResponseEntity getPrinciple(@PathVariable String principlePath) {
        Optional<Principle> fetchedPrinciple;
        try{
            int principleId = Integer.parseInt(principlePath);
            fetchedPrinciple = principleService.getPrinciple(principleId);
            if(!fetchedPrinciple.isPresent()){
                return new ResponseEntity<>(new Message("Principle with ID '" + principleId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedPrinciple.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedPrinciple = principleService.getPrinciple(principlePath);
            if(!fetchedPrinciple.isPresent()){
                return new ResponseEntity<>(new Message("Principle with name '" + principlePath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedPrinciple.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all principles
    @GetMapping(value = "")
    public ResponseEntity getAllPrinciples() {
        List<Principle> principles = principleService.getAllPrinciples();
        if(principles.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get principle list, principle list is empty"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(principles,HttpStatus.ACCEPTED);
    }

    // POSTs new principle
    @PostMapping
    public ResponseEntity postPrinciple(@RequestBody Principle reqBody) {

        if(principleService.getPrinciple(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, principle with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        Principle newPrinciple = principleService.createPrinciple(reqBody);

        return new ResponseEntity<>(newPrinciple,HttpStatus.ACCEPTED);
    }

    // Updates principle
    @PutMapping(value = "{principlePath}")
    public ResponseEntity putPrinciple(@PathVariable String principlePath, @RequestBody Principle reqBody) {
        Optional<Principle> principleToUpdate;
        try{
            int principleId = Integer.parseInt(principlePath);
            principleToUpdate = principleService.getPrinciple(principleId);
            if(!principleToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, principle with ID '" + principleId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            principleToUpdate = principleService.getPrinciple(principlePath);
            if(!principleToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, principle with name '" + principlePath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Principle newPrinciple = principleToUpdate.get();
        if(reqBody.getName() != null) {
            newPrinciple.setName(reqBody.getName());
        }

        if(reqBody.getDescription() != null) {
            newPrinciple.setDescription(reqBody.getDescription());
        }

        Principle returnedPrinciple = principleService.createPrinciple(newPrinciple);
        return new ResponseEntity<>(returnedPrinciple, HttpStatus.ACCEPTED);
    }

    // DELs principle
    @DeleteMapping(value = "{principlePath}")
    public ResponseEntity delPrinciple(@PathVariable String principlePath) {
        Optional<Principle> prevPrinciple;
        try{
            int principleId = Integer.parseInt(principlePath);
            prevPrinciple = principleService.getPrinciple(principleId);

        } catch(NumberFormatException nfe){
            prevPrinciple = principleService.getPrinciple(principlePath);
        }
        if(!prevPrinciple.isPresent()){
            return new ResponseEntity<>(new Message("Principle '" + principlePath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Principle principleToDelete = prevPrinciple.get();
        gson = new Gson();
        Principle deepCopy = gson.fromJson(gson.toJson(principleToDelete), Principle.class);
        principleService.destroyPrinciple(principleToDelete);
        return new ResponseEntity<>(deepCopy, HttpStatus.ACCEPTED);
    }
}
