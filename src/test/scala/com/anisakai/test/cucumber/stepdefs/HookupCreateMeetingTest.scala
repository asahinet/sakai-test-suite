package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{Hookup}
import junit.framework.Assert._


  /**
 * Created by gareth on 12/10/14.
 */
class HookupCreateMeetingTest extends ScalaDsl with EN with TearDown {
    var meetingName: String = ""

    When("""I create a meeting with default time and date""") { () =>
      meetingName = Hookup.createMeeting
    }

    Then("""the meeting '(.+)' be displayed in the list""") { (check: String) =>
      if (check.equalsIgnoreCase("should")) {
        assertTrue(Hookup.isMeetingInList(meetingName))
      } else {
        assertFalse(Hookup.isMeetingInList(meetingName))
      }
    }

    When("""I delete a meeting""") { () =>
      Hookup.deleteMeeting(meetingName)
    }
}
