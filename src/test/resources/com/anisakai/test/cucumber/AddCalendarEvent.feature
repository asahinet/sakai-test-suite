@test2

Feature: Test adding a calendar event
  Background:
    Given I am logged on as 'student01' with a password of 'password'
    Given I am on the 'My Workspace' site using the 'Calendar' tool

  Scenario: Add a calendar event
    When I create an event with random data
    Then the event should be added to my calendar
    Then I should logout
