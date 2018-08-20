package com.solirius.journal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.solirius.journal.Service.FrameworkService;
import com.solirius.journal.Service.LanguageService;
import com.solirius.journal.Service.ResourceService;
import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.domain.Resource;
import cucumber.steps.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.text.html.Option;
import javax.xml.ws.Response;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.framework;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @Mock
    private FrameworkService frameworkService;

    @Mock
    private LanguageService languageService;

    @Autowired
    private MockMvc mockMvc;

    final private String ENDPOINT = "/resources";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private List<Resource> testResources = new ArrayList<>();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(resourceController).build();

        Resource testResource = new Resource();
        testResource.setName("testResource");
        testResource.setUrl("http://testResource.com");
        testResource.setResourceId(1);

        Resource testResource2 = new Resource();
        testResource2.setName("testResource2");
        testResource2.setUrl("http://testResource.com");
        testResource2.setResourceId(2);

        testResources = Arrays.asList(testResource, testResource2);
        given(resourceService.createResource(new Resource())).willReturn(testResource);
        given(resourceService.getAllResources()).willReturn(testResources);
        given(resourceService.getResource(anyInt())).willReturn(Optional.ofNullable(testResource));
        given(resourceService.getResource(anyString())).willReturn(Optional.ofNullable(testResource));
        given(languageService.getLanguage(anyInt())).willReturn(Optional.ofNullable(new Language()));
        given(frameworkService.getFramework(anyInt())).willReturn(Optional.ofNullable(new Framework()));
        given(languageService.getLanguage(anyString())).willReturn(Optional.ofNullable(new Language()));
        given(frameworkService.getFramework(anyString())).willReturn(Optional.ofNullable(new Framework()));
        given(resourceService.destroyResource(any(Resource.class))).willReturn(testResource);
    }

    @Test
    public void testGetAllResourcesReturns200Response() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                            .andExpect(status().is2xxSuccessful())
                            .andReturn();
        List<Resource> listres = MAPPER.readValue(result.getResponse().getContentAsString(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Resource.class));
        assertTrue(listres.size() == testResources.size());
        assertEquals(listres.get(0).getName(),"testResource");
        assertEquals(listres.get(1).getName(),"testResource2");
    }

    @Test
    public void testGetAllResourcesReturnsNotFoundResponseIfNotFound() throws Exception {
        given(resourceService.getAllResources()).willReturn(Collections.emptyList());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testGetResourceByNameReturns200Response() throws Exception {
        given(resourceService.getResource(anyString())).willReturn(Optional.ofNullable(testResources.get(0)));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/testResource"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String foundRes = mvcResult.getResponse().getContentAsString();
        Resource retrievedRes = JsonUtil.toObjectFromJson(foundRes, Resource.class);
        assertEquals(retrievedRes.getName(),"testResource");
    }

    @Test
    public void testGetResourceByNameReturnsNotFoundResponseIfNoResourceFound() throws Exception {
        given(resourceService.getResource(anyString())).willReturn(Optional.empty());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/testResource"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testGetResourceByIdReturns200Response() throws Exception {
        given(resourceService.getResource(anyInt())).willReturn(Optional.ofNullable(testResources.get(0)));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + 1))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String foundRes = mvcResult.getResponse().getContentAsString();
        Resource retrievedRes = JsonUtil.toObjectFromJson(foundRes, Resource.class);
        assertEquals(retrievedRes.getName(), testResources.get(0).getName());
    }

    @Test
    public void testGetResourceByIdReturnsNotFoundResponseIfNoResourceFound() throws Exception {
        given(resourceService.getResource(anyInt())).willReturn(Optional.empty());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + 10))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUpdateResourceByNameReturns200Response() throws Exception {
        String putJson = JsonUtil.getJsonInput("test_putresource");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/SOmething")
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals("updated",testResources.get(0).getUrl());
    }

    @Test
    public void testUpdateResourceByIdReturns200Response() throws Exception {
        String putJson = JsonUtil.getJsonInput("test_putresource");

        MvcResult testing2 = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/" + 10)
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals("updated",testResources.get(0).getUrl());
    }

    @Test
    public void testUpdateResourceByNameReturnsNotFoundResponseIfNotFound() throws Exception {
        given(resourceService.getResource(anyString())).willReturn(Optional.empty());
        String putJson = JsonUtil.getJsonInput("test_putresource");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/foo")
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUpdateResourceByIdReturnsNotFoundResponseIfNotFound() throws Exception {
        given(resourceService.getResource(anyInt())).willReturn(Optional.empty());
        String putJson = JsonUtil.getJsonInput("test_putresource");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/" + 10)
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testPostResourceReturns200Response() throws Exception {
        String postJson = JsonUtil.getJsonInput("test_resource");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT).content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testPostResourceReturnsNotFoundResponseDueToFramework() throws Exception {
        given(frameworkService.getFramework(anyString())).willReturn(Optional.empty());
        String postJson = JsonUtil.getJsonInput("test_resource");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT).content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testPostResourceReturnsNotFoundResponseDueToLanguage() throws Exception {
        given(languageService.getLanguage(anyString())).willReturn(Optional.empty());
        String postJson = JsonUtil.getJsonInput("test_resource");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT).content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testDelResourceByNameReturns200Response() throws Exception {
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/foo"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testDelResourceByIdReturns200Response() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/" + 10))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testDelResourceByNameReturnsNotFoundResponseIfNoResourceFound() throws Exception {
        given(resourceService.getResource(anyString())).willReturn(Optional.empty());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/foo"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testDelResourceByIdReturnsNotFoundResponseIfNoResourceFound() throws Exception {
        given(resourceService.getResource(anyInt())).willReturn(Optional.empty());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/" + 10))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
