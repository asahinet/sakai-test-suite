package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._
import com.anisakai.test.pageobjects.{CalendarObj, Portal}
import cucumber.runtime.PendingException

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
class LoginTest extends ScalaDsl with EN with ScreenShotOnFailure {

  Given( """^I am on the '(.+)' gateway page$""") {
    (url: String) =>
      Portal.navigateToPage(url)
  }

  When( """^I enter '(.+)' for user""") {
    (eid: String) =>
      Portal.enterEid(eid)
  }

  When( """^I enter '(.+)' for password""") {
    (password: String) =>
      Portal.enterPassword(password)
  }

  When( """^I click the Login button""") {
    Portal.login();
  }


  Then( """^I should see my workspace""") {
      assertTrue(Portal.isMyWorkspace())
  }

  Given("""^I am logged on as '(.+)' with a password of '(.+)'$"""){ (eid: String, password: String) =>
    Portal.login(eid, password)
  }
  Given("""^I am on the '(.+)' site using the '(.+)' tool$"""){ (siteName : String, toolName : String) =>
    Portal.gotoSite(siteName)
    Portal.gotoTool(toolName)
  }


}