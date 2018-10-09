package com.solirius.journal.repository;

import com.solirius.journal.model.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Integer> {

    Optional<Resource> findByName(String resourceName);

    List<Resource> findAllByOrderByIdAsc();
}