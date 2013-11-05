@suite @webcontent

Feature: Creating web content on a course

  As an instructor I want to be able to add web content to a course
  and be able to view that content as both an instructor and a student

Scenario: Adding web content to a course
  Given I'm logged in as an 'instructor'
  And I have created a course with 'Web Content' tool
  When I add web content to the tool
  Then The web page should be displayed

Scenario: Viewing web content as a student
  When I'm logged in as a 'student'
  And I have created a course with 'Web Content' tool
  Then The web page should be displayed