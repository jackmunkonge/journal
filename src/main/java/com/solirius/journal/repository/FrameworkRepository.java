package com.solirius.journal.repository;

import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrameworkRepository extends CrudRepository<Framework, Integer> {

    @Override
    List<Framework> findAll();

    Optional<Framework> findByName(String frameworkName);

    List<Framework> findAllByName(String name);

    List<Framework> findAllByLanguage(Language language);

    List<Framework> findAllByOrderByFrameworkIdAsc();

    List<Framework> findAllByNameOrderByFrameworkIdAsc(String name);

    List<Framework> findAllByLanguageOrderByFrameworkIdAsc(Language language);
}
