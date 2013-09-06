@test
Feature: Test Google search

  Scenario: Google Search
    Given I am on the 'http://www.google.com' page
    When I enter 'Amazon' for search term
    When I click the searching button
    When I click the Books link
    Then I should see the Amazon Books page