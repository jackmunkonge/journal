package com.solirius.journal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solirius.journal.Service.FrameworkService;
import com.solirius.journal.Service.LanguageService;
import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import cucumber.steps.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.util.*;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FrameworkControllerTest {

    @Mock
    private FrameworkService frameworkService;

    @InjectMocks
    private FrameworkController frameworkController;

    @Mock
    private LanguageService languageService;

    @Autowired
    private MockMvc mockMvc;

    final private String ENDPOINT = "/frameworks";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private List<Framework> testFrameworks = new ArrayList<>();

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(frameworkController).build();

        Framework testFramework = new Framework();
        testFramework.setName("testFramework");
        testFramework.setFrameworkId(1);

        Framework testFramework2 = new Framework();
        testFramework2.setName("testFramework2");
        testFramework2.setFrameworkId(2);

        testFrameworks = Arrays.asList(testFramework, testFramework2);
        given(frameworkService.getAllFrameworks()).willReturn(testFrameworks);
        given(frameworkService.getFramework(anyString())).willReturn(Optional.ofNullable(testFramework));
        given(frameworkService.getFramework(anyInt())).willReturn(Optional.ofNullable(testFramework));
        given(languageService.getLanguage(anyInt())).willReturn(Optional.of(new Language()));
        given(languageService.getLanguage(anyString())).willReturn(Optional.of(new Language()));
        given(frameworkService.createFramework(any(Framework.class))).willReturn(new Framework());
        given(frameworkService.destroyFramework(any(Framework.class))).willReturn(new Framework());

    }

    @Test
    public void testGetFrameworkByNameReturns200Response() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/testFramework"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String foundFramework = mvcResult.getResponse().getContentAsString();
        Framework retrievedFramework = JsonUtil.toObjectFromJson(foundFramework, Framework.class);
        assertEquals("testFramework",retrievedFramework.getName());
    }

    @Test
    public void testGetFrameworkByIdReturns200Response() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + 10))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String foundFramework = mvcResult.getResponse().getContentAsString();
        Framework retrievedFramework = JsonUtil.toObjectFromJson(foundFramework, Framework.class);
        assertEquals(retrievedFramework.getName(),"testFramework");
    }

    @Test
    public void testGetFrameworkByNameReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getFramework(anyString())).willReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/testFramework"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testGetFrameworkByIdReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getFramework(anyInt())).willReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + 10))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testGetAllFrameworksReturns200Response() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<Framework> listFrames = MAPPER.readValue(result.getResponse().getContentAsString(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Framework.class));
        assertTrue(listFrames.size() == testFrameworks.size());
        assertEquals(listFrames.get(0).getName(),"testFramework");
        assertEquals(listFrames.get(1).getName(),"testFramework2");
    }

    @Test
    public void testGetAllFrameworksReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getAllFrameworks()).willReturn(Collections.emptyList());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testPostFrameworkReturns200Response() throws Exception{
        String postJson = JsonUtil.getJsonInput("test_framework");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT).content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testPostFrameworkReturnsNotFoundResponseIfItsLanguageNotFound() throws Exception{
        given(languageService.getLanguage(anyString())).willReturn(Optional.empty());
        String postJson = JsonUtil.getJsonInput("test_framework");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT).content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUpdateFrameworkByNameReturns200Response() throws Exception{
        String putJson = JsonUtil.getJsonInput("test_putframework");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/foo")
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals("testFrameworkUpdated",testFrameworks.get(0).getName());
    }

    @Test
    public void testUpdateFrameworkByIdReturns200Response() throws Exception{
        String putJson = JsonUtil.getJsonInput("test_putframework");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/" + 10)
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals("testFrameworkUpdated",testFrameworks.get(0).getName());
    }

    @Test
    public void testUpdateFrameworkByNameReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getFramework(anyString())).willReturn(Optional.empty());
        String putJson = JsonUtil.getJsonInput("test_putframework");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/foo")
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUpdateFrameworkByIdReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getFramework(anyInt())).willReturn(Optional.empty());
        String putJson = JsonUtil.getJsonInput("test_putframework");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/" + 10)
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testDeleteFrameworkByNameReturns200Response() throws Exception{
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/foo"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testDeleteFrameworkByIdReturns200Response() throws Exception{
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/" + 10))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testDeleteFrameworkByNameReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getFramework(anyString())).willReturn(Optional.empty());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/foo"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testDeleteFrameworkByIdReturnsNotFoundResponseIfNotFound() throws Exception{
        given(frameworkService.getFramework(anyInt())).willReturn(Optional.empty());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/" + 10))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
