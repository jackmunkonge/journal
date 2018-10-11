package com.solirius.journal.Service;

import com.solirius.journal.model.Framework;
import com.solirius.journal.repository.FrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrameworkService extends TagService {

    private FrameworkRepository frameworkRepository;

    @Autowired
    public FrameworkService(FrameworkRepository frameworkRepository) {
        super(frameworkRepository);
        this.frameworkRepository = frameworkRepository;
    }
}
