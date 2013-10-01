@assignment @suite
Feature: Creating an assignment as an instructor

  Dir: Assignments > Feature file: assignments_instructor.feature

## Crud

  Scenario: create an assignment as an 'instructor'
    Given I'm logged in as an instructor
    When I create an assignment in 'Course' site
    Then I see an assignment listed
    And I can view the assignment as 'instructor'

  Scenario: Open assignment as student
    Given I'm logged in as a 'student'
    And that student has been added to my course
    When I open the assignment listed
    Then I should be able to submit the assignment

  Scenario: Update an assignment
    Given I'm logged in as an 'instructor'
    And I have created a course with an assignment
    When I edit the existing assignment
    And I change the title and date
    Then the updated assignment shows the new title and date

  Scenario: Delete an assignment
    Given I'm logged in as an 'instructor'
    And I have created a course with an assignment
    When I delete the assignment
    Then it should no longer be in the table
    And the student should no longer have access
