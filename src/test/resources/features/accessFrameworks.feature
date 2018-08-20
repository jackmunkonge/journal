Feature: Create, view and destroy frameworks
  Background:
    Given test frameworks posted
    And use framework number 0

  Scenario: Get a specific framework by ID
    Given a request for a framework
    And search framework by ID
    When a get request is made
    And response code is successful
    Then the same framework is retrieved

  Scenario: Get a specific framework by name
    Given a request for a framework
    And search framework by name
    When a get request is made
    And response code is successful
    Then the same framework is retrieved

  Scenario: Get a list of all frameworks
    Given a request for a framework
    When a get request is made
    And response code is successful
    Then a list of all frameworks are retrieved

  Scenario: Get a list of frameworks by language ID
    Given a request for a framework
    And search framework by language ID
    When a get request is made
    And response code is successful
    Then a list of the language's frameworks are retrieved

  Scenario: Get a list of frameworks by language name
    Given a request for a framework
    And search framework by language name
    When a get request is made
    And response code is successful
    Then a list of the language's frameworks are retrieved

  Scenario: Update a framework by ID
    Given a request for a framework
    And search framework by ID
    And response code is successful
    When an update request is made to framework
    Then the framework name is correctly changed

  Scenario: Update a framework by name
    Given a request for a framework
    And search framework by name
    And response code is successful
    When an update request is made to framework
    Then the framework name is correctly changed

  Scenario: Delete a framework by ID
    Given a request for a framework
    And search framework by ID
    And a delete request is made
    And response code is successful
    When a get request is made
    Then response code is unsuccessful

  Scenario: Delete a framework by name
    Given a request for a framework
    And search framework by name
    And a delete request is made
    And response code is successful
    When a get request is made
    Then response code is unsuccessful
