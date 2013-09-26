package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._
import com.anisakai.test.pageobjects.{Announcements, Page, Portal}

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/3/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */

class AnnouncementsTest extends ScalaDsl with EN {

  Given( """^I am on the '(.+)' entry page$""") { (url: String) =>
    Announcements.navigateToPage(url)
  }
  When("""^When I login with '(.+)' as the username and '(.+)' as the password$"""){ (un: String, pw: String) =>
    Announcements.login(un,pw)
  }
  Then("""^I should see myworkspace$"""){ () =>
    assertTrue(Announcements.isMyWorkspace())
    Announcements.checkCurrent()
  }
  When("""^I click on the MOTD Options button$"""){ () =>
    Announcements.clickOptions()
  }
  When("""^I select the Show Announcement subject radio button$"""){ () =>
    Announcements.selectSubject()
  }
  When("""^I click the update button$"""){ () =>
    Announcements.updateMotd()
  }
  Then("""^the Message of the day text should contain '(.+)'$"""){ (motd: String) =>
    assertTrue(Announcements.isChanged())
  }
}
