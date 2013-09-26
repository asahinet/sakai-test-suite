package com.anisakai.test.pageobjects

import org.scalatest.selenium.WebBrowser.click
import org.openqa.selenium.By
import java.util.Calendar
import org.openqa.selenium.support.ui.Select

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/6/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
object CalendarObj extends CalendarObj
class CalendarObj extends Portal {
  val cal = Calendar.getInstance
  val day = cal.get(Calendar.DAY_OF_MONTH)
  val month = cal.get(Calendar.MONTH) + 1
  val year = cal.get(Calendar.YEAR)
  var hour = 0
  var am_pm = "am"
  val eventTitle = faker.letterify("??????????")
  if (cal.get(Calendar.AM_PM) == 0 && cal.get(Calendar.HOUR_OF_DAY) != 11) {
    hour = cal.get(Calendar.HOUR_OF_DAY) + 1
    am_pm = "am"
  } else if (cal.get(Calendar.HOUR_OF_DAY) == 23) {
    hour = cal.get(Calendar.HOUR_OF_DAY) - 11
    am_pm = "am"
  } else if (cal.get(Calendar.HOUR_OF_DAY) == 11) {
    hour = cal.get(Calendar.HOUR_OF_DAY) + 1
    am_pm = "pm"
  } else {
    hour = cal.get(Calendar.HOUR_OF_DAY) - 11
    am_pm = "pm"
  }
  def goToCalendar() {
    click on cssSelector("a[title='Use calendar to post information about activities and events of interest to your site participants.']")
  }

  def checkCalendar() : Boolean = {
    return webDriver.findElement(By.className("title")).getText().contains("Calendar")
  }

  def addCalEvent() {
    switch to frame(0)
    click on webDriver.findElement(By.className("firstToolBarItem"))
  }

  def createRandomEvent() {
    textField("activitytitle").value = eventTitle
    singleSel("month").value = month.toString()
    singleSel("day").value = day.toString()
    singleSel("yearSelect").value = year.toString()
    singleSel("startHour").value = hour.toString()
    singleSel("startMinute").value = "0"
    def ampm = new Select(webDriver.findElement(By.name("startAmpm")))
    ampm.selectByVisibleText(am_pm)
    singleSel("eventType").value = "Exam"
    textArea("location").value = "Online"
    switch to frame(xpath("//iframe[contains(@title,'Rich text editor')]"))
    webDriver.switchTo().activeElement().sendKeys(faker.letterify("Testing"))
    webDriver.switchTo().window(windowHandle)
    webDriver.switchTo().frame("Main2fb6d8ebx81a3x4a97x9191xe790451e52bb")
    click on name("eventSubmit_doAdd")
  }

  def isAdded() : Boolean = {
    return webDriver.findElement(By.xpath("//E[contains(text(),$eventTitle)]")).isDisplayed()
  }

}
