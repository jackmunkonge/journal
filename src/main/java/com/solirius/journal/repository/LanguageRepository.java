//package com.solirius.journal.repository;
//
//import com.solirius.journal.model.Language;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface LanguageRepository extends CrudRepository<Language, Integer> {
//
//    Optional<Language> findByName(String languageName);
//
//    List<Language> findAllByOrderByLanguageIdAsc();
//
//    void deleteByName(String name);
//}
