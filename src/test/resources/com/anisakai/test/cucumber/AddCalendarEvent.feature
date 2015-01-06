@ignore

Feature: Test adding a calendar event

  Background:
    Given I am logged on as an admin user
    And I am on a course with 'Calendar' tool

  Scenario: Add a calendar event
    When I create an event with random data
    Then the event should be added to my calendar
