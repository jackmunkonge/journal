//package com.solirius.journal.repository;
//
//import com.solirius.journal.model.Framework;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface FrameworkRepository extends CrudRepository<Framework, Integer> {
//
//    Optional<Framework> findByName(String frameworkName);
//
//    List<Framework> findAllByOrderByFrameworkIdAsc();
//
//    void deleteByName(String name);
//}
