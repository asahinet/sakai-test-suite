package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.openqa.selenium.By

object SyllabusTool extends SyllabusTool
class SyllabusTool extends Page {

  def addSyllabus() : String = {
    return addSyllabus(false)
  }

  def addSyllabus(redirect: Boolean) : String = {
    val syllabusName: String = faker.letterify("??????")
    Portal.xslFrameOne
    if (redirect) {
      click on linkText("Redirect")
      textField("redirectForm:urlValue").value = Config.targetServer
      click on name("redirectForm:_id13")
    } else {
      click on linkText("Add Item")
      textField("newTitle").value = syllabusName
      click on linkText("Add")
      click on linkText("Click to add body text")
      Portal.richTextEditor()
      click on linkText("ok")
    }
    return syllabusName
  }

  def noneExist(): Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.getToFrameZero
    return !webDriver.findElements(By.xpath("//*[contains(text(), 'No Syllabus currently exists.')]")).isEmpty

  }

  def syllabusExists(syllabusName: String) : Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne

    if (linkText(syllabusName).findElement(webDriver).isDefined) { true } else { false }

    /*if(!webDriver.findElements(By.className("textPanelHeader")).isEmpty) {
      if (webDriver.findElements(By.className("textPanelHeader")).get(0).getText.equalsIgnoreCase(syllabusName)) {
        return true
      } else {
        return false
      }
    } else {
      return false
    }*/
  }

  def redirectExists(syllabusName: String) : Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.getToFrameZero
      if (!webDriver.findElements(By.xpath("//iframe[@src='"+Config.targetServer+"']")).isEmpty()) { true } else { false }
  }

  def editSyllabus(syllabusName: String) : String = {
    val newName: String = faker.letterify("??????")
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    click on linkText("Create/Edit")
    click on linkText(syllabusName)
    textField("_id3:title").value = newName
    click on name("_id3:_id55")
    return newName
  }

  def removeAllSyllabus() {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    if (!webDriver.findElements(By.xpath("//iframe[@src='"+Config.targetServer+"']")).isEmpty()) {
      click on linkText("Redirect")
      textField("redirectForm:urlValue").clear()
      click on name("redirectForm:_id13")
    }
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    click on linkText("Bulk Edit")
    for (i <- 0 until webDriver.findElements(By.xpath("//*[contains(@title, 'Select to Remove:')]")).size) {
      webDriver.findElements(By.xpath("//*[contains(@title, 'Select to Remove:')]")).get(i).click()
    }
    click on cssSelector("[title='Update']")
    click on name("_id2:_id16")
  }



}
