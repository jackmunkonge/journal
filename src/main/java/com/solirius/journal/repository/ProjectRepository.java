package com.solirius.journal.repository;

import com.solirius.journal.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

    Optional<Project> findByName(String projectName);

    List<Project> findAllByOrderByIdAsc();
}