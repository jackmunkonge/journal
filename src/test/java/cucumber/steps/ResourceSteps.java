package cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solirius.journal.Application;
import com.solirius.journal.model.Resource;
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
    private List<Resource> savedResources;
    private String endpoint;
    private Resource expectedResource;
    private int deletedResourceId = -1;


    private ResponseEntity<String> response;
    private RestTemplate restTemplate;
    private HttpHeaders header;

    private HttpEntity<String> noBodyRequest;
    private HttpEntity<String> frameworkRequest;
    private HttpEntity<String> languageRequest;
    private HttpEntity<String> libraryRequest;
    private HttpEntity<String> pluginRequest;
    private HttpEntity<String> principleRequest;
    private HttpEntity<String> resourceRequest;
    private HttpEntity<String> toolRequest;

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
    private ResponseEntity<String> httpMethod(String localEndpoint, HttpMethod method,HttpEntity<String> reqType) {
        return restTemplate.exchange(BASEURL + localEndpoint, method, reqType,String.class);
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

        // Request Bodies
        noBodyRequest = new HttpEntity<>("", header);
        frameworkRequest = new HttpEntity<>("ete_framework_post", header);
        languageRequest = new HttpEntity<>("ete_language_post", header);
        libraryRequest = new HttpEntity<>("ete_library_post", header);
        pluginRequest = new HttpEntity<>("ete_plugin_post", header);
        principleRequest = new HttpEntity<>("ete_principle_post", header);
        resourceRequest = new HttpEntity<>("ete_resource_post", header);
        toolRequest = new HttpEntity<>("ete_tool_post", header);

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
            frameworkRepository.deleteAllByName("testFramework");
            languageRepository.deleteAllByName("testLanguage");
            libraryRepository.deleteAllByName("testLibrary");
            pluginRepository.deleteAllByName("testPlugin");
            principleRepository.deleteAllByName("testPrinciple");
            toolRepository.deleteAllByName("testTool");


        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Given("^test objects posted$")
    public void testPrePosting() throws IOException {
        savedResources = new ArrayList<>();

        for(int i=1;i<3;i++) {
            response = httpMethod("/frameworks", HttpMethod.POST, frameworkRequest);
            response = httpMethod("/languages", HttpMethod.POST, languageRequest);
            response = httpMethod("/libraries", HttpMethod.POST, libraryRequest);
            response = httpMethod("/plugins", HttpMethod.POST, pluginRequest);
            response = httpMethod("/principles", HttpMethod.POST, principleRequest);
            response = httpMethod("/tools", HttpMethod.POST, toolRequest);
        }

        for(int i=0;i<5;i++) {
            resExtract.setName(resExtract.getName() + i);
            String resourceJson = JsonUtil.toJson(resExtract);
            resRequest = new HttpEntity<>(resourceJson, header);
            response = httpMethod("/resources",HttpMethod.POST, resourceRequest);
            savedResources.add(JsonUtil.toObjectFromJson(response.getBody(), Resource.class));
        }
        resExtract.setName("testResource");
    }

    @And("^use resource number (\\d+)$")
    public void useResourceNumber(int resourceNum){
        expectedResource = savedResources.get(resourceNum);
    }


    // Resource Step Definitions
    @Given("^a request for a (.*)$")
    public void aRequestForA(String reqType) {
        switch(reqType) {
            case "resource":
                endpoint = "/resources";
                break;
            case "framework":
                endpoint = "/frameworks";
                break;
            case "language":
                endpoint = "/languages";
                break;
        }
    }

    @When("^an? (.*) request is made$")
    public void aGetRequestIsMade(String reqType) {
        switch (reqType) {
            case "get":
                try {
                    response = httpMethod(endpoint,HttpMethod.GET,noBodyRequest);
                } catch(HttpClientErrorException e) { }
                break;
            case "ete_post":
                response = httpMethod(endpoint,HttpMethod.POST,resRequest);
                break;
            case "update":
                response = httpMethod(endpoint,HttpMethod.PUT,putResRequest);
                break;
            case "delete":
                response = httpMethod(endpoint,HttpMethod.DELETE,noBodyRequest);
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
        List<Resource> listres = MAPPER.readValue(response.getBody(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Resource.class));

        List<Resource> filteredList = listres.stream()
                .filter(res -> res.getName()
                        .contains(resExtract.getName())).collect(Collectors.toList());
        assertEquals(savedResources.size(), filteredList.size());
    }

    @Then("^the same resource is retrieved$")
    public void sameResource() throws IOException {
        Resource fetchedResource = MAPPER.readValue(response.getBody(),Resource.class);
        assertEquals(expectedResource.getResourceId(),fetchedResource.getResourceId());
    }

    @Then("^a list of the language's resources are retrieved$")
    public void allLanguageResourcesRetrieved() throws IOException {
        List<Resource> listres = MAPPER.readValue(response.getBody(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Resource.class));

        List<Resource> filteredList = listres.stream()
                .filter(res -> res.getLanguage().getName()
                        .contains(langExtract.getName())).collect(Collectors.toList());
        assertEquals(savedResources.size(), filteredList.size());
    }

    @Then("^a list of the framework's resources are retrieved$")
    public void allFrameworkResourcesRetrieved() throws IOException {
        List<Resource> listres = MAPPER.readValue(response.getBody(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Resource.class));

        List<Resource> filteredList = listres.stream()
                .filter(res -> res.getFramework().getName()
                        .contains(frameExtract.getName())).collect(Collectors.toList());
        assertEquals(savedResources.size(), filteredList.size());
    }

    @Then("^the url is correctly changed$")
    public void updateSuccess() throws IOException {
        Resource updatedRes = MAPPER.readValue(response.getBody(),Resource.class);
        assertEquals(updateBodyExtract.getUrl(),updatedRes.getUrl());
    }

    @And("^search resource by (.*) ID$")
    public void searchByID(String reqType) {
        switch (reqType) {
            case "resource":
                endpoint = endpoint + "/" + expectedResource.getResourceId();
                break;
            case "language":
                endpoint = endpoint + "/language/" + expectedResource.getLanguage().getLanguageId();
                break;
            case "framework":
                endpoint = endpoint + "/framework/" + expectedResource.getFramework().getFrameworkId();
                break;
        }

    }

    @And("^search resource by (.*) name$")
    public void searchByName(String reqType) {
        switch (reqType) {
            case "resource":
                endpoint = endpoint + "/" + expectedResource.getName();
                break;
            case "language":
                endpoint = endpoint + "/language/" + expectedResource.getLanguage().getName();
                break;
            case "framework":
                endpoint = endpoint + "/framework/" + expectedResource.getFramework().getName();
                break;
        }
    }

}
