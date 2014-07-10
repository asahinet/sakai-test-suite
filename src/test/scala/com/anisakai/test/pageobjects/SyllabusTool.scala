package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}

object SyllabusTool extends SyllabusTool
class SyllabusTool extends Page {

  def addSyllabus() : String = {
    return addSyllabus(false)
  }

  def addSyllabus(redirect: Boolean) : String = {
    def wait = new WebDriverWait(webDriver, 10)

    val syllabusName: String = faker.letterify("??????")
    Portal.xslFrameOne
    if (redirect) {
      click on partialLinkText("Redirect")
      textField("redirectForm:urlValue").value = Config.targetServer
      click on name("redirectForm:_id13")
    } else {
      click on partialLinkText("Add Item")
      textField("newTitle").value = syllabusName
      click on xpath("//button[.='Add']")
      if (!xpath("//*[.='"+syllabusName+"']/../../..//*[.='Click to add body text']").webElement.isDisplayed) {
        click on xpath("//*[contains(text(), '"+syllabusName+"')]/../../*[contains(@title, 'Click to expand')]")
      }
      click on xpath("//*[.='"+syllabusName+"']/../../..//div[.='Click to add body text']")
      eventually {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@title,'Rich text editor')]")))
        Portal.richTextEditor()
      }
      Portal.xslFrameOne
      click on xpath("//*[.='ok']")
    }
    return syllabusName
  }

  def noneExist(): Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    return !webDriver.findElements(By.xpath("//*[contains(text(), 'No Syllabus currently exists.')]")).isEmpty

  }

  def syllabusExists(syllabusName: String) : Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne

    if (xpath("//*[.='"+syllabusName+"']").findElement(webDriver).isDefined) { true } else { false }

  }

  def redirectExists(syllabusName: String) : Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
      if (!webDriver.findElements(By.xpath("//iframe[@src='"+Config.targetServer+"']")).isEmpty()) { true } else { false }
  }

  def editSyllabus(syllabusName: String) : String = {
    val newName: String = faker.letterify("??????")
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    click on xpath("//*[.='"+syllabusName+"']")
    xpath("//input[@class='input-medium']").webElement.clear
    xpath("//input[@class='input-medium']").webElement.sendKeys(newName)
    click on xpath("//*[.='ok']")
    return newName
  }

  def removeAllSyllabus() {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    if (!webDriver.findElements(By.xpath("//iframe[@src='"+Config.targetServer+"']")).isEmpty()) {
      click on partialLinkText("Redirect")
      textField("redirectForm:urlValue").clear
      click on name("redirectForm:_id13")
    }
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    click on xpath("//a[contains(text(),'Bulk Edit')]")
    webDriver.findElement(By.xpath("//th[contains(text(), 'Remove')]/input")).click
    click on cssSelector("[title='Update']")
    click on xpath("//input[contains(@value,'Update')]")
  }



}
