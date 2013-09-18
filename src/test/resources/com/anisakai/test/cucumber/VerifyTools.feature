@tools
Feature: Verifying all tool links are working correctly
  Scenario: Log in and go to site
    Given I am logged in with 'gleonard@anisakai.com' as username and 'password' as password
    Given I am on the 'Tools Test Site All13' site

  Scenario: Validate all tools are functional
    When I click the tool link the tool should load correctly

