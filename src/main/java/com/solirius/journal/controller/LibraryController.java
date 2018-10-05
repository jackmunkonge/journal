//package com.solirius.journal.controller;
//
//import com.solirius.journal.Service.LibraryService;
//import com.solirius.journal.model.Library;
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
//@RequestMapping("/libraries")
//public class LibraryController {
//
//    @Autowired
//    private LibraryService libraryService;
//
//    Gson gson;
//
//    // GETs library
//    @GetMapping(value = "{libraryPath}")
//    public ResponseEntity getLibrary(@PathVariable String libraryPath) {
//        Optional<Library> fetchedLibrary;
//        try{
//            int libraryId = Integer.parseInt(libraryPath);
//            fetchedLibrary = libraryService.getLibrary(libraryId);
//            if(!fetchedLibrary.isPresent()){
//                return new ResponseEntity<>(new Message("Library with ID '" + libraryId + "' does not exist"),HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(fetchedLibrary.get(),HttpStatus.ACCEPTED);
//        } catch(NumberFormatException nfe){
//            fetchedLibrary = libraryService.getLibrary(libraryPath);
//            if(!fetchedLibrary.isPresent()){
//                return new ResponseEntity<>(new Message("Library with name '" + libraryPath + "' does not exist"),HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(fetchedLibrary.get(),HttpStatus.ACCEPTED);
//        }
//    }
//
//    // GETs all libraries
//    @GetMapping(value = "")
//    public ResponseEntity getAllLibraries() {
//        List<Library> libraries = libraryService.getAllLibraries();
//        if(libraries.isEmpty()){
//            return new ResponseEntity<>(new Message("Cannot get library list, library list is empty"),HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(libraries,HttpStatus.ACCEPTED);
//    }
//
//    // POSTs new library
//    @PostMapping
//    public ResponseEntity postLibrary(@RequestBody Library reqBody) {
//
//        if(libraryService.getLibrary(reqBody.getName()).isPresent()) {
//            return new ResponseEntity<>(new Message("Cannot create, library with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
//        }
//
//        Library newLibrary = libraryService.createLibrary(reqBody);
//
//        return new ResponseEntity<>(newLibrary,HttpStatus.ACCEPTED);
//    }
//
//    // Updates library
//    @PutMapping(value = "{libraryPath}")
//    public ResponseEntity putLibrary(@PathVariable String libraryPath, @RequestBody Library reqBody) {
//        Optional<Library> libraryToUpdate;
//        try{
//            int libraryId = Integer.parseInt(libraryPath);
//            libraryToUpdate = libraryService.getLibrary(libraryId);
//            if(!libraryToUpdate.isPresent()){
//                return new ResponseEntity<>(new Message("Cannot update, library with ID '" + libraryId + "' does not exist"), HttpStatus.NOT_FOUND);
//            }
//        } catch(NumberFormatException nfe) {
//            libraryToUpdate = libraryService.getLibrary(libraryPath);
//            if(!libraryToUpdate.isPresent()){
//                return new ResponseEntity<>(new Message("Cannot update, library with name '" + libraryPath + "' does not exist"), HttpStatus.NOT_FOUND);
//            }
//        }
//
//
//        Library newLibrary = libraryToUpdate.get();
//        if(reqBody.getName() != null) {
//            newLibrary.setName(reqBody.getName());
//        }
//
//        if(reqBody.getDescription() != null) {
//            newLibrary.setDescription(reqBody.getDescription());
//        }
//
//        Library returnedLibrary = libraryService.createLibrary(newLibrary);
//        return new ResponseEntity<>(returnedLibrary, HttpStatus.ACCEPTED);
//    }
//
//    // DELs library
//    @DeleteMapping(value = "{libraryPath}")
//    public ResponseEntity delLibrary(@PathVariable String libraryPath) {
//        Optional<Library> prevLibrary;
//        try{
//            int libraryId = Integer.parseInt(libraryPath);
//            prevLibrary = libraryService.getLibrary(libraryId);
//
//        } catch(NumberFormatException nfe){
//            prevLibrary = libraryService.getLibrary(libraryPath);
//        }
//        if(!prevLibrary.isPresent()){
//            return new ResponseEntity<>(new Message("Library '" + libraryPath + "' does not exist"),HttpStatus.NOT_FOUND);
//        }
//
//        Library libraryToDelete = prevLibrary.get();
//        gson = new Gson();
//        Library deepCopy = gson.fromJson(gson.toJson(libraryToDelete), Library.class);
//        libraryService.destroyLibrary(libraryToDelete);
//        return new ResponseEntity<>(deepCopy, HttpStatus.ACCEPTED);
//    }
//}
