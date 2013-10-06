Feature: Adding entries to gradebook as an instructor

  As an instructor I want to verify that entires can be added, changed, and deleted from
  gradebook directly from the tool.

Scenario: Add an entry to the gradebook tool as instructor
  Given I'm logged in as an 'instructor'
  And I have created a course with gradebook tool
  When I add an entry to gradebook
  Then The created entry should be visable in the list

Scenario: view a gradebook entry as a student
  Given I'm logged in as an 'instructor'
  And I have created a course with gradebook tool
  And I have added a 'student' to the course
  When I add an entry to gradebook
  Then The created entry should be visable when logged in as a 'studnet'

Scenario: edit a gradebook entry as an instructor
  Given I'm logged in as an 'instructor'
  And I have created a course with gradebook tool
  And I add an entry to gradebook
  When I change the existing gradebook entry
  Then The changes should be visable when logged in as a student

Scenario: delete a gradebook entry as an instructor
  Given I'm logged in as an 'instructor'
  And I have created a course with gradebook tool
  And I add an entry to gradebook
  When I delete the gradebook entry
  Then The deleted entry should not be visable in the list

Scenario: release grade to student
  Given I'm logged in as an 'instructor'
  And I have created a course with gradebook tool
  And I have added a student to the course
  When I add an entry to gradebook
  And I grade the entry
  Then The created entry should be visable in the list