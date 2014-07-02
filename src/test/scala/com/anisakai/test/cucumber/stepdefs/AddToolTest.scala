package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{SiteManageTool, UsersTool, Portal}
import com.anisakai.test.Config

class AddToolTest extends ScalaDsl with EN with TearDown {

  Given("""^A new site is created$""") { () =>
    SiteManageTool.createSiteWithSitesTool("Course", "New Tool Test 1", "new-tool-test-1")
  }

  And("""^I add a list of tools$""") { () =>

  }
}
