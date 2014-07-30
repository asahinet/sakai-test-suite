package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.openqa.selenium.By

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/3/13
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
object Announcements extends Announcements

class Announcements extends Page {

  var status = 0

  def clickOptions() {
    if (Config.defaultPortal == "xsl") {
      switch to defaultContent
      switch to frame(5)
    }
    switch to frame(0)
    click on xpath("//*[@title='Message of the Day Options']")

  }

  def selectSubject() {
    if (radioButtonGroup("show-subject").value == "false") {
      radioButtonGroup("show-subject").value = "true"
    } else {
      radioButtonGroup("show-subject").value = "false"
    }
  }

  def checkCurrent() {
    Portal.getToFrameZero
    if (!className("textPanelHeader").findElement(webDriver).isDefined) {
      status = 1
    } else {
      status = 0
    }
  }

  def updateMotd() {
    click on id("eventSubmit_doUpdate")
  }

  def isChanged(): Boolean = {
    if (status == 1) {
      return webDriver.findElement(By.className("textPanelHeader")).isDisplayed()
    } else {
      return webDriver.findElement(By.className("textPanel")).isDisplayed()
    }

  }

}
