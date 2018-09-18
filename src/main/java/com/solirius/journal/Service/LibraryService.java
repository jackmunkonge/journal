package com.solirius.journal.Service;

import com.solirius.journal.model.Library;
import com.solirius.journal.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Optional<Library> getLibrary(Integer libraryId) {
        return libraryRepository.findById(libraryId);
    }

    public Optional<Library> getLibrary(String libraryName) {
        return libraryRepository.findByName(libraryName);
    }

    public List<Library> getAllLibraries() {
        return libraryRepository.findAllByOrderByLibraryIdAsc();
    }

    public Library createLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public Library destroyLibrary(Library library) {
        libraryRepository.delete(library);
        return library;
    }
}
