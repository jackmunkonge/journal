package com.solirius.journal.repository;

import com.solirius.journal.model.Plugin;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginRepository extends TagRepository<Plugin> {
}
