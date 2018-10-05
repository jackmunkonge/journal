//package com.solirius.journal.repository;
//
//import com.solirius.journal.model.Principle;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface PrincipleRepository extends CrudRepository<Principle, Integer> {
//
//    Optional<Principle> findByName(String principleName);
//
//    List<Principle> findAllByOrderByPrincipleIdAsc();
//
//    void deleteByName(String name);
//}