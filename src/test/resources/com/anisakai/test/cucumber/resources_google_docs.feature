Feature: Adding Google Docs to Resources

  As an Instructor I want to verify that I can add google docs to resources and view
  those documents, as well as have students view them.

Scenario: add a google document to resources
  Given I'm logged in as an 'instructor'
  And I have created a 'course'
  When I add a 'link to google docs' with default properties to resources
  Then I should see the google doc in resources

Scenario: add a google document to resources with a from date in the future
  Given I'm logged in as an 'instructor'
  And I create a course
  And I add a 'student' to the course
  When I add a 'link to google docs'
  And I set the from date in the future
  Then the resource should not be visible to the 'student'

Scenario: add a google document to resources with an until date in the past
  Given I'm logged in as an 'instructor'
  And I create a course
  And I add a 'student' to the course
  When I add a 'link to google docs'
  And I set the until date in the past
  Then the resource should not be visible to the 'student'