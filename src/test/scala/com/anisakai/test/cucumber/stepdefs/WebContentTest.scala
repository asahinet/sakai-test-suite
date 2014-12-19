package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects._
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/30/13
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
class WebContentTest extends ScalaDsl with EN with TearDown {
  var source: String = ""
  When( """^I add web content to the tool$""") { () =>
    source = WebContent.addWebContent
  }

  Then( """^The web page should be displayed$""") { () =>
    assertTrue(WebContent.isDisplayed(source))
  }
}
