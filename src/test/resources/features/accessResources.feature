Feature: Create, view and destroy resources
  Background:
    Given test resource objects posted
    And use resource number 0

  Scenario: Get the list of resources
    Given a request for a resource
    When a get request is made
    And response code is successful
    Then a list of all resources are retrieved

  Scenario: Get a specific resource by ID
    Given a request for a resource
    And search resource by resource ID
    When a get request is made
    And response code is successful
    Then the same resource is retrieved

  Scenario: Get a specific resource by name
    Given a request for a resource
    And search resource by resource name
    When a get request is made
    And response code is successful
    Then the same resource is retrieved

  Scenario: Get a resource by language ID
    Given a request for a resource
    And search resource by language ID
    When a get request is made
    And response code is successful
    Then a list of the language's resources are retrieved

  Scenario: Get a resource by language name
    Given a request for a resource
    And search resource by language name
    When a get request is made
    And response code is successful
    Then a list of the language's resources are retrieved

  Scenario: Get a resource by framework ID
    Given a request for a resource
    And search resource by framework ID
    When a get request is made
    And response code is successful
    Then a list of the framework's resources are retrieved

  Scenario: Get a resource by framework name
    Given a request for a resource
    And search resource by framework name
    When a get request is made
    And response code is successful
    Then a list of the framework's resources are retrieved

  Scenario: Update a resource by ID
    Given a request for a resource
    And search resource by resource ID
    And response code is successful
    When an update request is made
    Then the url is correctly changed

  Scenario: Update a resource by name
    Given a request for a resource
    And search resource by resource name
    And response code is successful
    When an update request is made
    Then the url is correctly changed

  Scenario: Delete a resource by ID
    Given a request for a resource
    And search resource by resource ID
    And a delete request is made
    And response code is successful
    When a get request is made
    Then response code is unsuccessful

  Scenario: Delete a resource by name
    Given a request for a resource
    And search resource by resource name
    And a delete request is made
    And response code is successful
    When a get request is made
    Then response code is unsuccessful



