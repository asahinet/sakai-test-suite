@suite @syllabus
Feature: Creating a Syllabus for a course

  As an instructor I want to verify that a created syllabus and a redirected
  syllabus are visible by students

  Scenario: Create a syllabus
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Syllabus' tool
    When I add a syllabus to the course
    Then I should view the syllabus

  Scenario: Edit a syllabus
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Syllabus' tool
    When I edit the syllabus
    Then I should view the syllabus

  Scenario: View a syllabus as a student
    Given I'm logged in as a 'student'
    And I am on a course with 'Syllabus' tool
    Then I should view the syllabus

  Scenario: Create a redirect syllabus
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Syllabus' tool
    When I redirect a syllabus to a URL
    Then I should view the redirect

  Scenario: Remove all syllabus
    Given I'm logged in as an 'instructor'
    And I am on a course with 'Syllabus' tool
    When I remove all syllabus
    Then All syllabus should be removed






