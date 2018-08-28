package com.solirius.journal.Service;

import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.domain.Resource;
import com.solirius.journal.repository.ResourceRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Optional<Resource> getResource(Integer resourceId) { return resourceRepository.findById(resourceId);}

    public Optional<Resource> getResource(String name) {
        return resourceRepository.findByName(name);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAllByOrderByResourceIdAsc();
    }

    public List<Resource> getAllResources(String name) {
        return resourceRepository.findAllByNameOrderByResourceIdAsc(name);
    }

    public List<Resource> getAllResources(Framework framework) {
        return resourceRepository.findAllByFrameworkOrderByResourceIdAsc(framework);
    }

    public List<Resource> getAllResources(Language language) {
        return resourceRepository.findAllByLanguageOrderByResourceIdAsc(language);
    }

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource destroyResource(Resource resource){
        resourceRepository.delete(resource);
        return resource;
    }
}
