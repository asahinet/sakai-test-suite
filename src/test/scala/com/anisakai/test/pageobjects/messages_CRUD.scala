package com.anisakai.test.pageobjects
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select
import com.anisakai.test.util.FileUtil


/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/23/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */

object messages_CRUD extends messages_CRUD

class messages_CRUD extends Page {

  def addMessage(attachment: Boolean) : String = {
    val messageSub = faker.letterify("???????")
    click on linkText("Compose Message")
    def select = new Select(webDriver.findElement(By.id("compose:list1")))
    select.deselectAll()
    select.selectByVisibleText("All Participants")
    textField("compose:subject").value = messageSub
    Portal.richTextEditor()
    if (attachment) {
      click on name("compose:_id72")
      webDriver.findElement(By.className("upload")).sendKeys(FileUtil.createRandomTextFile(3))
      textField("url").value = "www.google.com"
      click on id("add_url")
      click on id("attachButton")
      switch to defaultContent
      switch to frame(0)
    }
    click on name("compose:_id89")
    return messageSub
  }

  def addMessage() : String = {
    return addMessage(false)
  }

  def isAdded(messageSub: String) : Boolean = {
    click on linkText("Sent")
    switch to defaultContent
    switch to frame(0)
    if (webDriver.findElements(By.xpath("//a[@title='"+messageSub+"']")).isEmpty()) {
      false
    } else {
      true
    }

  }

}
