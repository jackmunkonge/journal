//package com.solirius.journal.controller;
//
//import com.solirius.journal.Service.PluginService;
//import com.solirius.journal.model.Plugin;
//import gherkin.deps.com.google.gson.Gson;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/plugins")
//public class PluginController {
//
//    @Autowired
//    private PluginService pluginService;
//
//    Gson gson;
//
//    // GETs plugin
//    @GetMapping(value = "{pluginPath}")
//    public ResponseEntity getPlugin(@PathVariable String pluginPath) {
//        Optional<Plugin> fetchedPlugin;
//        try{
//            int pluginId = Integer.parseInt(pluginPath);
//            fetchedPlugin = pluginService.getPlugin(pluginId);
//            if(!fetchedPlugin.isPresent()){
//                return new ResponseEntity<>(new Message("Plugin with ID '" + pluginId + "' does not exist"),HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(fetchedPlugin.get(),HttpStatus.ACCEPTED);
//        } catch(NumberFormatException nfe){
//            fetchedPlugin = pluginService.getPlugin(pluginPath);
//            if(!fetchedPlugin.isPresent()){
//                return new ResponseEntity<>(new Message("Plugin with name '" + pluginPath + "' does not exist"),HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(fetchedPlugin.get(),HttpStatus.ACCEPTED);
//        }
//    }
//
//    // GETs all plugins
//    @GetMapping(value = "")
//    public ResponseEntity getAllPlugins() {
//        List<Plugin> plugins = pluginService.getAllPlugins();
//        if(plugins.isEmpty()){
//            return new ResponseEntity<>(new Message("Cannot get plugin list, plugin list is empty"),HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(plugins,HttpStatus.ACCEPTED);
//    }
//
//    // POSTs new plugin
//    @PostMapping
//    public ResponseEntity postPlugin(@RequestBody Plugin reqBody) {
//
//        if(pluginService.getPlugin(reqBody.getName()).isPresent()) {
//            return new ResponseEntity<>(new Message("Cannot create, plugin with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
//        }
//
//        Plugin newPlugin = pluginService.createPlugin(reqBody);
//
//        return new ResponseEntity<>(newPlugin,HttpStatus.ACCEPTED);
//    }
//
//    // Updates plugin
//    @PutMapping(value = "{pluginPath}")
//    public ResponseEntity putPlugin(@PathVariable String pluginPath, @RequestBody Plugin reqBody) {
//        Optional<Plugin> pluginToUpdate;
//        try{
//            int pluginId = Integer.parseInt(pluginPath);
//            pluginToUpdate = pluginService.getPlugin(pluginId);
//            if(!pluginToUpdate.isPresent()){
//                return new ResponseEntity<>(new Message("Cannot update, plugin with ID '" + pluginId + "' does not exist"), HttpStatus.NOT_FOUND);
//            }
//        } catch(NumberFormatException nfe) {
//            pluginToUpdate = pluginService.getPlugin(pluginPath);
//            if(!pluginToUpdate.isPresent()){
//                return new ResponseEntity<>(new Message("Cannot update, plugin with name '" + pluginPath + "' does not exist"), HttpStatus.NOT_FOUND);
//            }
//        }
//
//
//        Plugin newPlugin = pluginToUpdate.get();
//        if(reqBody.getName() != null) {
//            newPlugin.setName(reqBody.getName());
//        }
//
//        if(reqBody.getDescription() != null) {
//            newPlugin.setDescription(reqBody.getDescription());
//        }
//
//        Plugin returnedPlugin = pluginService.createPlugin(newPlugin);
//        return new ResponseEntity<>(returnedPlugin, HttpStatus.ACCEPTED);
//    }
//
//    // DELs plugin
//    @DeleteMapping(value = "{pluginPath}")
//    public ResponseEntity delPlugin(@PathVariable String pluginPath) {
//        Optional<Plugin> prevPlugin;
//        try{
//            int pluginId = Integer.parseInt(pluginPath);
//            prevPlugin = pluginService.getPlugin(pluginId);
//
//        } catch(NumberFormatException nfe){
//            prevPlugin = pluginService.getPlugin(pluginPath);
//        }
//        if(!prevPlugin.isPresent()){
//            return new ResponseEntity<>(new Message("Plugin '" + pluginPath + "' does not exist"),HttpStatus.NOT_FOUND);
//        }
//
//        Plugin pluginToDelete = prevPlugin.get();
//        gson = new Gson();
//        Plugin deepCopy = gson.fromJson(gson.toJson(pluginToDelete), Plugin.class);
//        pluginService.destroyPlugin(pluginToDelete);
//        return new ResponseEntity<>(deepCopy, HttpStatus.ACCEPTED);
//    }
//}
