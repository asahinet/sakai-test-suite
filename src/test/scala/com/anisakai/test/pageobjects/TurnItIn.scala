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

  def warningMessage: Boolean = className("alertMessage").findElement(webDriver).isDefined

  def assignmentStatus(status: String, assignmentTitle: String): Boolean = webDriver.findElement(By.xpath("//tr[contains(.,'" + assignmentTitle + "')]/td[3]")).getText.toLowerCase.contains(status.toLowerCase)

  def turnItInExists(assignmentTitle: String): Boolean = {
    click on xpath("//a[.='Edit " + assignmentTitle + "']")
    Portal.getToFrameZero
    checkbox("new_assignment_use_review_service").isSelected
  }

  def uploadFile {
    webDriver.findElement(By.className("upload")).sendKeys(FileUtil.createRandomTextFile(3))
    click on name("post")
    eventually(Portal.getToFrameZero)
    click on name("eventSubmit_doConfirm_assignment_submission")
    Portal.getToFrameZero
  }
}
