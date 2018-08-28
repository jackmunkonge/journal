package com.solirius.journal.service;

import com.solirius.journal.Service.ResourceService;
import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.domain.Resource;
import com.solirius.journal.repository.FrameworkRepository;
import com.solirius.journal.repository.LanguageRepository;
import com.solirius.journal.repository.ResourceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
public class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private FrameworkRepository frameworkRepository;

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private ResourceService resourceService;

    private Resource testResource;
    private List<Resource> testResources;

    @Before
    public void setup() {
        testResource = new Resource();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");
        testResource.setResourceId(1);
        testResources = Arrays.asList(testResource);
        
        given(resourceRepository.findAllByOrderByResourceIdAsc()).willReturn(testResources);
        given(resourceRepository.findAllByNameOrderByResourceIdAsc(anyString())).willReturn(testResources);
        given(resourceRepository.findAllByFrameworkOrderByResourceIdAsc(any(Framework.class))).willReturn(testResources);
        given(resourceRepository.findAllByLanguageOrderByResourceIdAsc(any(Language.class))).willReturn(testResources);
        given(resourceRepository.findById(anyInt())).willReturn(Optional.ofNullable(testResource));
        given(resourceRepository.findByName(anyString())).willReturn(Optional.ofNullable(testResource));
        given(frameworkRepository.findByName(anyString())).willReturn(Optional.ofNullable(new Framework()));
        given(languageRepository.findByName(anyString())).willReturn(Optional.ofNullable(new Language()));
        given(resourceRepository.save(any(Resource.class))).willReturn(testResource);
        doNothing().when(resourceRepository).delete(any(Resource.class));
    }

    @Test
    public void testGetResourceByName() {
        Optional<Resource> foo = resourceService.getResource("foo");
        assertEquals(testResource,foo.get());
    }

    @Test
    public void testGetResourceById() {
        Optional<Resource> foo = resourceService.getResource(10);
        assertEquals(testResource,foo.get());
    }

    @Test
    public void testGetAllResources() {
        List<Resource> foos = resourceService.getAllResources();
        assertEquals(testResource,foos.get(0));
        assertEquals(1,foos.size());
    }

    @Test
    public void testGetAllResourcesByName() {
        List<Resource> foos = resourceService.getAllResources("foo");
        assertEquals(testResource,foos.get(0));
        assertEquals(1,foos.size());
    }

    @Test
    public void testGetAllResourcesByFramework() {
        List<Resource> foos = resourceService.getAllResources(new Framework());
        assertEquals(testResource,foos.get(0));
        System.out.println(foos.get(0));
        assertEquals(1,foos.size());
    }

    @Test
    public void testGetAllResourcesByLanguage() {
        List<Resource> foos = resourceService.getAllResources(new Language());
        assertEquals(testResource,foos.get(0));
        assertEquals(1,foos.size());
    }

    @Test
    public void testCreateResource() {
        Resource foo = resourceService.createResource(new Resource());
        assertEquals(testResource,foo);
    }

    @Test
    public void testDeleteResource() {
        Resource foo = resourceService.destroyResource(testResource);
        assertEquals(testResource,foo);
    }
}
