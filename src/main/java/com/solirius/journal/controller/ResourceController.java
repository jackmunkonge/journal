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

    Gson gson;

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


        if(!reqBody.getFrameworks().isEmpty()) {
            List<Framework> frameworksToPost = new ArrayList<>();
            for(Framework framework : reqBody.getFrameworks()) {
                if(!frameworkService.getFramework(framework.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, framework with name '" + framework.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Framework frameworkToPost = frameworkService.getFramework(framework.getName()).get();
                    frameworksToPost.add(frameworkToPost);
                }
            }
            reqBody.setFrameworks(frameworksToPost);
        }

        if(!reqBody.getLanguages().isEmpty()) {
            List<Language> languagesToPost = new ArrayList<>();
            for(Language language : reqBody.getLanguages()) {
                if(!languageService.getLanguage(language.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, language with name '" + language.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Language languageToPost = languageService.getLanguage(language.getName()).get();
                    languagesToPost.add(languageToPost);
                }
            }
            reqBody.setLanguages(languagesToPost);
        }

        if(!reqBody.getLibraries().isEmpty()) {
            List<Library> librariesToPost = new ArrayList<>();
            for(Library library : reqBody.getLibraries()) {
                if(!libraryService.getLibrary(library.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, library with name '" + library.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Library libraryToPost = libraryService.getLibrary(library.getName()).get();
                    librariesToPost.add(libraryToPost);
                }
            }
            reqBody.setLibraries(librariesToPost);
        }

        if(!reqBody.getPlugins().isEmpty()) {
            List<Plugin> pluginsToPost = new ArrayList<>();
            for(Plugin plugin : reqBody.getPlugins()) {
                if(!pluginService.getPlugin(plugin.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, plugin with name '" + plugin.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Plugin pluginToPost = pluginService.getPlugin(plugin.getName()).get();
                    pluginsToPost.add(pluginToPost);
                }
            }
            reqBody.setPlugins(pluginsToPost);
        }

        if(!reqBody.getPrinciples().isEmpty()) {
            List<Principle> principlesToPost = new ArrayList<>();
            for(Principle principle : reqBody.getPrinciples()) {
                if(!principleService.getPrinciple(principle.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, principle with name '" + principle.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Principle principleToPost = principleService.getPrinciple(principle.getName()).get();
                    principlesToPost.add(principleToPost);
                }
            }
            reqBody.setPrinciples(principlesToPost);
        }

        if(!reqBody.getTools().isEmpty()) {
            List<Tool> toolsToPost = new ArrayList<>();
            for(Tool tool : reqBody.getTools()) {
                if(!toolService.getTool(tool.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot create, tool with name '" + tool.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Tool toolToPost = toolService.getTool(tool.getName()).get();
                    toolsToPost.add(toolToPost);
                }
            }
            reqBody.setTools(toolsToPost);
        }


        Resource newResource = resourceService.createResource(reqBody);

        return new ResponseEntity<>(newResource, HttpStatus.ACCEPTED);
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


        if(!reqBody.getFrameworks().isEmpty()) {
            List<Framework> frameworksToUpdate = new ArrayList<>();
            for(Framework framework : reqBody.getFrameworks()) {
                if(!frameworkService.getFramework(framework.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, framework with name '" + framework.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Framework frameworkToUpdate = frameworkService.getFramework(framework.getName()).get();
                    frameworksToUpdate.add(frameworkToUpdate);
                }
            }
            newResource.setFrameworks(frameworksToUpdate);
        }

        if(!reqBody.getLanguages().isEmpty()) {
            List<Language> languagesToUpdate = new ArrayList<>();
            for(Language language : reqBody.getLanguages()) {
                if(!languageService.getLanguage(language.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, language with name '" + language.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Language languageToUpdate = languageService.getLanguage(language.getName()).get();
                    languagesToUpdate.add(languageToUpdate);
                }
            }
            newResource.setLanguages(languagesToUpdate);
        }

        if(!reqBody.getLibraries().isEmpty()) {
            List<Library> librariesToUpdate = new ArrayList<>();
            for(Library library : reqBody.getLibraries()) {
                if(!libraryService.getLibrary(library.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, library with name '" + library.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Library libraryToUpdate = libraryService.getLibrary(library.getName()).get();
                    librariesToUpdate.add(libraryToUpdate);
                }
            }
            newResource.setLibraries(librariesToUpdate);
        }

        if(!reqBody.getPlugins().isEmpty()) {
            List<Plugin> pluginsToUpdate = new ArrayList<>();
            for(Plugin plugin : reqBody.getPlugins()) {
                if(!pluginService.getPlugin(plugin.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, plugin with name '" + plugin.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Plugin pluginToUpdate = pluginService.getPlugin(plugin.getName()).get();
                    pluginsToUpdate.add(pluginToUpdate);
                }
            }
            newResource.setPlugins(pluginsToUpdate);
        }

        if(!reqBody.getPrinciples().isEmpty()) {
            List<Principle> principlesToUpdate = new ArrayList<>();
            for(Principle principle : reqBody.getPrinciples()) {
                if(!principleService.getPrinciple(principle.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, principle with name '" + principle.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Principle principleToUpdate = principleService.getPrinciple(principle.getName()).get();
                    principlesToUpdate.add(principleToUpdate);
                }
            }
            newResource.setPrinciples(principlesToUpdate);
        }

        if(!reqBody.getTools().isEmpty()) {
            List<Tool> toolsToUpdate = new ArrayList<>();
            for(Tool tool : reqBody.getTools()) {
                if(!toolService.getTool(tool.getName()).isPresent()) {
                    return new ResponseEntity<>(new Message("Cannot update, tool with name '" + tool.getName() + "' does not exist"), HttpStatus.NOT_FOUND);
                } else {
                    Tool toolToUpdate = toolService.getTool(tool.getName()).get();
                    toolsToUpdate.add(toolToUpdate);
                }
            }
            newResource.setTools(toolsToUpdate);
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
        gson = new Gson();
        Resource deepCopy = gson.fromJson(gson.toJson(resourceToDelete), Resource.class);
        resourceService.destroyResource(resourceToDelete);
        return new ResponseEntity<>(deepCopy,HttpStatus.ACCEPTED);
    }
}
