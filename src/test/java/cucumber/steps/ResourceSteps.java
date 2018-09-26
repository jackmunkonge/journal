package cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solirius.journal.Application;
import com.solirius.journal.model.*;
import com.solirius.journal.repository.*;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@ContextConfiguration
@SpringBootTest(classes=Application.class)
public class ResourceSteps {

    // Fields
    private static final ObjectMapper MAPPER = new ObjectMapper();
    final private String BASEURL = "http://localhost:8080";
    private String endpoint = "/resources";

    private List<Framework> savedFrameworks;
    private List<Language> savedLanguages;
    private List<Library> savedLibraries;
    private List<Plugin> savedPlugins;
    private List<Principle> savedPrinciples;
    private List<Resource> savedResources;
    private List<Tool> savedTools;

    private Framework expectedFramework;
    private Language expectedLanguage;
    private Library expectedLibrary;
    private Plugin expectedPlugin;
    private Principle expectedPrinciple;
    private Resource expectedResource;
    private Tool expectedTool;

    private Resource updatedResource;
    private int deletedResourceId = -1;


    private ResponseEntity<String> response;
    private RestTemplate restTemplate;
    private HttpHeaders header;

    @Autowired
    private FrameworkRepository frameworkRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private PrincipleRepository principleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ToolRepository toolRepository;


    // Custom Methods
    private ResponseEntity<String> httpMethod(String localEndpoint, HttpMethod method, HttpEntity<String> reqType) {
        return restTemplate.exchange(BASEURL + localEndpoint, method, reqType, String.class);
    }


    // Background
    @Before
    public void setup() throws IOException {

        // Header
        restTemplate = new RestTemplate();
        header = new HttpHeaders();
        if(header.isEmpty()) {
            header.add("Content-Type", "application/json");
        }
    }

