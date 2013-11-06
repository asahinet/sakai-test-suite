@gradebook @suite
Feature: Adding entries to gradebook as an instructor

  As an instructor I want to verify that entries can be added, changed, and deleted from
  gradebook directly from the tool.

Scenario: Add an entry to the gradebook tool as instructor
  Given I'm logged in as an 'instructor'
  And I am on a course with 'Gradebook' tool
  When I add an entry to gradebook
  Then The entry 'should' be visible in the list

Scenario: view a gradebook entry as a student
  Given I'm logged in as a 'student'
  And I am on a course with 'Gradebook' tool
  Then The entry 'should' be visible in the list

Scenario: edit a gradebook entry as an instructor
  Given I'm logged in as an 'instructor'
  And I am on a course with 'Gradebook' tool
  When I change the existing gradebook entry
  Then I'm logged in as a 'student'
  And I have created a course with 'Gradebook' tool
  Then The entry 'should' be visible in the list

Scenario: release grade to student
  Given I'm logged in as an 'instructor'
  And I have created a course with 'Gradebook' tool
  When I grade the entry
  And I'm logged in as a 'student'
  And I have created a course with 'Gradebook' tool
  Then The grade should be visible

Scenario: delete a gradebook entry as an instructor
  Given I'm logged in as an 'instructor'
  And I have created a course with 'Gradebook' tool
  When I delete the gradebook entry
  Then The entry 'shouldnt' be visible in the list


## In Process