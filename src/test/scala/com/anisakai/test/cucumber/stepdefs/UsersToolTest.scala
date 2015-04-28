package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects.{Portal, UsersTool}
import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
class UsersToolTest extends ScalaDsl with EN with TearDown {

  When( """^I enter '(.+)' in the search textfield$""") { (searchText: String) =>
    UsersTool.enterSearchText(searchText)
  }
  When( """^I click the Search button$""") { () =>
    UsersTool.submitSearch
  }
  Then( """^I should see the '(.+)' user$""") { (eid: String) =>
    assertTrue(UsersTool.foundUser(eid))
  }

  Given( """^I search for a user with an eid of '(.+)'$""") { (searchText: String) =>
    UsersTool.enterSearchText(searchText)
    UsersTool.submitSearch
    assertTrue(UsersTool.foundUser(searchText))
  }

  When( """^I edit their first name to '(.+)'$""") { (firstName: String) =>
    UsersTool.editFirstName(firstName)
  }

  Then( """^I should see the first name change$""") { () =>
    UsersTool.hasFirstNameChanged
  }

  Then( """^I should logout$""") { () =>
    Portal.logout
  }

  Given( """^A user with an eid of '(.+)' does not exist$""") { (eid: String) =>
    UsersTool.findOrCreateUser(eid)
  }

  Given( """^the following users exist:$""") {
    (arg0: DataTable) =>
      val row = arg0.asMaps(classOf[String], classOf[String]).iterator
      while (row.hasNext) {
        val map = row.next
        val eid = map.get("eid")
        val firstname = map.get("firstname")
        val lastname = map.get("lastname")
        val email = map.get("email")
        val usertype = map.get("type")
        val password = map.get("password")

        UsersTool.createUser(eid, firstname, lastname, email, usertype, password)
        UsersTool.enterSearchText(eid)
        UsersTool.submitSearch
        assertTrue(UsersTool.foundUser(eid))
      }
  }

  Given( """^a user with an eid of '(.+)' a firstname of '(.+)' a lastname of '(.+)' an email of '(.+)' that is of type '(.+)' with a password of '(.+)' exists$""") {
    (eid: String, firstname: String, lastname: String, email: String, usertype: String, password: String) =>
      UsersTool.createUser(eid, firstname, lastname, email, usertype, password)
      UsersTool.enterSearchText(eid)
      UsersTool.submitSearch
      assertTrue(UsersTool.foundUser(eid))

  }


  Then( """^Add a user with an eid of '(.+)' a firstname of '(.+)' a lastname of '(.+)' an email of '(.+)' that is of type '(.+)' with a password of '(.+)'$""") {
    (eid: String, firstname: String, lastname: String, email: String, usertype: String, password: String) =>
      UsersTool.createUser(eid, firstname, lastname, email, usertype, password)
      UsersTool.enterSearchText(eid)
      UsersTool.submitSearch
      assertTrue(UsersTool.foundUser(eid))
  }

  Then( """^create a user with random data$""") { () =>
    var newEid = UsersTool.randomUser
    UsersTool.enterSearchText(newEid)
    UsersTool.submitSearch
    assertTrue(UsersTool.foundUser(newEid))

  }
}

