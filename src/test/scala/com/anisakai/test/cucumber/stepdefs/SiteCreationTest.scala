package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import cucumber.runtime.PendingException
import com.anisakai.test.pageobjects.{UsersTool, SiteManageTool}
import cucumber.api.DataTable
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/24/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteCreationTest extends ScalaDsl with EN with ScreenShotOnFailure {
  Given( """^the following '(.+)' sites exist:$""") {
      (siteType : String, data: DataTable) =>
        val row = data.asMaps().iterator()
        while (row.hasNext) {
          val map = row.next()
          val title = map.get("title")
          val description = map.get("description")
          val contactname = map.get("contactname")
          val contactemail = map.get("contactemail")

          if (siteType.equalsIgnoreCase("project")) {
            SiteManageTool.createProjectSite(title, description, description, contactname, contactemail)
          } else {
            assertFalse("type course not implemented yet", false);
          }
        }
    }


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
