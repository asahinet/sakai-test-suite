@setup  @sitecreate
Feature: Site Management

  Background:
    Given I am logged on as an admin user

  Scenario:  Create Sites
    Given the following sites exist:
      | id               | title                 | description | contactname | sitetype  |
      | course-test-1    | Course Site Test 1    | for testing | sally       | course    |
      | project-test-1   | Project Site Test 1   | for testing | sally       | project   |
      | portfolio-test-1 | Portfolio Site Test 1 | for testing | sally       | portfolio |
    And the sites have the following tools:
      | All |


  Scenario:  Create memberships in existing sites
    Given the following memberships exist:
      | site-id          | user-eid    | role            |
      | course-test-1    | instructor1 | Instructor      |
      | course-test-1    | student01   | Student         |
      | course-test-1    | student02   | Student         |