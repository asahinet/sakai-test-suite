package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.Hookup
import junit.framework.Assert._

/**
 * Created by gareth on 12/10/14.
 */
class HookupBulkMeetingsTest extends ScalaDsl with EN with TearDown {
  var meetingName: String = ""

  When("""I create and delete '(.+)' meetings""") { (numMeetings: Int) =>
    for (i <- 1 to numMeetings) {
      meetingName = Hookup.createRandomMeeting
      Hookup.deleteMeeting(meetingName)
    }
  }

  Then("""there should be no errors""") { () =>
    assertFalse(Hookup.errors)
  }
}
