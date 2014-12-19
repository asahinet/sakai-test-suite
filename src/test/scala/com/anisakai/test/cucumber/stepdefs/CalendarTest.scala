package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects.CalendarObj
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/6/13
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
class CalendarTest extends ScalaDsl with EN with TearDown {
  var eventTitle: String = ""

  When( """^I create an event with random data$""") { () =>
    CalendarObj.addCalEvent
    eventTitle = CalendarObj.createRandomEvent
  }
  Then( """^the event should be added to my calendar$""") { () =>
    assertTrue(CalendarObj.isAdded(eventTitle))
  }


}
