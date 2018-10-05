//package com.solirius.journal.Service;
//
//import com.solirius.journal.model.Framework;
//import com.solirius.journal.repository.FrameworkRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class FrameworkService {
//
//    private FrameworkRepository frameworkRepository;
//
//    @Autowired
//    public FrameworkService(FrameworkRepository frameworkRepository) {
//        this.frameworkRepository = frameworkRepository;
//    }
//
//    public Optional<Framework> getFramework(Integer frameworkId) {
//        return frameworkRepository.findById(frameworkId);
//    }
//
//    public Optional<Framework> getFramework(String frameworkName) {
//        return frameworkRepository.findByName(frameworkName);
//    }
//
//    public List<Framework> getAllFrameworks() {
//        return frameworkRepository.findAllByOrderByFrameworkIdAsc();
//    }
//
//    public Framework createFramework(Framework framework) {
//        return frameworkRepository.save(framework);
//    }
//
//    public void destroyFramework(Framework framework) {
//        frameworkRepository.delete(framework);
//    }
//}
