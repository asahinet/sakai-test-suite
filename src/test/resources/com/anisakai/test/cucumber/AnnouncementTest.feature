@suite @announcement

Feature: Test MOTD

  Scenario: Testing MOTD
    Given I am logged on as 'student01' with a password of 'password'
    Then I should see my workspace
    When I click on the MOTD Options button
    When I select the Show Announcement subject radio button
    And I click the update button
    Then the Message of the day text should contain 'Sakai Administrator'
