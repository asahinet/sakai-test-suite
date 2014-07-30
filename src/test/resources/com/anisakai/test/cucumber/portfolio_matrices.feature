@dev @matrix

Feature: Testing adding a matrix to a portfolio

  Scenario: Add a Matrix to an existing portfolio site
    Given I have created a 'Portfolio' site
    When I add a matrix to the site
    And I add the user to the site
    Then I see the Manage Matrices page display

  Scenario: Attaching forms to Matrix
    Given I have created a portfolio with a matrix
    And I have added the user 'reviewer'
    When I add a 'Custom Form' to the 'Participant Forms'
    And I add the reviewer role to the form
    And I add the 'Evaluator' role to the form
    Then I can see the added roles in the 'Freshman' cell