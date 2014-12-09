package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects.{Portal, SiteManageTool}
import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.Config

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/24/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteCreationTest extends ScalaDsl with EN with TearDown {
  var maintainRole : String = ""
  var newlyCreatedSite: Boolean = true

  Given( """^the following '(.+)' sites exist:$""") {
    (siteType: String, data: DataTable) =>
      val row = data.asMaps().iterator()
      while (row.hasNext) {
        val map = row.next()
        val siteId = map.get("id")
        val title = map.get("title")
        val description = map.get("description")
        val contactname = map.get("contactname")
//        Portal.gotoAdminWorkspace()
        maintainRole = SiteManageTool.getMaintainRole
        Portal.goToTool("Sites")
        newlyCreatedSite = SiteManageTool.createSiteWithSitesTool(siteType, title, siteId)
        if (newlyCreatedSite) {
          if (Config.sakaiVersion.startsWith("10.")) {
            Portal.goToTool("Worksite Setup", true)
          } else {
            Portal.goToTool("Site Setup", true)
          }
            if (SiteManageTool.findSiteAndEdit(title)) {
              SiteManageTool.editSite(description, description, contactname)
              SiteManageTool.addTools(withSitesTool = true)
              SiteManageTool.manageAccess(true, false)
            }
        }
      }
  }

  Given( """^the following memberships exist:$""") {
    (data: DataTable) =>
        val row = data.asMaps().iterator()

        while (row.hasNext) {
          val map = row.next()
          val siteId = map.get("site-id")
          val userEid = map.get("user-eid")
          var role = map.get("role")

          if (role == "Instructor") {
            role = maintainRole
          }

          Portal.goToSiteDirectly(siteId)
          Portal.goToTool("Site Editor", true)


          SiteManageTool.addUserWithRole(userEid, role)

          // doing this so we can can come back to Site Editor and reset
          Portal.goToTool("Home")
        }

  }

}
