package com.anisakai.test.pageobjects

import com.anisakai.test.util.FileUtil
import org.openqa.selenium.By

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/9/13
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */

object TurnItIn extends TurnItIn

class TurnItIn extends Page {

  def warningMessage(): Boolean = {
    if (!className("alertMessage").findElement(webDriver).isDefined) {
      return false
    } else {
      return true
    }
  }

  def assignmentStatus(status: String, assignmentTitle: String): Boolean = {
    if (webDriver.findElement(By.xpath("//tr[contains(.,'" + assignmentTitle + "')]/td[3]")).getText.toLowerCase.contains(status.toLowerCase)) {
      return true
    } else {
      return false
    }
  }

  def turnItInExists(assignmentTitle: String): Boolean = {
    click on xpath("//a[.='Edit " + assignmentTitle + "']")
    Portal.getToFrameZero

    if (checkbox("new_assignment_use_review_service").isSelected) {
      return true
    } else {
      return false
    }
  }

  def uploadFile() {
    webDriver.findElement(By.className("upload")).sendKeys(FileUtil.createRandomTextFile(3))
    click on name("post")
    eventually(Portal.getToFrameZero)
    click on name("eventSubmit_doConfirm_assignment_submission")
    Portal.getToFrameZero
  }
}
