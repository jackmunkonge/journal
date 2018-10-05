package com.solirius.journal.controller;

import com.solirius.journal.Service.TagService;
import com.solirius.journal.model.Tag;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    Gson gson;

    // GETs tag
    @GetMapping(value = "{tagPath}")
    public ResponseEntity getTag(@PathVariable String tagPath) {
        Optional<Tag> fetchedFrame;
        try{
            int tagId = Integer.parseInt(tagPath);
            fetchedFrame = tagService.getTag(tagId);
            if(!fetchedFrame.isPresent()){
                return new ResponseEntity<>(new Message("Tag with ID '" + tagId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity<>(fetchedFrame.get(),HttpStatus.ACCEPTED);
        } catch(NumberFormatException nfe){
            fetchedFrame = tagService.getTag(tagPath);
            if(!fetchedFrame.isPresent()){
                return new ResponseEntity<>(new Message("Tag with name '" + tagPath + "' does not exist"),HttpStatus.NOT_FOUND);
            }
                return new ResponseEntity<>(fetchedFrame.get(),HttpStatus.ACCEPTED);
        }
    }

    // GETs all tags
    @GetMapping(value = "")
    public ResponseEntity getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        if(tags.isEmpty()){
            return new ResponseEntity<>(new Message("Cannot get tag list, tag list is empty"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tags,HttpStatus.ACCEPTED);
    }

    // POSTs new tag
    @PostMapping
    public ResponseEntity postTag(@RequestBody Tag reqBody) {

        if(tagService.getTag(reqBody.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Cannot create, tag with name '" + reqBody.getName() + "' already exists"), HttpStatus.BAD_REQUEST);
        }

        Tag newTag = tagService.createTag(reqBody);

        return new ResponseEntity<>(newTag,HttpStatus.ACCEPTED);
    }

    // Updates tag
    @PutMapping(value = "{tagPath}")
    public ResponseEntity putTag(@PathVariable String tagPath, @RequestBody Tag reqBody) {
        Optional<Tag> tagToUpdate;
        try{
            int tagId = Integer.parseInt(tagPath);
            tagToUpdate = tagService.getTag(tagId);
            if(!tagToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, tag with ID '" + tagId + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        } catch(NumberFormatException nfe) {
            tagToUpdate = tagService.getTag(tagPath);
            if(!tagToUpdate.isPresent()){
                return new ResponseEntity<>(new Message("Cannot update, tag with name '" + tagPath + "' does not exist"), HttpStatus.NOT_FOUND);
            }
        }


        Tag newTag = tagToUpdate.get();
        if(reqBody.getName() != null) {
            newTag.setName(reqBody.getName());
        }

        if(reqBody.getTagDescription() != null) {
            newTag.setTagDescription(reqBody.getTagDescription());
        }

        Tag returnedTag = tagService.createTag(newTag);
        return new ResponseEntity<>(returnedTag, HttpStatus.ACCEPTED);
    }

    // DELs tag
    @DeleteMapping(value = "{tagPath}")
    public ResponseEntity delTag(@PathVariable String tagPath) {
        Optional<Tag> prevTag;
        try{
            int tagId = Integer.parseInt(tagPath);
            prevTag = tagService.getTag(tagId);

        } catch(NumberFormatException nfe){
            prevTag = tagService.getTag(tagPath);
        }
        if(!prevTag.isPresent()){
            return new ResponseEntity<>(new Message("Tag '" + tagPath + "' does not exist"),HttpStatus.NOT_FOUND);
        }

        Tag tagToDelete = prevTag.get();
        gson = new Gson();
        Tag deepCopy = gson.fromJson(gson.toJson(tagToDelete), Tag.class);
        tagService.destroyTag(tagToDelete);
        return new ResponseEntity<>(deepCopy,HttpStatus.ACCEPTED);
    }
}
