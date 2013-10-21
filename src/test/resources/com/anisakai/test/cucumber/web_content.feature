Feature: Creating web content on a course

  As an instructor I want to be able to add web content to a course
  and be able to view that content as both an instructor and a student

Scenario: Adding web content to a course
  Given I'm logged in as an instructor
  And I have created a course with the web content tool
  When I add web content to the tool
  Then I should see the web page in the tool

Scenario: Viewing web content as a student
  Given I created a course
  And I added web content to the course
  When I'm logged in as a student
  And I view the web content
  Then The Web page should display