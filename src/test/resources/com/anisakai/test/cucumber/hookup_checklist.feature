Feature: Basic checklist for Hookup testing

  Background:
    Given A site has been created with the Hookup tool
    And The users have been added to that site

  Scenario: Schedule a meeting
    Given I'm logged in as an 'instructor'
    When I create a meeting in 'Scheduled Meetings'
    Then I see a meeting listed

  Scenario: Schedule a Huddle
    Given I'm logged in as an 'instructor'
    When I create a huddle in 'Scheduled Meetings'
    And I choose to email participants
    Then I can enter the huddle
    And The email has sent to the participants

  Scenario: Schedule a Huddle
    Given I'm logged in as an 'instructor'
    When I create a huddle in 'Scheduled Meetings'
    And I End the Huddle
    Then the meeting should be removed

  Scenario: Verify that BigBlueButton functions

