package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import cucumber.runtime.PendingException
import com.anisakai.test.pageobjects.{Portal, UsersTool, SiteManageTool}
import cucumber.api.DataTable
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/24/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteCreationTest extends ScalaDsl with EN with TearDown {
  Given( """^the following '(.+)' sites exist:$""") {
    (siteType: String, data: DataTable) =>
      val row = data.asMaps().iterator()
      while (row.hasNext) {
        val map = row.next()
        val siteId = map.get("id")
        val title = map.get("title")
        val description = map.get("description")
        val contactname = map.get("contactname")
        var newlyCreatedSite: Boolean = true
        if (siteType.equalsIgnoreCase("turn it in")) {
          Portal.gotoSite("Administration Workspace")
          Portal.gotoTool("Sites")
          newlyCreatedSite = SiteManageTool.createSiteWithSitesTool(siteType, title, siteId)
        } else {
          Portal.gotoTool("Sites")
          newlyCreatedSite = SiteManageTool.createSiteWithSitesTool(siteType, title, siteId)
          Portal.gotoTool("Site Setup", true)
        }
        SiteManageTool.findSiteAndEdit(title)
        SiteManageTool.editSite(description, description, contactname)
        if (newlyCreatedSite) {
          SiteManageTool.addAllTools()
        }
        SiteManageTool.manageAccess(true, false)
      }
  }

  Given( """^the following memberships exist:$""") {
    (data: DataTable) =>


      val row = data.asMaps().iterator()

      while (row.hasNext) {
        val map = row.next()
        val siteId = map.get("site-id")
        val userEid = map.get("user-eid")
        val role = map.get("role")

        Portal.gotoSiteDirectly(siteId)
        Portal.gotoTool("Site Editor", true)

        SiteManageTool.addUserWithRole(userEid, role)

        // doing this so we can can come back to Site Editor and reset
        Portal.gotoTool("Home")
      }
  }

  Given("""^I navigate to the '(.+)' tool$"""){ (tool:String) =>
    Portal.gotoTool(tool)
  }

  When("""^I create '(.+)' site with a title of '(.+)' and an id of'(.+)'$"""){ (siteType: String, siteTitle : String, siteId : String) =>
    SiteManageTool.createSiteWithSitesTool(siteType, siteTitle, siteId)
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

  Then("""^I add all the tools$"""){ () =>
    SiteManageTool.addAllTools()

  }

  When("""^I edit the '(.+)' site information$"""){ (siteTitle:String) =>
    SiteManageTool.findSiteAndEdit(siteTitle)
    SiteManageTool.editSite("description","description", "john smith")

  }

}
