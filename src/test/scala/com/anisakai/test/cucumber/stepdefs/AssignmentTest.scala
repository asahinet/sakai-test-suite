package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.Config
import com.anisakai.test.pageobjects.{AssignmentTool, Portal}
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 9/30/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
class AssignmentTest extends ScalaDsl with EN with TearDown {

  var assignmentTitle: String = ""
  var oldData: Array[String] = null

  def login(role: String) {
    if (role.equalsIgnoreCase("instructor")) {
      Portal.login(Config.defaultInstructorEid, Config.defaultInstructorPassword)
    } else if (role.equalsIgnoreCase("student")) {
      Portal.login(Config.defaultStudentEid, Config.defaultStudentPassword)
    }
    assertFalse(role + " is not a supported role yet", false)
  }

  Given( """^I'm logged in as an? '(.*)'$""") { (role: String) =>
    login(role)
  }

  When( """^I create an assignment in '(.*)' site$""") { (siteType: String) =>
    if (siteType.equalsIgnoreCase("course")) {
      Portal.goToSite(Config.defaultCourseSiteTitle)
      Portal.goToTool("Assignments", true)
      AssignmentTool.goToAdd()
      assignmentTitle = AssignmentTool.assignment()
    }
    assertFalse(siteType + " is not a supported site type for Assignments", false);
  }

  Then( """^I see an assignment listed$""") { () =>
    assertTrue(AssignmentTool.isAdded(assignmentTitle))
  }

  And( """^I can view the assignment as instructor$""") { () =>
    assertTrue(AssignmentTool.isViewable(assignmentTitle))
  }

  Given( """^that student has been added to my course$""") { () =>
    assertTrue(Portal.isEnrolled(Config.defaultCourseSiteTitle))
  }
  When( """^I open the assignment listed$""") { () =>
    Portal.goToSite(Config.defaultCourseSiteTitle)
    Portal.goToTool("Assignments", true)
    AssignmentTool.openAssignment(assignmentTitle)
  }

  Then( """^I should be able to submit the assignment$""") { () =>
    assertTrue(AssignmentTool.studentSubmitAssignment())
  }

  And( """^I have created a course with an assignment$""") { () =>
    Portal.goToSite(Config.defaultCourseSiteTitle)
    Portal.goToTool("Assignments", true)
  }

  When( """^I edit the existing assignment$""") { () =>
    oldData = AssignmentTool.goToEdit(assignmentTitle)
  }

  When( """^I change the title and date$""") { () =>
    assignmentTitle = AssignmentTool.edit()
  }

  Then( """^the updated assignment shows the new title and date$""") { () =>
    AssignmentTool.verifyEdit(assignmentTitle, oldData)
  }

  When( """^I delete the assignment$""") { () =>
    AssignmentTool.deleteAssignment(assignmentTitle)
  }

  Then( """^it should no longer be in the table$""") { () =>
    assertTrue(AssignmentTool.removed(assignmentTitle))
  }

  Then( """^the '(.+)' should no longer have access$""") { (role: String) =>
    login(role)
    Portal.goToSite(Config.defaultCourseSiteTitle)
    Portal.goToTool("Assignments", true)
    assertTrue(AssignmentTool.removed(assignmentTitle))
  }
}
