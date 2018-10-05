//package com.solirius.journal.Service;
//
//import com.solirius.journal.model.Language;
//import com.solirius.journal.repository.LanguageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class LanguageService {
//
//    private LanguageRepository languageRepository;
//
//    @Autowired
//    public LanguageService(LanguageRepository languageRepository) {
//        this.languageRepository = languageRepository;
//    }
//
//    public Optional<Language> getLanguage(Integer languageId) {
//        return languageRepository.findById(languageId);
//    }
//
//    public Optional<Language> getLanguage(String languageName) {
//        return languageRepository.findByName(languageName);
//    }
//
//    public List<Language> getAllLanguages() {
//        return languageRepository.findAllByOrderByLanguageIdAsc();
//    }
//
//    public Language createLanguage(Language language) {
//        return languageRepository.save(language);
//    }
//
//    public void destroyLanguage(Language language) {
//        languageRepository.delete(language);
//    }
//}
