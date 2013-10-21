Feature: Creating, reading, updating, and deleting messages
  As an instructor I want to verify that messages can be added, changed, and deleted from
  messages directly from the tool.

  Scenario: Add a message to the messages tool as instructor
    Given I'm logged in as an 'instructor'
    And I have created a course with messages tool
    And I have added a 'student' to the course
    When I add a message to messages
    Then The created message should be visable in the list

  Scenario: Add a message to the messages tool as a student
    Given I have created a course with messages tool
    And I have added a 'student' to the course
    When I log in as a 'student' to the course
    And I add a message to messages
    Then The created message should be visable in the list

  Scenario: Add a message with an attachement
    Given I'm logged in as an 'instructor'
    And I have created a course with messages tool
    And I have added a 'student' to the course
    When I add a message with an attachment to messages
    Then The created message should be visable in the list