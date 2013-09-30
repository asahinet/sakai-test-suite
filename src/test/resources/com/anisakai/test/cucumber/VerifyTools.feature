@tools
Feature: Verifying all tool links are working correctly
  Background:
    Given I am logged on as 'admin' with a password of 'admin'

  Scenario: Validate all tools are functional on a course site
    Given Course:I am on 'Course Tools Test' 'Course' site
    When I click the course tool link the tool should load correctly

   Scenario: Validate all tools are functional on a project site
     Given Project:I am on 'Project Tools Test' 'Project' site
     When I click the project tool link the tool should load correctly

   Scenario: Validate all tools are functional on a portfolio site
     Given Portfolio:I am on 'Portfolio Tools Test' 'Portfolio' site
     When I click the portfolio tool link the tool should load correctly
