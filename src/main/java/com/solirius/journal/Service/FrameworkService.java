package com.solirius.journal.Service;

import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.repository.FrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrameworkService {

    private FrameworkRepository frameworkRepository;

    @Autowired
    public FrameworkService(FrameworkRepository frameworkRepository) {
        this.frameworkRepository = frameworkRepository;
    }

    public Optional<Framework> getFramework(Integer frameworkId) {
        return frameworkRepository.findById(frameworkId);
    }

    public Optional<Framework> getFramework(String frameworkName) {
        return frameworkRepository.findByName(frameworkName);
    }

    public List<Framework> getAllFrameworks() {
        return frameworkRepository.findAll();
    }

    public List<Framework> getAllFrameworks(String name) {
        return frameworkRepository.findAllByName(name);
    }

    public List<Framework> getAllFrameworks(Language language){
        return frameworkRepository.findAllByLanguage(language);
    }

    public Framework createFramework(Framework framework) {
        return frameworkRepository.save(framework);
    }

    public Framework destroyFramework(Framework framework) {
        frameworkRepository.delete(framework);
        return framework;
    }
}
