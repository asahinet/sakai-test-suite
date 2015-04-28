package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}

object SyllabusTool extends SyllabusTool

class SyllabusTool extends Page {

  def addSyllabus(redirect: Boolean = false): String = {
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
      eventually {
        if (xpath("//*[.='" + syllabusName + "']/../../../div").webElement.getAttribute("aria-expanded").equals("false")) {
          click on xpath("//*[contains(text(), '" + syllabusName + "')]/../../*[contains(@title, 'Click to expand')]")
        }
        click on xpath("//*[.='" + syllabusName + "']/../../..//div[.='Click to add body text']")
        Portal.richTextEditor
        Portal.xslFrameOne
        click on xpath("//*[.='ok']")
        eventually(click on xpath("//*[contains(text(), '" + syllabusName + "')]/../../*[contains(@title, 'Click to publish')]"))
      }
    }
    syllabusName
  }

  // Returns true if no syllabus exists
  def noneExist: Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    xpath("//*[contains(text(), 'No Syllabus currently exists.')]").findElement(webDriver).isDefined
  }

  // Returns true if a syllabus exists
  def syllabusExists(syllabusName: String): Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    xpath("//*[.='" + syllabusName + "']").findElement(webDriver).isDefined
  }

  // Returns true if a redirect exists
  def redirectExists(syllabusName: String): Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    xpath("//iframe[@src='" + Config.targetServer + "']").findElement(webDriver).isDefined
  }

  def editSyllabus(syllabusName: String): String = {
    val newName: String = faker.letterify("??????")
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    click on xpath("//*[.='" + syllabusName + "']")
    xpath("//input[@class='input-medium']").webElement.clear
    xpath("//input[@class='input-medium']").webElement.sendKeys(newName)
    click on xpath("//*[.='ok']")
    newName
  }

  def removeAllSyllabus {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    // Clears out the redirect source first
    if (xpath("//iframe[@src='" + Config.targetServer + "']").findElement(webDriver).isDefined) {
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
