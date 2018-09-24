Feature: Create, view and destroy resources
  Background:
    Given test objects posted
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

  Scenario: Get resources by framework ID
    Given a request for a resource
    And search resource by framework ID
    When a get request is made
    And response code is successful
    Then a list of the framework's resources are retrieved

  Scenario: Get resources by framework name
    Given a request for a resource
    And search resource by framework name
    When a get request is made
    And response code is successful
    Then a list of the framework's resources are retrieved

  Scenario: Get resources by language ID
    Given a request for a resource
    And search resource by language ID
    When a get request is made
    And response code is successful
    Then a list of the language's resources are retrieved

  Scenario: Get resources by language name
    Given a request for a resource
    And search resource by language name
    When a get request is made
    And response code is successful
    Then a list of the language's resources are retrieved

  Scenario: Get resources by library ID
    Given a request for a resource
    And search resource by library ID
    When a get request is made
    And response code is successful
    Then a list of the library's resources are retrieved

  Scenario: Get resources by library name
    Given a request for a resource
    And search resource by library name
    When a get request is made
    And response code is successful
    Then a list of the library's resources are retrieved

  Scenario: Get resources by plugin ID
    Given a request for a resource
    And search resource by plugin ID
    When a get request is made
    And response code is successful
    Then a list of the plugin's resources are retrieved

  Scenario: Get resources by plugin name
    Given a request for a resource
    And search resource by plugin name
    When a get request is made
    And response code is successful
    Then a list of the plugin's resources are retrieved

  Scenario: Get resources by principle ID
    Given a request for a resource
    And search resource by principle ID
    When a get request is made
    And response code is successful
    Then a list of the principle's resources are retrieved

  Scenario: Get resources by principle name
    Given a request for a resource
    And search resource by principle name
    When a get request is made
    And response code is successful
    Then a list of the principle's resources are retrieved

  Scenario: Get resources by tool ID
    Given a request for a resource
    And search resource by tool ID
    When a get request is made
    And response code is successful
    Then a list of the tool's resources are retrieved

  Scenario: Get resources by tool name
    Given a request for a resource
    And search resource by tool name
    When a get request is made
    And response code is successful
    Then a list of the tool's resources are retrieved

  Scenario: Post a resource
    Given a post request is made
    And response code is successful
    Given a request for a resource
    And use posted resource
    And search resource by resource name
    When a get request is made
    And response code is successful
    Then the posted resource is retrieved

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



