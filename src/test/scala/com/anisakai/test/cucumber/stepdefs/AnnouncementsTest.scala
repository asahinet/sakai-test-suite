package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._
import com.anisakai.test.pageobjects.{Announcements, Google, Page, Portal}

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/3/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */

class AnnouncementsTest extends ScalaDsl with EN {
  lazy val test = new Announcements()

  Given( """^I am on the '(.+)' entry page$""") { (url: String) =>
    test.navigateToPage(url)
  }
  When("""^When I login with '(.+)' as the username and '(.+)' as the password$"""){ (un: String, pw: String) =>
    test.login(un,pw)
  }
  Then("""^I should see myworkspace$"""){ () =>
    assertTrue(test.isMyWorkspace())
    test.checkCurrent()
  }
  When("""^I click on the MOTD Options button$"""){ () =>
    test.clickOptions()
  }
  When("""^I select the Show Announcement subject radio button$"""){ () =>
    test.selectSubject()
  }
  When("""^I click the update button$"""){ () =>
    test.updateMotd()
  }
  Then("""^the Message of the day text should contain '(.+)'$"""){ (motd: String) =>
    assertTrue(test.isChanged())
  }
}
