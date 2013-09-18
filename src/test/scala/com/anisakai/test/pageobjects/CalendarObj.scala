package com.anisakai.test.pageobjects

import org.scalatest.selenium.WebBrowser.click
import org.openqa.selenium.By
import java.util.Calendar

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/6/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
class CalendarObj extends Portal {
  val month = Calendar.MONTH.toString()
  val day = Calendar.DAY_OF_WEEK.toString()
  val year = Calendar.YEAR.toString()
  def goToCalendar() {
    click on cssSelector("a[title='Use calendar to post information about activities and events of interest to your site participants.']")
  }

  def checkCalendar() : Boolean = {
    return webDriver.findElement(By.className("siteTitle")).getText().contains("Calendar")
  }

  def addCalEvent() {
    click on webDriver.findElement(By.className("firstToolBarItem"))
  }

  def createRandomEvent() {
    textField("activityTitle").value = faker.letterify("??????")
    singleSel("month").value = month
    singleSel("day").value = day
    singleSel("yearSelect").value = year

  }

}
