package com.solirius.journal.Service;

import com.solirius.journal.model.Principle;
import com.solirius.journal.repository.PrincipleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrincipleService {

    private PrincipleRepository principleRepository;

    @Autowired
    public PrincipleService(PrincipleRepository principleRepository) {
        this.principleRepository = principleRepository;
    }

    public Optional<Principle> getPrinciple(Integer principleId) {
        return principleRepository.findById(principleId);
    }

    public Optional<Principle> getPrinciple(String principleName) {
        return principleRepository.findByName(principleName);
    }

    public List<Principle> getAllPrinciples() {
        return principleRepository.findAllByOrderByPrincipleIdAsc();
    }

    public Principle createPrinciple(Principle principle) {
        return principleRepository.save(principle);
    }

    public void destroyPrinciple(Principle principle) {
        principleRepository.delete(principle);
    }
}
