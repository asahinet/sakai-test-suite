@dev @forums

Feature: Creating Forum topics as an instructor

  Scenario: Create a forum as an instructor
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Forums' tool
    When I create a new 'forum' as an 'instructor'
    Then The 'forum' is added to the list

  Scenario: Add a new Topic as an instructor
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Forums' tool
    When I create a new 'topic' as an 'instructor'
    Then The 'topic' is added to the list

  Scenario: Start a new conversation as an instructor
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Forums' tool
    When I create a new 'conversation' as an 'instructor'
    Then The 'conversation' is added to the list

  Scenario: Start a new conversation as a student
    Given I'm logged in as a 'student'
    And I am on a course with 'Forums' tool
    When I create a new 'conversation' as a 'student'
    Then The 'conversation' is added to the list

  Scenario: Reply to a message
    Given I'm logged in as an 'student'
    And I am on a course with 'Forums' tool
    When I reply to a message
    Then The 'reply' is added to the list

  Scenario: Edit a message
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Forums' tool
    When I 'edit' an existing message
    Then The message is changed

  Scenario: Delete a message
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Forums' tool
    When I 'delete' an existing message
    Then The message is deleted

