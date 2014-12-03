@setup  @sitecreate
Feature: Site Management

  Background:
    Given I am logged on as an admin user

  Scenario:  Create Course Sites
    Given the following 'course' sites exist:
      | id            | title              | description | contactname |
      | course-test-1 | Course Site Test 1 | for testing | sally       |

  Scenario:  Create Project Sites
    Given the following 'project' sites exist:
      | id             | title               | description | contactname |
      | project-test-1 | Project Site Test 1 | for testing | sally       |

  Scenario:  Create Portfolio Sites
    Given the following 'portfolio' sites exist:
      | id               | title                 | description | contactname |
      | portfolio-test-1 | Portfolio Site Test 1 | for testing | sally       |

  Scenario:  Create memberships in existing sites
    Given the following memberships exist:
      | site-id          | user-eid    | role            |
      | course-test-1    | instructor1 | Instructor      |
      | course-test-1    | student01   | Student         |
      | course-test-1    | student02   | Student         |

#  Scenario:  Create A Site
#   Given I am on the 'Administration Workspace' site using the 'Site Setup' tool
#   When I create a site with random data
#   And add 'instructor1' as an 'Instructor'
#   And add 'student01' as a 'Student'
#   Then I should see 'instructor1' with a role of 'Instructor'
#   And I should see 'student01' with a role of 'Student'
