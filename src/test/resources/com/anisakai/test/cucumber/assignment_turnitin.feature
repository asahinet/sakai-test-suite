Feature: Creating an assignment with the Third Party Tool Turn It In enabled

  As an Instructor I want to be able to create an assignment with Turn It In enabled
  so that students can receive feedback from Turn it in on their submitted assignments



  Scenario: I create a Turn It In assignment incorrectly
    Given I'm logged in as 'instructor'
    And I create a 'course'
    When I add an 'assignment'
    And I enable turn it in
    But I don't choose Single upload file
    Then I should get a warning message

  Scenario: I create a Turn It In assignment correctly
    Given I'm logged in as an Instructor
    And I create a 'course'
    And I add a Turn It In assignment
    Then The status of the assignment should be open

  Scenario: Submitting a Turn It In assignment as a student
    Given A course exists with a Turn It In assignment
    And I'm logged in as a student
    When I submit an assingment
    Then The status should be submitted