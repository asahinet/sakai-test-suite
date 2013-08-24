package com.anisakai.test.pageobjects

import org.scalatest.concurrent.Eventually
import org.openqa.selenium.{By, WebElement}
import scala.collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
class UsersToolPage extends GatewayPage with Eventually {
  var currentEid : String = null
  var currentFirstName : String = null
  var oldFirstName : String = null

  def search: TextField = textField("search")
  def firstName: TextField = textField("first-name")

  def submitSearch() {
    click on linkText("Search")
  }

  def enterSearchText(search : String) {
    this.search.value = search
  }

  def clickAllLinks() {
    var linkElements = webDriver.findElements(By.tagName("a"));
    var linkTexts = new Array[String](linkElements.length)
    var i : Int = 0;

    //extract the link texts of each link element
    for (e  <- linkElements)
    {
      linkTexts(i) = e.getText();
      i = i + 1
    }

    linkTexts.foreach {(t : String) =>
      webDriver.findElement(By.linkText(t)).click();
      System.out.println("\"" + t + "\"" + " is working.");
      webDriver.navigate().back();
    }

  }

  def gotoSite(siteName : String) {
    //switch to defaultContent
    click on partialLinkText(siteName)
    //webDriver.findElementByXPath("//li[@class='nav_menu']/a[@title='" + siteName + "']").click();
    //driver.findElement(By.cssSelector("a[title=\"Administration Workspace: Administration Workspace\"] > span")).click();
    //click on cssSelector("a[title=\"Administration Workspace: Administration Workspace\"] > span")
    //click on cssSelector("a:contains('" + siteName + "')")
  }

  def gotoTool(toolName : String) {
 //   switch to defaultContent
    click on linkText(toolName)
    switch to frame(0)
  }

  def foundUser(eid : String) : Boolean = {
    currentEid = eid;
    return partialLinkText(eid) != null
  }

  def editFirstName(firstName : String) {
    click on partialLinkText(currentEid)
    oldFirstName = this.firstName.value
    this.firstName.value = firstName
    this.currentFirstName = firstName
    click on name("eventSubmit_doSave")
  }

  def hasFirstNameChanged() : Boolean = {
    return cssSelector("h4:contains('" + currentFirstName + "')") != null
  }

}
