package com.anisakai.test.pageobjects

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Created by gareth on 12/10/14.
 */

object Hookup extends Hookup

class Hookup extends Page {
  def getToHookupFrame {
    switch to defaultContent
    switch to frame("wciframe")
  }

  def createMeeting: String = {
    getToHookupFrame
    click on linkText("Schedule Meeting")
    val meetingName = faker.letterify("??????")
    textField("name").value = meetingName
    click on cssSelector("[value=Save]")
    meetingName
  }

  def meetingData: Map[String, String] = {
    var meeting: Map[String, String] = Map()
    meeting += ("name" -> faker.letterify("??????"))
    meeting += ("description" -> faker.sentence(5))

    val rand = new scala.util.Random(System.currentTimeMillis)

    var range = 1 to 3
    // 1 = days only, 2 = time only, 3 = both
    val timeChange = range(rand.nextInt(range length))
    val currentStartTime = DateTime.parse(textField("startTime").value, DateTimeFormat.forPattern("YYYY/MM/dd hh:mm a"))
    var newStartTime = currentStartTime
    if (timeChange == 1) {
      range = 1 to 30 // random number of days
      newStartTime = currentStartTime.plusDays(range(rand.nextInt(range length)))
    } else if (timeChange == 2) {
      range = 1 to 240 // random number of minutes
      newStartTime = currentStartTime.plusMinutes(range(rand.nextInt(range length)))
    } else {
      range = 1 to 30 // random number of days
      newStartTime = currentStartTime.plusDays(range(rand.nextInt(range length)))
      range = 1 to 240 // random number of minutes
      newStartTime = newStartTime.plusMinutes(range(rand.nextInt(range length)))
    }

    range = 1 to 2 // email or no email
    val email = range(rand.nextInt(range length))

    meeting += ("start" -> newStartTime.toString("YYYY/MM/dd hh:mm a"))
    meeting += ("email" -> email.toString)
    meeting
  }

  def createRandomMeeting: String = {
    getToHookupFrame
    click on linkText("Schedule Meeting")
    val meeting = meetingData
    textField("name").value = meeting.get("name").get
    textArea("welcomeMessage").value = meeting.get("description").get
    textField("startTime").value = meeting.get("start").get
    if (meeting.get("email") == "1") {
      radioButtonGroup("email").value = "Yes"
    } else {
      radioButtonGroup("email").value = "No"
    }
    click on cssSelector("[value=Save]")
    meeting.get("name").get
  }

  def deleteMeeting(meetingName: String) {
    getToHookupFrame
    click on linkText("My Meetings")
    click on xpath("//*[contains(text(), '"+meetingName+"')]/../..//a[contains(@href,'deleteMeeting')]")
  }

  def isMeetingInList(meetingName: String): Boolean = {
    if (xpath("//*[contains(text(),'"+meetingName+"')]").findElement(webDriver).isDefined) {
      true
    } else {
      false
    }
  }

  def errors: Boolean = {
    getToHookupFrame
    if(xpath("//*[contains(text(),'error')]").findElement(webDriver).isDefined || xpath("//*[contains(text(),'Error')]").findElement(webDriver).isDefined) {
      true
    } else {
      false
    }
  }
}
