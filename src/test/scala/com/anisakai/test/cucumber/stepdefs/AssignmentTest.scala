package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import cucumber.runtime.PendingException
import junit.framework.Assert._
import com.anisakai.test.pageobjects.{CalendarObj, AssignmentTool, Portal}
import com.anisakai.test.Config

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 9/30/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
class AssignmentTest extends ScalaDsl with EN with TearDown{

  var assignmentTitle : String = ""
  var current = new Array[String](2)
  def login(role: String) {
    if (role.equalsIgnoreCase("instructor")) {
      Portal.login(Config.defaultInstructorEid, Config.defaultInstructorPassword)
    } else if (role.equalsIgnoreCase("student")) {
      Portal.login(Config.defaultStudentEid, Config.defaultStudentPassword)
    }
    assertFalse(role + " is not a supported role yet", false)
  }

  Given("""^I'm logged in as an? '(.*)'$"""){ (role : String) =>
    login(role)
  }

  When("""^I create an assignment in '(.*)' site$"""){ (siteType: String) =>
    if (siteType.equalsIgnoreCase("course")) {
      Portal.gotoSite(Config.defaultCourseSiteTitle)
      Portal.gotoTool("Assignments")
      AssignmentTool.gotoAdd()
      assignmentTitle = AssignmentTool.assignment()
    }
    assertFalse(siteType + " is not a supported site type for Assignments",false);
  }

  Then("""^I see an assignment listed$"""){ () =>
    assertTrue(CalendarObj.isAdded(assignmentTitle))
  }

  And("""^I can view the assignment as instructor$"""){ () =>
    assertTrue(AssignmentTool.isAdded(assignmentTitle))
  }

  Given("""^that student has been added to my course$"""){ () =>
    assertTrue(Portal.isEnrolled(Config.defaultCourseSiteTitle))
  }
  When("""^I open the assignment listed$"""){ () =>
    Portal.gotoSite(Config.defaultCourseSiteTitle)
    Portal.gotoTool("Assignments", true)
    AssignmentTool.openAssignment(assignmentTitle)
  }

  Then("""^I should be able to submit the assignment$"""){ () =>
    assertTrue(AssignmentTool.submitAssignment())
  }

  Given("""^I have created a course with an assignment$"""){ () =>
    Portal.gotoSite(Config.defaultCourseSiteTitle)
    Portal.gotoTool("Assignments")
    assertTrue(CalendarObj.isAdded(assignmentTitle))
  }

  When("""^I edit the existing assignment$"""){ () =>
    current = AssignmentTool.gotoEdit(assignmentTitle)
  }

  When("""^I change the title and date$"""){ () =>
    assignmentTitle = AssignmentTool.edit(current)
  }

  Then("""^the updated assignment shows the new title and date$"""){ () =>
    AssignmentTool.verifyEdit(assignmentTitle, current)
  }

  When("""^I delete the assignment$"""){ () =>
    AssignmentTool.deleteAssignment(assignmentTitle)
  }

  Then("""^it should no longer be in the table$"""){ () =>
    assertTrue(AssignmentTool.removed(assignmentTitle))
  }

  Then("""^the '(.+)' should no longer have access$"""){ (role : String) =>
    login(role)
    Portal.gotoSite(Config.defaultCourseSiteTitle)
    Portal.gotoTool("Assignments", true)
    assertTrue(AssignmentTool.removed(assignmentTitle))
  }
}
