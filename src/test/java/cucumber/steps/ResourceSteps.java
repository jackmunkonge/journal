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
    private List<Resource> savedResources;
    private String endpoint = "/resources";
    private Resource expectedResource;
    private int deletedResourceId = -1;


    private ResponseEntity<String> response;
    private RestTemplate restTemplate;
    private HttpHeaders header;

    private HttpEntity<String> noBodyRequest;

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

        // Get Request Body
        noBodyRequest = new HttpEntity<>("", header);
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
            response = httpMethod("/frameworks", HttpMethod.POST, new HttpEntity<>("ete_framework" + i + "_post", header));
            response = httpMethod("/languages", HttpMethod.POST, new HttpEntity<>("ete_language" + i + "_post", header));
            response = httpMethod("/libraries", HttpMethod.POST, new HttpEntity<>("ete_library" + i + "_post", header));
            response = httpMethod("/plugins", HttpMethod.POST, new HttpEntity<>("ete_plugin" + i + "_post", header));
            response = httpMethod("/principles", HttpMethod.POST, new HttpEntity<>("ete_principle" + i + "_post", header));
            response = httpMethod("/tools", HttpMethod.POST, new HttpEntity<>("ete_tool" + i + "_post", header));
        }

        for(int i=1;i<=3;i++) {
            response = httpMethod("/resources", HttpMethod.POST, new HttpEntity<>("ete_resource" + i + "_post", header));
            savedResources.add(JsonUtil.toObjectFromJson(response.getBody(), Resource.class));
        }
    }

    @And("^use (.*) number (\\d+)$")/////////////////////////////////////////////////////////WORKING HERE
    public void useResourceNumber(String reqType, int resourceNum){
        switch() {
            case "":

                break;
        }
        expectedResource = savedResources.get(resourceNum);
    }

    @When("^an? (.*) request is made$")
    public void aGetRequestIsMade(String reqType) {
        switch (reqType) {
            case "get":
                try {
                    response = httpMethod(endpoint, HttpMethod.GET, noBodyRequest);
                } catch(HttpClientErrorException e) { }
                break;
            case "update":
                response = httpMethod(endpoint, HttpMethod.PUT, new HttpEntity<>("ete_resource_put", header));
                break;
            case "delete":
                response = httpMethod(endpoint, HttpMethod.DELETE, noBodyRequest);
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
                assertTrue(expectedResource.getFrameworks().get(0), listResources.contains());
                break;
            case "language":
                clazz = Language.class;
                break;
            case "library":
                clazz = Library.class;
                break;
            case "plugin":
                clazz = Plugin.class;
                break;
            case "principle":
                clazz = Principle.class;
                break;
            case "tool":
                clazz = Tool.class;
                break;
        }



        listResources ==
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

    @And("^search resource by (.*) name$")////////////////////////////////////////////////////////////////////WORKING HERE
    public void searchByName(String reqType) {
        switch (reqType) {
            case "resource":
                endpoint = endpoint + "/" + expectedResource.getName();
                break;
            case "framework":
                endpoint = endpoint + "/framework/" + expectedResource.getFrameworks().get(0).getName();
                break;
            case "language":
                endpoint = endpoint + "/language/" + expectedResource.getLanguage().getName();
                break;
        }
    }

}
