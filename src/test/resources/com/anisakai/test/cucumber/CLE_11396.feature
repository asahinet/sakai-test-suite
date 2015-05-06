Feature: Testing CLE-11396

  As an Instructor I want to be able to view question statistics without a stack trace error

  Scenario: I view question statistics on a completed quiz
    Given I am logged in as an Instructor
    And I am have a course site with a published quiz and at least on submission
    When I click on Published quizes and choose Scores in the Dropdown
    And I click the 'Questions' button
    Then I should see the question statistics

