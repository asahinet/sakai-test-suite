package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._
import com.anisakai.test.pageobjects.Portal
import cucumber.runtime.PendingException

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
class LoginTest extends ScalaDsl with EN {
  lazy val gatewayPage = new Portal()


  Given( """^I am on the '(.+)' gateway page$""") {
    (url: String) =>
      gatewayPage.navigateToPage(url)
  }

  When( """^I enter '(.+)' for user""") {
    (eid: String) =>
      gatewayPage.enterEid(eid)
  }

  When( """^I enter '(.+)' for password""") {
    (password: String) =>
      gatewayPage.enterPassword(password)
  }

  When( """^I click the Login button""") {
    gatewayPage.login();
  }


  Then( """^I should see my workspace""") {
      assertTrue(gatewayPage.isMyWorkspace())
  }

  After() {
    //gatewayPage.webDriver.quit();
  }
}