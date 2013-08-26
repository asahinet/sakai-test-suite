@setup
Feature: Test the Site Editor Tool
    Background:
    Given I am logged on as 'admin' with a password of 'admin'
    Given I am on the 'Administration Workspace' site using the 'Site Setup' tool

    Scenario:  Create A Site
    Then create a site with random data
    Then I should logout
