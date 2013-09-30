@setup @adminusertool
Feature: Test the Admin Users Tool

  Background:
    Given I am logged on as 'admin' with a password of 'admin'
    Given I am on the 'Administration Workspace' site using the 'Users' tool


  Scenario:  Create Users
    Given the following users exist:
      | eid         | email           | firstname | lastname | type       | password |
      | student01   | sally@zappa.com | sally     | fields   | registered | password |
      | student02   | joe@zappa.com   | joe       | zappa    | registered | password |
      | instructor1 | bbob@zappa.com  | billy     | smith    | maintain   | password |
      | instructor2 | fred@zappa.com  | fred      | jones    | maintain   | password |

  Scenario: User Search
    When I search for a user with an eid of 'student01'
    Then I should see the 'student01' user

  Scenario:  User Edit
    Given I search for a user with an eid of 'student01'
    When I edit their first name to 'frank'
    Then I should see the first name change

  Scenario:  User Create Random
    Then create a user with random data
