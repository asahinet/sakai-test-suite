Feature: Test the Admin Users Tool
    Background:
    Given I am logged on as 'admin' with a password of 'admin'
    Given I am on the 'Administration Workspace' site using the 'Users' tool

    Scenario: User Search
    When I search for a user with an eid of 'student01'
    Then I should see the 'student01' user
    Then I should logout

    Scenario:  User Create Specific User
    Then add a user with an eid of 'student03' a firstname of 'joe' a lastname of 'zappa' an email of 'joe@zappa.com' that is of type 'registered' with a password of 'password'
    Then I should logout

    Scenario:  User Create Random
    Then create a user with random data
    Then I should logout

    Scenario:  User Edit
    Given I search for a user with an eid of 'student03'
    When I edit their first name to 'frank'
    Then I should see the first name change
    Then I should logout