@setup  @site
Feature: Test the Site Editor Tool

  Background:
    Given I am logged on as 'admin' with a password of 'admin'
    And I am on the 'Administration Workspace' site using the 'Users' tool
    And a user with an eid of 'student01' a firstname of 'sally' a lastname of 'fields' an email of 'sally@zappa.com' that is of type 'registered' with a password of 'password' exists
    And a user with an eid of 'instructor1' a firstname of 'billy' a lastname of 'smith' an email of 'bbob@zappa.com' that is of type 'maintain' with a password of 'password' exists
    And I am on the 'Administration Workspace' site using the 'Site Setup' tool

  Scenario:  Create A Site
    When I create a site with random data
    And add 'instructor1' as an 'Instructor'
    And add 'student01' as a 'Student'
    Then I should see 'instructor1' with a role of 'Instructor'
    And I should see 'student01' with a role of 'Student'
    Then I should logout

