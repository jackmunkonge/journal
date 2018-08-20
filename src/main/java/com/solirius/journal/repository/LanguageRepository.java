package com.solirius.journal.repository;

import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {

    @Override
    List<Language> findAll();

    Optional<Language> findByName(String languageName);

    List<Language> findAllByName(String name);
}
