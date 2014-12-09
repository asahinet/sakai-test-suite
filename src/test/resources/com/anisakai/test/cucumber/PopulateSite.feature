@populatesite

  Feature: Create a site and populate with data

    Background:
      Given I am logged on as an admin user

    Scenario:  Create A Site
      Given I am on the 'Administration Workspace' site using the 'Site Setup' tool
      And I create '100' users
      Then I add the students to a random site
