package com.solirius.journal.repository;

import com.solirius.journal.model.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Integer> {

    Optional<Library> findByName(String libraryName);

    List<Library> findAllByOrderByLibraryIdAsc();

    void deleteAllByName(String name);
}