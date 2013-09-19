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
  var siteManageTool = new SiteManageTool();

  When("""^I create a site with random data$"""){ () =>
    siteManageTool.createRandomSite()
  }

  When("""^add '(.+)' as a.? '(.+)'$"""){ (eid: String, role : String) =>
    siteManageTool.addUserWithRole(eid, role)
  }

  Then("""^I should see '(.+)' with a role of '(.+)'$"""){ (eid: String, role : String) =>
    siteManageTool.verifyUserHasRole(eid, role)

  }

}
