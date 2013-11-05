package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{GradebookTool, TurnItIn, AssignmentTool, Portal}
import junit.framework.Assert._
import com.anisakai.test.Config
import org.openqa.selenium.By
import org.scalatest.selenium.WebBrowser.{click, switch}

class gradebook_instructor_test extends ScalaDsl with EN with TearDown {

  var itemName: String = ""

  And("""^I am on a course with '(.+)' tool$""") { (tool : String) =>
    Portal.gotoSiteDirectly(Config.defaultCourseSiteId)
    Portal.gotoTool(tool, true)
  }

  When("""^I add an entry to gradebook$""") { () =>
    itemName = GradebookTool.addEntry()
  }

  Then("""^The entry '(.+)' be visible in the list$""") { (visible : String) =>
    if (visible.equalsIgnoreCase("should")) {
      assertTrue(GradebookTool.isAdded(itemName))
    } else if (visible.equalsIgnoreCase("shouldnt")) {
      assertFalse(GradebookTool.isAdded(itemName))
    }
  }

  When("""^I change the existing gradebook entry$""") { () =>
    itemName = GradebookTool.editEntry(itemName)
  }

  When("""^I grade the entry$""") { () =>
    GradebookTool.gradeEntry(itemName)

  }

  Then("""^The grade should be visible$""") { () =>
    assertTrue(GradebookTool.checkGrade(itemName))
  }

  When("""^I delete the gradebook entry$""") { () =>
    GradebookTool.deleteEntry(itemName)
  }


}
