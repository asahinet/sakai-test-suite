package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{UsersToolPage, GatewayPage}
import junit.framework.Assert._
import cucumber.runtime.PendingException

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
class UsersToolTest extends ScalaDsl with EN {
  lazy val usersToolPage = new UsersToolPage()

  Given("""^I am logged on as '(.+)' with a password of '(.+)'$"""){ (eid: String, password: String) =>
    usersToolPage.login(eid, password)
  }
  Given("""^I am on the '(.+)' site using the '(.+)' tool$"""){ (siteName : String, toolName : String) =>
      usersToolPage.gotoSite(siteName)
      usersToolPage.gotoTool(toolName)
  }
  When("""^I enter '(.+)' in the search textfield$"""){ (searchText: String) =>
    usersToolPage.enterSearchText(searchText)
  }
  When("""^I click the Search button$"""){ () =>
    usersToolPage.submitSearch()
  }
  Then("""^I should see the '(.+)' user$"""){ (eid:String) =>
    assertTrue(usersToolPage.foundUser(eid))
  }

  Given("""^I search for a user with an eid of '(.+)'$"""){ (searchText:String) =>
    usersToolPage.enterSearchText(searchText)
    usersToolPage.submitSearch()
    assertTrue(usersToolPage.foundUser(searchText))
  }

  When("""^I edit their first name to '(.+)'$"""){ (firstName : String) =>
    usersToolPage.editFirstName(firstName)
  }

  Then("""^I should see the first name change$"""){ () =>
    usersToolPage.hasFirstNameChanged
  }

  Then("""^I should logout$"""){ () =>
    usersToolPage.logout
  }

  After() {
    //usersToolPage.webDriver.quit()
  }
}
