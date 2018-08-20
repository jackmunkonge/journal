package cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solirius.journal.Application;
import com.solirius.journal.controller.resource.FrameworkCreateRequest;
import com.solirius.journal.controller.resource.LanguageCreateRequest;
import com.solirius.journal.controller.resource.ResourceCreateRequest;
import com.solirius.journal.domain.Framework;
import com.solirius.journal.domain.Language;
import com.solirius.journal.domain.Resource;
import com.solirius.journal.repository.FrameworkRepository;
import com.solirius.journal.repository.LanguageRepository;
import com.solirius.journal.repository.ResourceRepository;
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
    private String endpoint = "/resources";
    private Resource expectedResource;
    private int deletedResourceId = -1;


    private ResponseEntity<String> response;
    private RestTemplate restTemplate;
    private HttpHeaders header;

    private String testLanguageStr;
    private String testFrameworkStr;
    private String testResourceStr;
    private String putJson;
    private String putFrame;

    private HttpEntity<String> langRequest;
    private HttpEntity<String> frameRequest;
    private HttpEntity<String> resRequest;
    private HttpEntity<String> noBodyRequest;
    private HttpEntity<String> putResRequest;

    private ResourceCreateRequest resExtract;
    private FrameworkCreateRequest frameExtract;
    private LanguageCreateRequest langExtract;
    private ResourceCreateRequest updateBodyExtract;


    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    FrameworkRepository frameworkRepository;

    @Autowired
    LanguageRepository languageRepository;


    // Custom Methods
    private ResponseEntity<String> httpMethod(String localEndpoint, HttpMethod method,HttpEntity<String> reqType){
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
        testLanguageStr = JsonUtil.getJsonInput("ete_language");
        testFrameworkStr = JsonUtil.getJsonInput("ete_framework");
        testResourceStr = JsonUtil.getJsonInput("ete_resource");
        putJson = JsonUtil.getJsonInput("ete_putreq");

        langRequest = new HttpEntity<>(testLanguageStr, header);
        frameRequest = new HttpEntity<>(testFrameworkStr, header);
        resRequest = new HttpEntity<>(testResourceStr, header);
        noBodyRequest = new HttpEntity<>("", header);
        putResRequest = new HttpEntity<>(putJson, header);

        // Json File Field-Extractions
        resExtract = JsonUtil.toObjectFromJson(JsonUtil.getJsonInput("ete_resource"),ResourceCreateRequest.class);
        frameExtract = JsonUtil.toObjectFromJson(JsonUtil.getJsonInput("ete_framework"),FrameworkCreateRequest.class);
        langExtract = JsonUtil.toObjectFromJson(JsonUtil.getJsonInput("ete_language"),LanguageCreateRequest.class);
        updateBodyExtract = JsonUtil.toObjectFromJson(JsonUtil.getJsonInput("ete_putreq"),ResourceCreateRequest.class);

    }

    @After
    public void cleanup(){
        try{
            for(Resource object:savedResources){
                if (deletedResourceId == -1){
                    resourceRepository.deleteById(object.getResourceId());
                } else if(deletedResourceId != object.getResourceId()) {
                    resourceRepository.deleteById(object.getResourceId());
                }
            }

            frameworkRepository.deleteAll(frameworkRepository.findAllByName("testFramework"));
            languageRepository.deleteAll(languageRepository.findAllByName("testLanguage"));
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @Given("^test resource objects posted$")
    public void testResourcePosting() throws IOException {
        savedResources = new ArrayList<>();
        response = httpMethod("/languages",HttpMethod.POST,langRequest);
        response = httpMethod("/frameworks",HttpMethod.POST,frameRequest);

        for(int i=0;i<5;i++){
            resExtract.setName(resExtract.getName() + i);
            String resourceJson = JsonUtil.toJson(resExtract);
            resRequest = new HttpEntity<>(resourceJson, header);
            response = httpMethod("/resources",HttpMethod.POST,resRequest);
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
        switch(reqType){
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
        switch (reqType){
            case "get":
                try{
                    response = httpMethod(endpoint,HttpMethod.GET,noBodyRequest);
                } catch(HttpClientErrorException e){ }
                break;
            case "post":
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
        switch (reqType){
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
        switch (reqType){
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
