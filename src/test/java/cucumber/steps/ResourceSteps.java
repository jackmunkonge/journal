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

    private List<Framework> savedFrameworks = new ArrayList<>();
    private List<Language> savedLanguages = new ArrayList<>();
    private List<Library> savedLibraries = new ArrayList<>();
    private List<Plugin> savedPlugins = new ArrayList<>();
    private List<Principle> savedPrinciples = new ArrayList<>();
    private List<Resource> savedResources = new ArrayList<>();
    private List<Tool> savedTools = new ArrayList<>();

    private Framework expectedFramework;
    private Language expectedLanguage;
    private Library expectedLibrary;
    private Plugin expectedPlugin;
    private Principle expectedPrinciple;
    private Resource expectedResource;
    private Tool expectedTool;

    private List<HttpEntity<String>> frameworkPostRequests = new ArrayList<>();
    private List<HttpEntity<String>> languagePostRequests = new ArrayList<>();
    private List<HttpEntity<String>> libraryPostRequests = new ArrayList<>();
    private List<HttpEntity<String>> pluginPostRequests = new ArrayList<>();
    private List<HttpEntity<String>> principlePostRequests = new ArrayList<>();
    private List<HttpEntity<String>> resourcePostRequests = new ArrayList<>();
    private HttpEntity<String> resourcePutRequest;
    private List<HttpEntity<String>> toolPostRequests = new ArrayList<>();

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
    private ResponseEntity<String> httpMethod(String localEndpoint, HttpMethod method, HttpEntity<String> req) {
        return restTemplate.exchange(BASEURL + localEndpoint, method, req, String.class);
    }


    // Background
    @Before
    public void setup() {
        // Header
        restTemplate = new RestTemplate();
        header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        // Set Request Bodies

        resourcePutRequest = new HttpEntity<>("/ete_put/ete_resource_put", header);

        frameworkPostRequests.add(new HttpEntity<>("/ete_post/ete_framework_post"+ 1, header));
        languagePostRequests.add(new HttpEntity<>("/ete_post/ete_language_post"+ 1, header));
        libraryPostRequests.add(new HttpEntity<>("/ete_post/ete_library_post"+ 1, header));
        pluginPostRequests.add(new HttpEntity<>("/ete_post/ete_plugin_post"+ 1, header));
        principlePostRequests.add(new HttpEntity<>("/ete_post/ete_principle_post"+ 1, header));
        toolPostRequests.add(new HttpEntity<>("/ete_post/ete_tool_post"+ 1, header));

        frameworkPostRequests.add(new HttpEntity<>("/ete_post/ete_framework_post"+ 2, header));
        languagePostRequests.add(new HttpEntity<>("/ete_post/ete_language_post"+ 2, header));
        libraryPostRequests.add(new HttpEntity<>("/ete_post/ete_library_post"+ 2, header));
        pluginPostRequests.add(new HttpEntity<>("/ete_post/ete_plugin_post"+ 2, header));
        principlePostRequests.add(new HttpEntity<>("/ete_post/ete_principle_post"+ 2, header));
        toolPostRequests.add(new HttpEntity<>("/ete_post/ete_tool_post"+ 2, header));

        resourcePostRequests.add(new HttpEntity<>("/ete_post/ete_resource_post"+ 1, header));
        resourcePostRequests.add(new HttpEntity<>("/ete_post/ete_resource_post"+ 2, header));
        resourcePostRequests.add(new HttpEntity<>("/ete_post/ete_resource_post"+ 3, header));
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
                frameworkRepository.deleteByName(savedFrameworks.get(i).getName());
                languageRepository.deleteByName(savedLanguages.get(i).getName());
                libraryRepository.deleteByName(savedLibraries.get(i).getName());
                pluginRepository.deleteByName(savedPlugins.get(i).getName());
                principleRepository.deleteByName(savedPrinciples.get(i).getName());
                toolRepository.deleteByName(savedTools.get(i).getName());
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Given("^test objects posted$")
    public void testPrePosting() throws IOException {
        for(int i=0;i<=1;i++) {
            response = httpMethod("/frameworks", HttpMethod.POST, frameworkPostRequests.get(i));
            savedFrameworks.add(JsonUtil.toObjectFromJson(response.getBody(), Framework.class));
            response = httpMethod("/languages", HttpMethod.POST, languagePostRequests.get(i));
            savedLanguages.add(JsonUtil.toObjectFromJson(response.getBody(), Language.class));
            response = httpMethod("/libraries", HttpMethod.POST, libraryPostRequests.get(i));
            savedLibraries.add(JsonUtil.toObjectFromJson(response.getBody(), Library.class));
            response = httpMethod("/plugins", HttpMethod.POST, pluginPostRequests.get(i));
            savedPlugins.add(JsonUtil.toObjectFromJson(response.getBody(), Plugin.class));
            response = httpMethod("/principles", HttpMethod.POST, principlePostRequests.get(i));
            savedPrinciples.add(JsonUtil.toObjectFromJson(response.getBody(), Principle.class));
            response = httpMethod("/tools", HttpMethod.POST, toolPostRequests.get(i));
            savedTools.add(JsonUtil.toObjectFromJson(response.getBody(), Tool.class));
        }

        for(int i=1;i<=3;i++) {
            response = httpMethod("/resources", HttpMethod.POST, resourcePostRequests.get(i));
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
                response = httpMethod(endpoint, HttpMethod.PUT, resourcePutRequest);
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
        Resource fetchedResource = MAPPER.readValue(response.getBody(), Resource.class);
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
