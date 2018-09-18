package com.solirius.journal.Service;

import com.solirius.journal.model.Resource;
import com.solirius.journal.repository.ResourceRepository;
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

    public Optional<Resource> getResource(Integer resourceId) {
        return resourceRepository.findById(resourceId);
    }

    public Optional<Resource> getResource(String resourceName) {
        return resourceRepository.findByName(resourceName);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAllByOrderByResourceIdAsc();
    }

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource destroyResource(Resource resource) {
        resourceRepository.delete(resource);
        return resource;
    }
}
