package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects.Announcements
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/3/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */

class AnnouncementsTest extends ScalaDsl with EN with TearDown {

  When( """^I click on the MOTD Options button$""") { () =>
    Announcements.checkCurrent
    Announcements.clickOptions
  }
  When( """^I select the Show Announcement subject radio button$""") { () =>
    Announcements.selectSubject
  }
  When( """^I click the update button$""") { () =>
    Announcements.updateMotd
  }
  Then( """^the Message of the day text should contain '(.+)'$""") { (motd: String) =>
    assertTrue(Announcements.isChanged)
  }

}
