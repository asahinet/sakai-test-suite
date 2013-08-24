Feature: Test CLE Login

    Scenario: CLE Local User Login
    Given I am on the 'https://nightly.cle.rsmart.com/portal' gateway page
    When I enter 'admin' for user
        And I enter 'admin' for password
        And I click the Login button
    Then I should see my workspace
