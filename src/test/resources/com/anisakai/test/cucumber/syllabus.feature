Feature: Creating a Syllabus for a course

  As an instructor I want to verify that a created syllabus and a redirected
  syllabus are visible by students

Scenario: Create a syllabus
  Given I'm logged in as an instructor
  And I have created a course
  When I add a syllabus to the course
  Then I should view the syllabus


Scenario: Create a redirect syllabus
  Given I'm logged in as an instructor
  And I have create a course
  When I redirect a syllabus to a URL
  Then I should view the syllabus


Scenario: Edit a syllabus
  Given I'm logged in as an instructor
  And I have created a course
  And I have added a syllabus to the course
  When I edit the syllabus
  Then my changes should be visible

Scenario: View a syllabus as a student
  Given I have created a course
  And I have added a student to that course
  And I have created a syllabus
  When I log in as a student
  Then I should be able to view the syllabus