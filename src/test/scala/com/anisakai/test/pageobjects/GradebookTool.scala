package com.anisakai.test.pageobjects

import org.openqa.selenium.By

object GradebookTool extends GradebookTool

class GradebookTool extends Page {

  def addEntry(): String = {
    val itemName: String = faker.letterify("???? ???")
    Portal.xslFrameOne
    click on linkText("Add Gradebook Item(s)")
    textField("gbForm:bulkNewAssignmentsTable:0:title").value = itemName
    textField("gbForm:bulkNewAssignmentsTable:0:points").value = "100"
    click on xpath("//*[@id='gbForm:saveButton']")
    return itemName
  }

  def isAdded(itemName: String): Boolean = {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
    Portal.xslFrameOne
    if (xpath("//*[contains(.,'" + itemName + "')]").findElement(webDriver).isDefined) {
      return true
    } else {
      return false
    }
  }

  def editEntry(itemName: String): String = {
    Portal.xslFrameOne
    click on xpath("//*[@class='skip' and .='" + itemName + "']/..")
    val newItemName: String = faker.letterify("???? ???")
    textField("gbForm:title").value = newItemName
    textField("gbForm:points").value = "100"
    click on xpath("//*[@id='gbForm:saveButton']")
    return newItemName
  }

  def gradeEntry(itemName: String) {
    Portal.xslFrameOne
    click on linkText(itemName)
    Portal.xslFrameOne
    for (i <- 0 until webDriver.findElements(By.xpath("//*[contains(@id,'Score')]")).size) {
      webDriver.findElements(By.xpath("//*[contains(@id,'Score')]")).get(i).sendKeys("90")
    }
    click on id("gbForm:saveButton2")
  }

  def checkGrade(itemName: String): Boolean = {
    Portal.xslFrameOne
    return webDriver.findElement(By.xpath("//*[.='" + itemName + "']/following-sibling::*[2]")).getText.equals("90/100")
  }

  def deleteEntry(itemName: String) {
    Portal.xslFrameOne
    click on linkText(itemName)
    Portal.xslFrameOne
    click on id("gbForm:removeAssignment")
    checkbox("gbForm:removeConfirmed").select()
    click on id("gbForm:_idJsp38")
  }


}
