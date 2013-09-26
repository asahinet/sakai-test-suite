package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{VerifyTools, UsersTool, Portal}
import junit.framework.Assert._
import org.openqa.selenium.{By, WebElement}

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/9/13
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
class VerifyToolsTest extends ScalaDsl with EN {
  Given("""^I am logged in as '(.+)' with a password of '(.+)'$"""){ (eid: String, password: String) =>
    VerifyTools.login(eid, password)
  }

  //Scenario: Course Site
  Given("""^Course:I am on '(.+)' '(.+)' site$"""){ (siteName: String, siteType: String) =>

    VerifyTools.gotoSite(siteName, siteType)
  }

  When("""^I click the course tool link the tool should load correctly$"""){ () =>
    assertTrue(VerifyTools.checkTools())
  }

  //Scenario: Project Site
  Given("""^Project:I am on '(.+)' '(.+)' site$"""){ (siteName: String, siteType: String) =>
    VerifyTools.gotoSite(siteName, siteType)
  }

  When("""^I click the project tool link the tool should load correctly$"""){ () =>
    assertTrue(VerifyTools.checkTools())
  }

  //Scenario: Portfolio Site
  Given("""^Portfolio:I am on '(.+)' '(.+)' site$"""){ (siteName: String, siteType: String) =>
    VerifyTools.gotoSite(siteName, siteType)
  }

  When("""^I click the portfolio tool link the tool should load correctly$"""){ () =>
    assertTrue(VerifyTools.checkTools())
  }
}
