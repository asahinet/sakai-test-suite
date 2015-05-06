Feature: Testing CLE-11314

  Importing from a site from Site Editor and choosing Replace Data should completely override the existing data
  of the second site.

  Scenario: Replace data in a site with another site
    Given I have a course site with data in Resources
    And I have a second course site with data in resources
    When I Import from a site
    And Choose to replace data
    Then The data in resources should be the same as the original site