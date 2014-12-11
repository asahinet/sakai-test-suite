@hookupbulk

  Feature: Create a bulk amount of meetings

    Background:
      Given I am logged on as an admin user
      And I am on a course with 'ANI Virtual Meeting' tool

    Scenario: Create bulk meetings
      When I create and delete '200' meetings
      Then there should be no errors

