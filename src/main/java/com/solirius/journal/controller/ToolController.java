package com.solirius.journal.controller;

import com.solirius.journal.Service.ToolService;
import com.solirius.journal.model.Tool;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    Gson gson;

    // GETs tool
    @GetMapping(value = "{toolPath}")
    public ResponseEntity getTool(@PathVariable String toolPath) {
        Optional<Tool> fetchedTool;
        try{
            int toolId = Integer.parseInt(toolPath);
            fetchedTool = toolService.getTool(toolId);
            if(!fetchedTool.isPresent()){
                return new ResponseEntity<>(new Message("Tool with ID '" + toolId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedTool.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedTool = toolService.getTool(toolPath);
            if(!fetchedTool.isPresent()){
                return new ResponseEntity<>(new Message("Tool with name '" + toolPath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedTool.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all tools
    @GetMapping(value = "")
    public ResponseEntity getAllTools() {
        List<Tool> tools = toolService.getAllTools();
        if(tools.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get tool list, tool list is empty"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tools,HttpStatus.ACCEPTED);
    }

    // POSTs new tool
    @PostMapping
    public ResponseEntity postTool(@RequestBody Tool reqBody) {

        if(toolService.getTool(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, tool with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        Tool newTool = toolService.createTool(reqBody);

        return new ResponseEntity<>(newTool,HttpStatus.ACCEPTED);
    }

    // Updates tool
    @PutMapping(value = "{toolPath}")
    public ResponseEntity putTool(@PathVariable String toolPath, @RequestBody Tool reqBody) {
        Optional<Tool> toolToUpdate;
        try{
            int toolId = Integer.parseInt(toolPath);
            toolToUpdate = toolService.getTool(toolId);
            if(!toolToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, tool with ID '" + toolId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            toolToUpdate = toolService.getTool(toolPath);
            if(!toolToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, tool with name '" + toolPath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Tool newTool = toolToUpdate.get();
        if(reqBody.getName() != null) {
            newTool.setName(reqBody.getName());
        }

        if(reqBody.getDescription() != null) {
            newTool.setDescription(reqBody.getDescription());
        }

        Tool returnedTool = toolService.createTool(newTool);
        return new ResponseEntity<>(returnedTool, HttpStatus.ACCEPTED);
    }

    // DELs tool
    @DeleteMapping(value = "{toolPath}")
    public ResponseEntity delTool(@PathVariable String toolPath) {
        Optional<Tool> prevTool;
        try{
            int toolId = Integer.parseInt(toolPath);
            prevTool = toolService.getTool(toolId);

        } catch(NumberFormatException nfe){
            prevTool = toolService.getTool(toolPath);
        }
        if(!prevTool.isPresent()){
            return new ResponseEntity<>(new Message("Tool '" + toolPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Tool toolToDelete = prevTool.get();
        gson = new Gson();
        Tool deepCopy = gson.fromJson(gson.toJson(toolToDelete), Tool.class);
        toolService.destroyTool(toolToDelete);
        return new ResponseEntity<>(deepCopy, HttpStatus.ACCEPTED);
    }
}
