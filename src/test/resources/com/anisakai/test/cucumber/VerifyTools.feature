@tools @suite
Feature: Verifying all tool links are working correctly

  Background:
    Given I am logged on as an admin user

  Scenario: Validate all tools are functional on a course site
    Given I am on 'Course Site Test 1' site
    When I click the course tool link the tool should load correctly

  Scenario: Validate all tools are functional on a project site
    Given I am on 'Project Site Test 1' site
    When I click the project tool link the tool should load correctly

  ##Scenario: Validate all tools are functional on a portfolio site
    #Given I am on 'Portfolio Site Test 1' site
    #When I click the portfolio tool link the tool should load correctly
