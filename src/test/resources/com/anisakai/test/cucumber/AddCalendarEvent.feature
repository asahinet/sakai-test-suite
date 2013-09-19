@test2

Feature: Test adding a calendar event

  Scenario: Log in and navigate to calendar
    Given I am logged in with 'gleonard@anisakai.com' as username and 'password' as password
    Then I should be on my workspace
    When I select Calendar in left nav bar
    Then I should be on my calendar

  Scenario: Add a calendar event
    When I click the add button
    When I create an event with random data
    Then the event should be added to my calendar
