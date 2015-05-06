Feature: Testing CLE-11370

  Updating sites in My preferences shows the first site in Active sites in the Favorite sites
  This should not be the case. We should only see the sites in the Active Sites list.

  Scenario: Moving sites in My Preferences from Favorites to Active
    Given I am a member of multiple sites
    When I move a site from Favorite Sites to Active Sites
    Then That site no longer displays in the header
    And The first site listed in Active Sites is not in the header also
