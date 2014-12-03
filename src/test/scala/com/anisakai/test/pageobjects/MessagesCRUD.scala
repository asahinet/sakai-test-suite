package com.anisakai.test.pageobjects

import com.anisakai.test.util.FileUtil
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select
import com.anisakai.test.Config


/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/23/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */

object MessagesCRUD extends MessagesCRUD

class MessagesCRUD extends Page {

  def addMessage(attachment: Boolean): String = {
    val messageSub = faker.letterify("???????")
    Portal.xslFrameOne
    click on linkText("Compose Message")
    def select = new Select(webDriver.findElement(By.id("compose:list1")))
    select.deselectAll()
    select.selectByIndex(0)
    textField("compose:subject").value = messageSub
    Portal.richTextEditor()
    Portal.xslFrameOne
    if (attachment) {
      if (!Config.sakaiVersion.contains("10.")) {
        click on name("compose:_id72")
      } else {
        click on xpath("//*[@value = 'Add attachments']")
      }
      webDriver.findElement(By.className("upload")).sendKeys(FileUtil.createRandomTextFile(3))
      textField("url").value = "www.google.com"
      click on id("add_url")
      click on id("attachButton")
    }
    if (!Config.sakaiVersion.contains("10.")) {
      click on name("compose:_id89")
    } else {
      click on xpath("//*[@value = 'Send ']")
    }
    return messageSub
  }

  def addMessage(): String = {
    return addMessage(false)
  }

  def isAdded(messageSub: String): Boolean = {
    Portal.goToTool("Messages", true)
    Portal.xslFrameOne
    click on linkText("Sent")
    Portal.xslFrameOne
    if (!xpath("//a[@title='" + messageSub + "']").findElement(webDriver).isDefined) {
      false
    } else {
      true
    }

  }

}
