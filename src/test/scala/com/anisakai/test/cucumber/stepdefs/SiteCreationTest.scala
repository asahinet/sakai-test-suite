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
class SiteCreationTest extends ScalaDsl with EN {
  var siteManageTool = new SiteManageTool();

  Then("""^create a site with random data$"""){ () =>
    siteManageTool.createRandomSite()
  }
}
