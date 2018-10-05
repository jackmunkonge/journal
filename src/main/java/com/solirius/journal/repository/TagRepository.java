package com.solirius.journal.repository;

import com.solirius.journal.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    Optional<Tag> findByName(String tagName);

    List<Tag> findAllByOrderByTagIdAsc();

    List<Tag> findAllByTagTypeOrderByTagIdAsc(String tagType);
}
