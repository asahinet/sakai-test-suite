Feature: Testing the adding and editing of content from the Lesson Builder Tool



  Scenario: Adding text to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with lessons tool
    When I add text to the lessons tool
    Then I can view the text as instructor

  Scenario: Adding multimedia to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with lessons tool
    When I add multimedia to the lessons tool
    And I choose to upload a file
    Then I can view the multimedia as instructor

  Scenario: Adding multimedia to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with lessons tool
    When I add multimedia to the lessons tool
    And I choose to link to a URL
    Then I can view the multimedia as instructor

  Scenario: Adding resource file to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with lessons tool
    When I add a resource to the lessons tool
    And I choose to upload a file
    Then I can view the uploaded file as an instructor

  Scenario: Adding resource link to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with lessons tool
    When I add resource to the lessons tool
    And I choose to link to a URL
    Then I can view the linked resource as an instructor

  Scenario: Adding subpage to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with lessons tool
    When I add two subpages to the lesson tool
    Then I can view the subpages as an instructor

  Scenario: Adding an assignment to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with  assignments tool
    And I have added the lessons tool
    When I create an assignment
    And I add the assignment to lessons
    Then I can view the assignment in the lesson tool as instructor

  Scenario: Adding an quiz to Lesson Builder
    Given I'm logged in as an instructor
    And I have created a site with  the test and quizzes tool
    And I have added the lessons tool
    When I create an quiz
    And I add the quiz to lessons
    Then I can view the quiz in the lesson tool as instructor