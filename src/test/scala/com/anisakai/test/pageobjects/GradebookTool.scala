package com.anisakai.test.pageobjects

import org.openqa.selenium.By
import com.thoughtworks.selenium.Selenium

object GradebookTool extends GradebookTool

class GradebookTool extends Page {

  def addEntry() : String = {
    val itemName: String = faker.letterify("???? ???")
    click on linkText ("Add Gradebook Item(s)")
    textField("gbForm:bulkNewAssignmentsTable:0:title").value = itemName
    textField("gbForm:bulkNewAssignmentsTable:0:points").value = "100"
    click on xpath("//*[@id='gbForm:saveButton']")
    return itemName
  }

  def isAdded(itemName : String) : Boolean = {
    switch to defaultContent
    switch to frame(0)
    return !webDriver.findElements(By.xpath("//*[contains(.,'"+itemName+"')]")).isEmpty
  }

  def editEntry(itemName : String) : String = {
    switch to defaultContent
    switch to frame(0)
    click on xpath("//*[@class='skip' and .='"+itemName+"']/..")
    val newItemName: String = faker.letterify("???? ???")
    textField("gbForm:title").value = newItemName
    textField("gbForm:points").value = "100"
    click on xpath("//*[@id='gbForm:saveButton']")
    return newItemName
  }

  def gradeEntry(itemName : String) {
    click on linkText(itemName)
    switch to defaultContent
    switch to frame(0)
    for(i <- 0 until webDriver.findElements(By.xpath("//*[contains(@id,'Score')]")).size) {
      webDriver.findElements(By.xpath("//*[contains(@id,'Score')]")).get(i).sendKeys("90")
    }
    click on id("gbForm:saveButton2")
  }

  def checkGrade(itemName : String) : Boolean = {
    return webDriver.findElement(By.xpath("//*[.='"+itemName+"']/following-sibling::*[2]")).getText.equals("90/100")
  }

  def deleteEntry(itemName : String) {
    click on linkText(itemName)
    switch to defaultContent
    switch to frame(0)
    click on id("gbForm:removeAssignment")
    checkbox("gbForm:removeConfirmed").select()
    click on id("gbForm:_idJsp38")
  }


}
