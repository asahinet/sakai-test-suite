@googledocs @indev
Feature: Adding Google Docs to Resources

  As an Instructor I want to verify that I can add google docs to resources and view
  those documents, as well as have students view them.

  Scenario: add a google document to resources
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Resources' tool
    When I add a google doc with 'default' dates
    And I'm logged in as a 'student'
    And I am on a course with 'Resources' tool
    Then the resource 'should' be visible

  Scenario: add a google document to resources with a from date in the future
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Resources' tool
    When I add a google doc with 'future' dates
    And I'm logged in as a 'student'
    And I am on a course with 'Resources' tool
    Then the resource 'shouldnt' be visible

  Scenario: add a google document to resources with an until date in the past
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Resources' tool
    When I add a google doc with 'past' dates
    And I'm logged in as a 'student'
    And I am on a course with 'Resources' tool
    Then the resource 'shouldnt' be visible

  Scenario: Remove google documents from resources
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Resources' tool
    When I remove all google documents
    Then the resource 'shouldnt' be visible