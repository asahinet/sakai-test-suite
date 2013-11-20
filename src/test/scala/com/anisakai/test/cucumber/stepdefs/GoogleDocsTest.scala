package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{GradebookTool, TurnItIn, AssignmentTool, Portal}
import junit.framework.Assert._
import com.anisakai.test.Config
import org.openqa.selenium.By
import org.scalatest.selenium.WebBrowser.{click, switch}

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 11/4/13
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */

class GoogleDocsTest extends ScalaDsl with EN with TearDown {
  When("""^I add a link to google docs with default properties$"""){ () =>

  }

  Then("""^I should see the google doc in resources$"""){ () =>

  }

  And("""^I set the until date in the '(.+)'$"""){ () =>

  }

  Then("""^Then the resource should not be visible to the student$""") { () =>

  }

}
