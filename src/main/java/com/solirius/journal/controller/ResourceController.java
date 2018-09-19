package com.solirius.journal.controller;

import com.solirius.journal.Service.*;
import com.solirius.journal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private FrameworkService frameworkService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PrincipleService principleService;

    @Autowired
    private PluginService pluginService;

    @Autowired
    private LanguageService languageService;

    // GETs resource
    @GetMapping(value = "{resourcePath}")
    public ResponseEntity getResource(@PathVariable String resourcePath) {
        Optional<Resource> fetchedResource;
        try{
            int resourceId = Integer.parseInt(resourcePath);
            fetchedResource = resourceService.getResource(resourceId);
            if(!fetchedResource.isPresent()){
                return new ResponseEntity<>(new Message("Resource with ID '" + resourceId + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedResource.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedResource = resourceService.getResource(resourcePath);
            if(!fetchedResource.isPresent()){
                return new ResponseEntity<>(new Message("Resource with name '" + resourcePath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(fetchedResource.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all resources
    @GetMapping(value = "")
    public ResponseEntity getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        if(resources.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get resource list, resource list is empty"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resources,HttpStatus.ACCEPTED);
    }

    // POSTs new resource
    @PostMapping
    public ResponseEntity postResource(@RequestBody Resource reqBody) {

        if(resourceService.getResource(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, resource with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        if(!reqBody.getProjects().isEmpty()){
            for(Project project : reqBody.getProjects()) {
                Optional<Project> reqProject = projectService.getProject(project.getName());
                if(!reqProject.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, project with name '" + project.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(!reqBody.getTools().isEmpty()){
            for(Tool tool : reqBody.getTools()) {
                Optional<Tool> reqTool = toolService.getTool(tool.getName());
                if(!reqTool.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, tool with name '" + tool.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(!reqBody.getPlugins().isEmpty()){
            for(Plugin plugin : reqBody.getPlugins()) {
                Optional<Plugin> reqPlugin = pluginService.getPlugin(plugin.getName());
                if(!reqPlugin.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, plugin with name '" + plugin.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(!reqBody.getLibraries().isEmpty()){
            for(Library library : reqBody.getLibraries()) {
                Optional<Library> reqLibrary = libraryService.getLibrary(library.getName());
                if(!reqLibrary.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, library with name '" + library.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(!reqBody.getPrinciples().isEmpty()){
            for(Principle principle : reqBody.getPrinciples()) {
                Optional<Principle> reqPrinciple = principleService.getPrinciple(principle.getName());
                if(!reqPrinciple.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, principle with name '" + principle.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(!reqBody.getFrameworks().isEmpty()){
            for(Framework framework : reqBody.getFrameworks()) {
                Optional<Framework> reqFramework = frameworkService.getFramework(framework.getName());
                if(!reqFramework.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, framework with name '" + framework.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(!reqBody.getLanguages().isEmpty()){
            for(Language language : reqBody.getLanguages()) {
                Optional<Language> reqLanguage = languageService.getLanguage(language.getName());
                if(!reqLanguage.isPresent()){
                    return new ResponseEntity<>(new Message("Cannot create, language with name '" + language.getName() + "' does not exist"), HttpStatus.BAD_REQUEST);
                }
            }
        }

        Resource newResource = resourceService.createResource(reqBody);

        return new ResponseEntity<>(newResource,HttpStatus.ACCEPTED);
    }

    // Updates resource
    @PutMapping(value = "{resourcePath}")
    public ResponseEntity putResource(@PathVariable String resourcePath, @RequestBody Resource reqBody) {
        Optional<Resource> resourceToUpdate;
        try{
            int resourceId = Integer.parseInt(resourcePath);
            resourceToUpdate = resourceService.getResource(resourceId);
            if(!resourceToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, resource with ID '" + resourceId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            resourceToUpdate = resourceService.getResource(resourcePath);
            if(!resourceToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, resource with name '" + resourcePath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Resource newResource = resourceToUpdate.get();
        if(reqBody.getName() != null) {
            newResource.setName(reqBody.getName());
        }

        if(reqBody.getDescription() != null) {
            newResource.setDescription(reqBody.getDescription());
        }

        if(reqBody.getUrl() != null) {
            newResource.setUrl(reqBody.getUrl());
        }

        if(reqBody.getFilePath() != null) {
            newResource.setFilePath(reqBody.getFilePath());
        }

        if(!reqBody.getProjects().isEmpty()) {
            for(Project project : reqBody.getProjects()) {
                if(!projectService.getProject(project.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, project with name '" + project.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setProjects(reqBody.getProjects());
        }

        if(!reqBody.getTools().isEmpty()) {
            for(Tool tool : reqBody.getTools()) {
                if(!toolService.getTool(tool.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, tool with name '" + tool.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setTools(reqBody.getTools());
        }

        if(!reqBody.getPlugins().isEmpty()) {
            for(Plugin plugin : reqBody.getPlugins()) {
                if(!pluginService.getPlugin(plugin.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, plugin with name '" + plugin.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setPlugins(reqBody.getPlugins());
        }

        if(!reqBody.getLibraries().isEmpty()) {
            for(Library lib : reqBody.getLibraries()) {
                if(!libraryService.getLibrary(lib.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, library with name '" + lib.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setLibraries(reqBody.getLibraries());
        }

        if(!reqBody.getPrinciples().isEmpty()) {
            for(Principle principle : reqBody.getPrinciples()) {
                if(!principleService.getPrinciple(principle.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, principle with name '" + principle.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setPrinciples(reqBody.getPrinciples());
        }

        if(!reqBody.getLanguages().isEmpty()) {
            for(Language language : reqBody.getLanguages()) {
                if(!languageService.getLanguage(language.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, language with name '" + language.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setLanguages(reqBody.getLanguages());
        }

        if(!reqBody.getFrameworks().isEmpty()) {
            for(Framework framework : reqBody.getFrameworks()) {
                if(!frameworkService.getFramework(framework.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, library with name '" + framework.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                }
            }
            newResource.setFrameworks(reqBody.getFrameworks());
        }

        Resource returnedResource = resourceService.createResource(newResource);
        return new ResponseEntity<>(returnedResource, HttpStatus.ACCEPTED);
    }

    // DELs resource
    @DeleteMapping(value = "{resourcePath}")
    public ResponseEntity delResource(@PathVariable String resourcePath) {
        Optional<Resource> prevResource;
        try{
            int resourceId = Integer.parseInt(resourcePath);
            prevResource = resourceService.getResource(resourceId);

        } catch(NumberFormatException nfe){
            prevResource = resourceService.getResource(resourcePath);
        }
        if(!prevResource.isPresent()){
            return new ResponseEntity<>(new Message("Resource '" + resourcePath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Resource resourceToDelete = prevResource.get();
        Resource deletedResource = resourceService.destroyResource(resourceToDelete);
        return new ResponseEntity<>(deletedResource,HttpStatus.ACCEPTED);
    }
}
