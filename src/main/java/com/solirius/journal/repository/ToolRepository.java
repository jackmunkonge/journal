//package com.solirius.journal.repository;
//
//import com.solirius.journal.model.Tool;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ToolRepository extends CrudRepository<Tool, Integer> {
//
//    Optional<Tool> findByName(String toolName);
//
//    List<Tool> findAllByOrderByToolIdAsc();
//
//    void deleteByName(String name);
//}