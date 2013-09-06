@test1

Feature: Test Login and basic nav

  Scenario: Testing Nav
    Given I am on the 'https://my.anisakai.com/portal' entry page
    When I login with 'gleonard@anisakai.com' as the username and 'password' as the password
    Then I should see myworkspace
    When I click on the MOTD Options button
    When I select the Show Announcement subject radio button
      And I click the update button
    Then the Message of the day text should contain 'Sakai Administrator'

