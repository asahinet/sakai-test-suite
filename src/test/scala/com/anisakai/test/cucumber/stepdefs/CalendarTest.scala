package com.anisakai.CalendarObj.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

import com.anisakai.test.pageobjects.CalendarObj

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/6/13
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
class CalendarTest extends ScalaDsl with EN {

  /*Given( """^I am on the '(.+)' page$""") { (url: String) =>
    CalendarObj.navigateToPage(url)
  } */
  Given("""^I am logged in with '(.+)' as username and '(.+)' as password$"""){ (eid: String, password: String) =>
    CalendarObj.login(eid, password)
  }
  Then("""^I should be on my workspace$"""){ () =>
    assertTrue(CalendarObj.isMyWorkspace())
  }
  When("""^I select Calendar in left nav bar$"""){ () =>
    CalendarObj.goToCalendar()
  }
  Then("""^I should be on my calendar$"""){ () =>
    assertTrue(CalendarObj.checkCalendar())
  }
  When("""^I click the add button$"""){ () =>
    CalendarObj.addCalEvent()
  }
  When("""^I create an event with random data$"""){ () =>
    CalendarObj.createRandomEvent()
  }
  Then("""^the event should be added to my calendar$"""){ () =>
    assertTrue(CalendarObj.isAdded())
  }


}
