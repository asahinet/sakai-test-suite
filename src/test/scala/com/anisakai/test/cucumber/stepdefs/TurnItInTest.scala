package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{TurnItIn, AssignmentTool, Portal}
import junit.framework.Assert._
import com.anisakai.test.Config


/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/9/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
class TurnItInTest extends ScalaDsl with EN with TearDown {

  var assignmentTitle: String = ""
  var eid : String = ""
  var password: String = ""

  def login(role: String) {
    Portal.navigateToPage(Config.targetServer)
    Portal.logout()

    if (role.equalsIgnoreCase("admin")) {
      eid = Config.defaultAdminEid
      password = Config.defaultAdminPassword
    } else if (role.equalsIgnoreCase("instructor")) {
      eid = Config.defaultInstructorEid
      password = Config.defaultInstructorPassword
    } else if (role.equalsIgnoreCase("student")) {
      eid = Config.defaultStudentEid
      password = Config.defaultStudentPassword
    }
    Portal.enterEid(eid)
    Portal.enterPassword(password)
    Portal.login()
  }

  Given("""^I'm logged in as an? '(.+)' on a Turn It In instance$"""){ (role : String) =>
    login(role)
  }

  When("""^I add a Turn It In assignment '(.+)'$"""){ (correct: String) =>
    Portal.gotoSite("TurnItIn Test 01")
    Portal.gotoTool("Assignments")
    AssignmentTool.gotoAdd()
    if (correct.equalsIgnoreCase("correctly")) {
      assignmentTitle = AssignmentTool.assignment(true, true)
    } else {
      assignmentTitle = AssignmentTool.assignment(true, false)
    }
  }

  Then("""^I should get a warning message$"""){ () =>
    assertTrue(TurnItIn.warningMessage())
  }

  Then("""^The status of the assignment should be '(.+)'$"""){ (status : String) =>
    assertTrue(TurnItIn.assignmentStatus(status, assignmentTitle))
  }

  Given("""^A course exists with a Turn It In assignment$"""){ () =>
    login("instructor")
    Portal.gotoSite("TurnItIn Test 01")
    Portal.gotoTool("Assignments")
    assertTrue(TurnItIn.turnItInExists(assignmentTitle))
  }

  When("""^I submit a Turn It In assignment$""") { () =>
    Portal.gotoSite("TurnItIn Test 01")
    Portal.gotoTool("Assignments")
    AssignmentTool.openAssignment(assignmentTitle)
    TurnItIn.uploadFile()
  }






}
