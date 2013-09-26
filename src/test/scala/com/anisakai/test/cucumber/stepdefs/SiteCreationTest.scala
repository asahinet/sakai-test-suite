package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import cucumber.runtime.PendingException
import com.anisakai.test.pageobjects.SiteManageTool

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/24/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteCreationTest extends ScalaDsl with EN with ScreenShotOnFailure {

  When("""^I create a site with random data$"""){ () =>
    var siteTitle = SiteManageTool.createRandomSite("course")
    SiteManageTool.findSiteAndEdit(siteTitle)
  }

  When("""^add '(.+)' as a.? '(.+)'$"""){ (eid: String, role : String) =>
    SiteManageTool.addUserWithRole(eid, role)
  }

  Then("""^I should see '(.+)' with a role of '(.+)'$"""){ (eid: String, role : String) =>
    SiteManageTool.verifyUserHasRole(eid, role)

  }

}
