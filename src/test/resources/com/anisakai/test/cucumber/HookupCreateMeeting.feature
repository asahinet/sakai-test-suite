@hookup

  Feature: Create an ANI Virtual Meeting

    Background:
      Given I am logged on as an admin user
      And I am on a course with 'ANI Virtual Meeting' tool

    Scenario: Create a Meeting
      When I create a meeting with default time and date
      Then the meeting 'should' be displayed in the list

    Scenario: Delete a Meeting
      When I delete a meeting
      Then the meeting 'shouldnt' be displayed in the list