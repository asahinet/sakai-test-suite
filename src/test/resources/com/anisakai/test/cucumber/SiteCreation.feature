@setup  @sitecreate
Feature: Test the Site Editor Tool

  Background:
    Given I am logged on as 'admin' with a password of 'admin'



  Scenario:  Create Course Sites
    Given the following 'course' sites exist:
      | id            | title                | description  | contactname |
      | course-test-1 | Course Site Test 1   | for testing  | sally       |
      | course-test-2 | Course Site Test 2   | for testing  | joe         |
      | course-test-3 | Course Site Test 3   | for testing  | billy       |
    Then I should logout

  Scenario:  Create Project Sites
    Given the following 'project' sites exist:
      | id             | title                 | description  | contactname |
      | project-test-1 | Project Site Test 1   | for testing  | sally       |
      | project-test-2 | Project Site Test 2   | for testing  | joe         |
      | project-test-3 | Project Site Test 3   | for testing  | billy       |
    Then I should logout

  Scenario:  Create A Site
    Given I am on the 'Administration Workspace' site using the 'Site Setup' tool
    When I create a site with random data
    And add 'instructor1' as an 'Instructor'
    And add 'student01' as a 'Student'
    Then I should see 'instructor1' with a role of 'Instructor'
    And I should see 'student01' with a role of 'Student'
    Then I should logout