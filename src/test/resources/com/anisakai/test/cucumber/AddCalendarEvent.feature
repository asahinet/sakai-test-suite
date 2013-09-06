@test2

Feature: Test adding a calendar event

  Scenario: Log in and navigate to calendar
    Given I am on the 'https://my.anisakai.com/portal' entry page
    When user login with 'gleonard@anisakai.com' as the username and 'password' as the password
    Then I should see myworkspace
    When I select Calendar in left nav bar
    Then I should be on my calendar

  Scenario: Add a calendar event
    When I click the add button
    When I create an event with random data
    When I save the event
    Then the event should be added to my calendar