    @After
    public void cleanup() {
        try {
            for(Resource object : savedResources) {
                if(deletedResourceId == -1) {
                    resourceRepository.deleteById(object.getResourceId());
                } else if(deletedResourceId != object.getResourceId()) {
                    resourceRepository.deleteById(object.getResourceId());
                }
            }

            for(int i=1;i<=2;i++) {
                frameworkRepository.deleteByName("testFramework" + i);
                languageRepository.deleteByName("testLanguage" + i);
                libraryRepository.deleteByName("testLibrary" + i);
                pluginRepository.deleteByName("testPlugin" + i);
                principleRepository.deleteByName("testPrinciple" + i);
                toolRepository.deleteByName("testTool" + i);
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Given("^test objects posted$")
    public void testPrePosting() throws IOException {
        savedResources = new ArrayList<>();

        for(int i=1;i<=2;i++) {
            response = httpMethod("/frameworks", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_framework" + i + "_post"), header));
            savedFrameworks.add(JsonUtil.toObjectFromJson(response.getBody(), Framework.class));
            response = httpMethod("/languages", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_language" + i + "_post"), header));
            savedLanguages.add(JsonUtil.toObjectFromJson(response.getBody(), Language.class));
            response = httpMethod("/libraries", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_library" + i + "_post"), header));
            savedLibraries.add(JsonUtil.toObjectFromJson(response.getBody(), Library.class));
            response = httpMethod("/plugins", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_post/ete_plugin" + i + "_post"), header));
            savedPlugins.add(JsonUtil.toObjectFromJson(response.getBody(), Plugin.class));
            response = httpMethod("/principles", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_post/ete_principle" + i + "_post"), header));
            savedPrinciples.add(JsonUtil.toObjectFromJson(response.getBody(), Principle.class));
            response = httpMethod("/tools", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_post/ete_tool" + i + "_post"), header));
            savedTools.add(JsonUtil.toObjectFromJson(response.getBody(), Tool.class));
        }

        for(int i=1;i<=3;i++) {
            response = httpMethod("/resources", HttpMethod.POST, new HttpEntity<>(JsonUtil.getJsonInput("ete_post/ete_resource" + i + "_post"), header));
            savedResources.add(JsonUtil.toObjectFromJson(response.getBody(), Resource.class));
        }
    }

    @And("^use (.*) number (\\d+)$")
    public void useResourceNumber(String reqType, int resourceNum){
        switch(reqType) {
            case "framework":
                expectedFramework = savedFrameworks.get(resourceNum);
                break;
            case "language":
                expectedLanguage = savedLanguages.get(resourceNum);
                break;
            case "library":
                expectedLibrary = savedLibraries.get(resourceNum);
                break;
            case "plugin":
                expectedPlugin = savedPlugins.get(resourceNum);
                break;
            case "principle":
                expectedPrinciple = savedPrinciples.get(resourceNum);
                break;
            case "resource":
                expectedResource = savedResources.get(resourceNum);
                break;
            case "tool":
                expectedTool = savedTools.get(resourceNum);
                break;
        }
    }

    @When("^an? (.*) request is made$")
    public void aGetRequestIsMade(String reqType) throws IOException {
        switch (reqType) {
            case "get":
                try {
                    response = httpMethod(endpoint, HttpMethod.GET, new HttpEntity<>("", header));
                } catch(HttpClientErrorException e) { }
                break;
            case "update":
                response = httpMethod(endpoint, HttpMethod.PUT, new HttpEntity<>(JsonUtil.getJsonInput("ete_post/ete_resource_put"), header));
                updatedResource = JsonUtil.toObjectFromJson(response.getBody(), Resource.class);
                break;
            case "delete":
                response = httpMethod(endpoint, HttpMethod.DELETE, new HttpEntity<>("", header));
                deletedResourceId = expectedResource.getResourceId();
                break;
        }
    }

    @And("^response code is (?:successful|unsuccessful)$")
    public void responseCode() {
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Then("^a list of all resources are retrieved$")
    public void allResourcesRetrieved() throws IOException {
        List<Resource> listResources = MAPPER.readValue(response.getBody(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Resource.class));

        List<Resource> filteredList = listResources.stream()
                .filter(resource -> resource.getName()
                        .contains(expectedResource.getName())).collect(Collectors.toList());
        assertEquals(savedResources.size(), filteredList.size());
    }

    @Then("^the same resource is retrieved$")
    public void sameResource() throws IOException {
        Resource fetchedResource = MAPPER.readValue(response.getBody(),Resource.class);
        assertEquals(expectedResource.getResourceId(),fetchedResource.getResourceId());
    }

    @Then("^a list of the (.*)'s resources are retrieved$")
    public void aListOfResourcesRetrieved(String reqType) throws IOException {
        List<Resource> listResources = MAPPER.readValue(response.getBody(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Resource.class));

        switch(reqType) {
            case "framework":
                assertEquals(expectedFramework.getResources(), listResources);
                break;
            case "language":
                assertEquals(expectedLanguage.getResources(), listResources);
                break;
            case "library":
                assertEquals(expectedLibrary.getResources(), listResources);
                break;
            case "plugin":
                assertEquals(expectedPlugin.getResources(), listResources);
                break;
            case "principle":
                assertEquals(expectedPrinciple.getResources(), listResources);
                break;
            case "tool":
                assertEquals(expectedTool.getResources(), listResources);
                break;
        }
    }

    @Then("^the url is correctly changed$")
    public void updateSuccess() throws IOException {
        Resource resource = MAPPER.readValue(response.getBody(), Resource.class);
        assertEquals(updatedResource.getUrl(), resource.getUrl());
    }

    @And("^search resource by (.*) ID$")
    public void searchByID(String reqType) {
        switch (reqType) {
            case "framework":
                endpoint = endpoint + "/framework/" + expectedFramework.getFrameworkId();
                break;
            case "language":
                endpoint = endpoint + "/language/" + expectedLanguage.getLanguageId();
                break;
            case "library":
                endpoint = endpoint + "/library/" + expectedLibrary.getLibraryId();
                break;
            case "plugin":
                endpoint = endpoint + "/plugin/" + expectedPlugin.getPluginId();
                break;
            case "principle":
                endpoint = endpoint + "/principle/" + expectedPrinciple.getPrincipleId();
                break;
            case "resource":
                endpoint = endpoint + "/" + expectedResource.getResourceId();
                break;
            case "tool":
                endpoint = endpoint + "/tool/" + expectedTool.getToolId();
                break;
        }
    }

    @And("^search resource by (.*) name$")
    public void searchByName(String reqType) {
        switch (reqType) {
            case "framework":
                endpoint = endpoint + "/framework/" + expectedFramework.getName();
                break;
            case "language":
                endpoint = endpoint + "/language/" + expectedLanguage.getName();
                break;
            case "library":
                endpoint = endpoint + "/library/" + expectedLibrary.getName();
                break;
            case "plugin":
                endpoint = endpoint + "/plugin/" + expectedPlugin.getName();
                break;
            case "principle":
                endpoint = endpoint + "/principle/" + expectedPrinciple.getName();
                break;
            case "resource":
                endpoint = endpoint + "/" + expectedResource.getName();
                break;
            case "tool":
                endpoint = endpoint + "/tool/" + expectedTool.getName();
                break;
        }
    }

}
