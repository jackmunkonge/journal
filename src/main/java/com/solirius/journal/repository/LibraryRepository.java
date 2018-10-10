package com.solirius.journal.repository;

import com.solirius.journal.model.Library;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends TagRepository<Library> {
}
