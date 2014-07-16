@turnitin @assignment @suite
Feature: Creating an assignment with the Third Party Tool Turn It In enabled

  As an Instructor I want to be able to create an assignment with Turn It In enabled
  so that students can receive feedback from Turn it in on their submitted assignments
  #Scenario: Set Up Instance
   # Given There is a course created on the turn it in instance
    #And

  Scenario: I create a Turn It In assignment incorrectly
    Given I'm logged in as an 'instructor' on a Turn It In instance
    When I add a Turn It In assignment 'incorrectly'
    Then The status of the assignment should be 'Not Open'

  Scenario: I create a Turn It In assignment correctly
    Given I'm logged in as an 'instructor' on a Turn It In instance
    And I add a Turn It In assignment 'correctly'
    Then The status of the assignment should be 'Open'

  Scenario: Submitting a Turn It In assignment as a student
    Given A course exists with a Turn It In assignment
    And I'm logged in as a 'student' on a Turn It In instance
    When I submit a Turn It In assignment
    Then The status of the assignment should be 'Submitted'