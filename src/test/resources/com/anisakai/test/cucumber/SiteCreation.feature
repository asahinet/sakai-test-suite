@setup  @sitecreate
Feature: Site Management

  Background:
    Given I am logged on as an admin user

  Scenario:  Create Course Sites
    Given the following 'course' sites exist:
      | id            | title                | description  | contactname |
      | course-test-1 | Course Site Test 1   | for testing  | sally       |
      | course-test-2 | Course Site Test 2   | for testing  | joe         |
      | course-test-3 | Course Site Test 3   | for testing  | billy       |

  Scenario:  Create Project Sites
    Given the following 'project' sites exist:
      | id             | title                 | description  | contactname |
      | project-test-1 | Project Site Test 1   | for testing  | sally       |
      | project-test-2 | Project Site Test 2   | for testing  | joe         |
      | project-test-3 | Project Site Test 3   | for testing  | billy       |

  Scenario:  Create memberships in existing sites
    Given the following memberships exist:
    | site-id     | user-eid     | role        |
    | course-test-1 | instructor1  | Instructor  |
    | course-test-1 | student01    | Student     |
    | course-test-1 | student02    | Student     |
    | course-test-2 | instructor2  | Instructor  |
    | course-test-2 | student01    | Student     |
    | course-test-2 | student02    | Student     |
    | course-test-3 | instructor1  | Instructor  |
    | course-test-3 | instructor2  | Instructor  |
    | course-test-3 | student01    | Student     |
    | course-test-3 | student02    | Student     |




  Scenario:  Create A Site
    Given I am on the 'Administration Workspace' site using the 'Site Setup' tool
    When I create a site with random data
    And add 'instructor1' as an 'Instructor'
    And add 'student01' as a 'Student'
    Then I should see 'instructor1' with a role of 'Instructor'
    And I should see 'student01' with a role of 'Student'
