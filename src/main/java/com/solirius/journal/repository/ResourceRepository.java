package com.solirius.journal.repository;

import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.domain.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resources;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Integer> {

    @Override
    List<Resource> findAll();

    List<Resource> findAllByFramework(Framework framework);

    List<Resource> findAllByLanguage(Language language);

    List<Resource> findAllByName(String name);

    Optional<Resource> findByName(String name);

}
