@lessonbuilder @suite
Feature: Testing the adding and editing of content from the Lesson Builder Tool



  Scenario: Adding text to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Lesson Builder' tool
    When I add text to the lessons tool
    Then I can view the addition as an instructor

  Scenario: Adding multimedia file to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Lesson Builder' tool
    When I add 'file' multimedia to the lessons tool
    Then I can view the addition as an instructor

  Scenario: Adding multimedia URL to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Lesson Builder' tool
    When I add 'URL' 'multimedia' to the lessons tool
    Then I can view the addition as an instructor

  Scenario: Adding resource file to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Lesson Builder' tool
    When I add a 'file' 'resource' to the lessons tool
    Then I can view the addition as an instructor

  Scenario: Adding resource URL to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Lesson Builder' tool
    When I add a 'url' 'resource' to the lessons tool
    Then I can view the addition as an instructor

  Scenario: Adding subpage to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Lesson Builder' tool
    When I add '2' subpages to the lesson tool
    Then I can view the addition as an instructor

  Scenario: Adding an assignment to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Assignments' tool
    When I create an assignment
    And I add the 'Assignment' to the lesson tool
    Then I can view the addition as an instructor

  Scenario: Adding an quiz to Lesson Builder
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Test & Quizzes' tool
    When I create a quiz
    And I add the 'Quiz' to the lesson tool
    Then I can view the addition as an instructor