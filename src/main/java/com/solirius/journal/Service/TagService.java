package com.solirius.journal.Service;

import com.solirius.journal.model.Tag;
import com.solirius.journal.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> getTag(Integer tagId) {
        return tagRepository.findById(tagId);
    }

    public Optional<Tag> getTag(String tagName) {
        return tagRepository.findByName(tagName);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAllByOrderByTagIdAsc();
    }

    public List<Tag> getAllTagsByTagType(String tagType) {
        return tagRepository.findAllByTagTypeOrderByTagIdAsc(tagType);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void destroyTag(Tag tag) {
        tagRepository.delete(tag);
    }
}
