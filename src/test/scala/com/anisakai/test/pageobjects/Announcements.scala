package com.anisakai.test.pageobjects
import org.openqa.selenium.{By, WebElement}
import java.util.concurrent.TimeUnit
import com.anisakai.test.Config
import org.scalatest.concurrent.Eventually
import com.github.javafaker.Faker
/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/3/13
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
object Announcements extends Announcements

class Announcements extends Portal {

  var status = 0

  def clickOptions() {

    click on cssSelector("a[title='Message of the Day Options']")

  }

  def selectSubject() {
    if (radioButtonGroup("show-subject").value == "false") {
      radioButtonGroup("show-subject").value = "true"
    } else {
      radioButtonGroup("show-subject").value = "false"
    }
  }

  def checkCurrent() {
    switch to frame("Mainaf974629x55bbx48bdx9a8fxccbcc0d03747")
    if (webDriver.findElement(By.className("synopticList")).getText().contains("Sakai Administrator")) {
      status = 1
    } else {
      status = 0
    }
  }

  def updateMotd() {
    click on id("eventSubmit_doUpdate")
  }

  def isChanged() : Boolean = {
    if (status == 0) {
      return webDriver.findElement(By.className("textPanelFooter")).isDisplayed()
    } else {
      return webDriver.findElement(By.className("textPanel")).isDisplayed()
    }

  }

}
