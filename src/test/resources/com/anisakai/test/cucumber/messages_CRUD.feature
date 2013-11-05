@messages @suite
Feature: Creating, reading, updating, and deleting messages
  As an instructor I want to verify that messages can be added, changed, and deleted from
  messages directly from the tool.

  Scenario: Add a message to the messages tool as instructor
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Messages' tool
    When I add a message to messages
    Then The created message should be visible in the list

  Scenario: Add a message to the messages tool as a student
    Given I'm logged in as a 'student'
    And I am on a course with 'Messages' tool
    When I add a message to messages
    Then The created message should be visible in the list

  Scenario: Add a message with an attachment
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Messages' tool
    When I add a message with an attachment to messages
    Then The created message should be visible in the list