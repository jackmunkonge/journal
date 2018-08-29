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
public class LanguageControllerTest {

    @Mock
    private FrameworkService frameworkService;

    @InjectMocks
    private LanguageController languageController;

    @Mock
    private LanguageService languageService;

    @Autowired
    private MockMvc mockMvc;

    final private String ENDPOINT = "/languages";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private List<Language> testLanguages = new ArrayList<>();

    private Language testLanguage;

    private Language testLanguage2;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(languageController).build();

        testLanguage = new Language();
        testLanguage.setName("testLanguage");
        testLanguage.setLanguageId(1);

        testLanguage2 = new Language();
        testLanguage2.setName("testLanguage2");
        testLanguage2.setLanguageId(2);

        testLanguages = Arrays.asList(testLanguage, testLanguage2);
        given(languageService.getAllLanguages()).willReturn(testLanguages);
        given(languageService.getLanguage(anyString())).willReturn(Optional.ofNullable(testLanguage));
        given(languageService.getLanguage(anyInt())).willReturn(Optional.ofNullable(testLanguage));
        given(languageService.createLanguage(any(Language.class))).willReturn(testLanguage);
        given(languageService.destroyLanguage(any(Language.class))).willReturn(testLanguage);

    }

    @Test
    public void testGetLanguageByNameReturns200Response() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/testLanguage"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String foundLanguage = mvcResult.getResponse().getContentAsString();
        Language retrievedLanguage = JsonUtil.toObjectFromJson(foundLanguage, Language.class);
        assertEquals("testLanguage",retrievedLanguage.getName());
    }

    @Test
    public void testGetLanguageByIdReturns200Response() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + 10))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String foundLanguage = mvcResult.getResponse().getContentAsString();
        Language retrievedLanguage = JsonUtil.toObjectFromJson(foundLanguage, Language.class);
        assertEquals(retrievedLanguage.getName(),"testLanguage");
    }

    @Test
    public void testGetLanguageByNameReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getLanguage(anyString())).willReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/testLanguage"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testGetLanguageByIdReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getLanguage(anyInt())).willReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + 10))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testGetAllLanguagesReturns200Response() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<Language> listLanguages = MAPPER.readValue(result.getResponse().getContentAsString(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Language.class));
        assertEquals(listLanguages.size(), testLanguages.size());
        assertEquals(listLanguages.get(0).getName(),"testLanguage");
        assertEquals(listLanguages.get(1).getName(),"testLanguage2");
    }

    @Test
    public void testGetAllLanguagesReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getAllLanguages()).willReturn(Collections.emptyList());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testPostLanguageReturns200Response() throws Exception{
        String postJson = JsonUtil.getJsonInput("test_language");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT).content(postJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Language retrLanguage = MAPPER.readValue(result.getResponse().getContentAsString(), Language.class);
        assertEquals(testLanguages.get(0).getName(),retrLanguage.getName());
    }

    @Test
    public void testUpdateLanguageByNameReturns200Response() throws Exception{
        String putJson = JsonUtil.getJsonInput("test_putlanguage");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/foo")
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals("testLanguageUpdated",testLanguages.get(0).getName());
    }

    @Test
    public void testUpdateLanguageByIdReturns200Response() throws Exception{
        String putJson = JsonUtil.getJsonInput("test_putlanguage");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/" + 10)
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals("testLanguageUpdated",testLanguages.get(0).getName());
    }

    @Test
    public void testUpdateLanguageByNameReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getLanguage(anyString())).willReturn(Optional.empty());
        String putJson = JsonUtil.getJsonInput("test_putlanguage");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/foo")
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUpdateLanguageByIdReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getLanguage(anyInt())).willReturn(Optional.empty());
        String putJson = JsonUtil.getJsonInput("test_putlanguage");

        MvcResult testing = mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/" + 10)
                .content(putJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testDeleteLanguageByNameReturns200Response() throws Exception{
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/foo"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Language retrLanguage = MAPPER.readValue(result2.getResponse().getContentAsString(), Language.class);
        assertEquals(testLanguages.get(0).getName(),retrLanguage.getName());
    }

    @Test
    public void testDeleteLanguageByIdReturns200Response() throws Exception{
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/" + 10))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Language retrLanguage = MAPPER.readValue(result2.getResponse().getContentAsString(), Language.class);
        assertEquals(testLanguages.get(0).getName(),retrLanguage.getName());
    }

    @Test
    public void testDeleteLanguageByNameReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getLanguage(anyString())).willReturn(Optional.empty());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/foo"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testDeleteLanguageByIdReturnsNotFoundResponseIfNotFound() throws Exception{
        given(languageService.getLanguage(anyInt())).willReturn(Optional.empty());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/" + 10))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
