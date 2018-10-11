package com.solirius.journal.Service;

import com.solirius.journal.model.Tag;
import com.solirius.journal.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class TagService<T extends Tag, U extends TagRepository<T>> {

    private U repository;

    @Autowired
    public TagService(U repository) {
        this.repository = repository;
    }

    public Optional<T> getTag(Integer tagId) {
        return repository.findById(tagId);
    }

    public Optional<T> getTag(String tagName) {
        return repository.findByName(tagName);
    }

    public List<T> getAllTags() {
        return repository.findAllByOrderByIdAsc();
    }

    public T createTag(T tag) {
        return repository.save(tag);
    }

    public void destroyTag(T tag) {
        repository.delete(tag);
    }
}
