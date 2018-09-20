package com.solirius.journal.controller;

import com.solirius.journal.Service.*;
import com.solirius.journal.model.*;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ProjectService projectService;

    Gson gson;

    // GETs project
    @GetMapping(value = "{projectPath}")
    public ResponseEntity getProject(@PathVariable String projectPath) {
        Optional<Project> fetchedProject;
        try{
            int projectId = Integer.parseInt(projectPath);
            fetchedProject = projectService.getProject(projectId);
            if(!fetchedProject.isPresent()){
                return new ResponseEntity<>(new Message("Project with ID '" + projectId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedProject.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedProject = projectService.getProject(projectPath);
            if(!fetchedProject.isPresent()){
                return new ResponseEntity<>(new Message("Project with name '" + projectPath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedProject.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all projects
    @GetMapping(value = "")
    public ResponseEntity getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        if(projects.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get project list, project list is empty"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projects,HttpStatus.ACCEPTED);
    }

    // POSTs new project
    @PostMapping
    public ResponseEntity postProject(@RequestBody Project reqBody) {

        if(projectService.getProject(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, project with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }


        if(!reqBody.getResources().isEmpty()) {
            List<Resource> resourcesToPost = new ArrayList<>();
            for(Resource resource : reqBody.getResources()) {
                if(!resourceService.getResource(resource.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, resource with name '" + resource.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Resource resourceToPost = resourceService.getResource(resource.getName()).get();
                    resourcesToPost.add(resourceToPost);
                }
            }
            reqBody.setResources(resourcesToPost);
        }


        Project newProject = projectService.createProject(reqBody);

        return new ResponseEntity<>(newProject, HttpStatus.ACCEPTED);
    }

    // Updates project
    @PutMapping(value = "{projectPath}")
    public ResponseEntity putProject(@PathVariable String projectPath, @RequestBody Project reqBody) {
        Optional<Project> projectToUpdate;
        try{
            int projectId = Integer.parseInt(projectPath);
            projectToUpdate = projectService.getProject(projectId);
            if(!projectToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, project with ID '" + projectId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            projectToUpdate = projectService.getProject(projectPath);
            if(!projectToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, project with name '" + projectPath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Project newProject = projectToUpdate.get();
        if(reqBody.getName() != null) {
            newProject.setName(reqBody.getName());
        }

        if(reqBody.getDescription() != null) {
            newProject.setDescription(reqBody.getDescription());
        }

        if(reqBody.getUrl() != null) {
            newProject.setUrl(reqBody.getUrl());
        }

        if(reqBody.getFilePath() != null) {
            newProject.setFilePath(reqBody.getFilePath());
        }


        if(!reqBody.getResources().isEmpty()) {
            List<Resource> resourcesToUpdate = new ArrayList<>();
            for(Resource resource : reqBody.getResources()) {
                if(!resourceService.getResource(resource.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, resource with name '" + resource.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Resource resourceToUpdate = resourceService.getResource(resource.getName()).get();
                    resourcesToUpdate.add(resourceToUpdate);
                }
            }
            newProject.setResources(resourcesToUpdate);
        }


        Project returnedProject = projectService.createProject(newProject);
        return new ResponseEntity<>(returnedProject, HttpStatus.ACCEPTED);
    }

    // DELs project
    @DeleteMapping(value = "{projectPath}")
    public ResponseEntity delProject(@PathVariable String projectPath) {
        Optional<Project> prevProject;
        try{
            int projectId = Integer.parseInt(projectPath);
            prevProject = projectService.getProject(projectId);

        } catch(NumberFormatException nfe){
            prevProject = projectService.getProject(projectPath);
        }
        if(!prevProject.isPresent()){
            return new ResponseEntity<>(new Message("Project '" + projectPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Project projectToDelete = prevProject.get();
        gson = new Gson();
        Project deepCopy = gson.fromJson(gson.toJson(projectToDelete), Project.class);
        projectService.destroyProject(projectToDelete);
        return new ResponseEntity<>(deepCopy,HttpStatus.ACCEPTED);
    }
}
