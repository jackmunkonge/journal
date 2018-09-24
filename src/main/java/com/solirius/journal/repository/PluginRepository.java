package com.solirius.journal.repository;

import com.solirius.journal.model.Plugin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PluginRepository extends CrudRepository<Plugin, Integer> {

    Optional<Plugin> findByName(String pluginName);

    List<Plugin> findAllByOrderByPluginIdAsc();

    void deleteAllByName(String name);
}