package com.solirius.journal.repository;

import com.solirius.journal.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface TagRepository<T extends Tag> extends CrudRepository<T, Integer> {

    Optional<T> findByName(String name);

    List<T> findAllByOrderByIdAsc();
}
