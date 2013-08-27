package com.anisakai.test.pageobjects

import org.openqa.selenium.{By, WebElement}
import java.util.concurrent.TimeUnit
import com.anisakai.test.Config
import org.scalatest.concurrent.Eventually
import com.github.javafaker.Faker

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
class Portal extends Page with Eventually{
  def eid: TextField = textField("eid");
  def password: PasswordField = pwdField("pw");

  def login() {
    submit();
  }

  def login(eid : String, password : String) {
    go to Config.targetServer
    enterEid(eid)
    enterPassword(password)
    submit()
  }

  def enterEid(eid: String) {
    this.eid.value = eid
  }

  def enterPassword(password: String) {

    this.password.value = password
  }

  def isMyWorkspace() : Boolean = {
    return webDriver.findElement(By.className("siteTitle")).getText().startsWith("My Workspace")
  }

  def gotoSite(siteName : String) {
    click on partialLinkText(siteName)
    //webDriver.findElementByXPath("//li[@class='nav_menu']/a[@title='" + siteName + "']").click();
    //driver.findElement(By.cssSelector("a[title=\"Administration Workspace: Administration Workspace\"] > span")).click();
    //click on cssSelector("a[title=\"Administration Workspace: Administration Workspace\"] > span")
    //click on cssSelector("a:contains('" + siteName + "')")
  }

  def gotoTool(toolName : String) {
 //   switch to defaultContent
    if (toolName.equals("Site Setup") && !Config.sakaiDistro.equalsIgnoreCase("ani"))  {
      if (linkText("Worksite Setup").findElement(webDriver).isDefined) {
        click on linkText("Worksite Setup")
      } else if (linkText("Site Info").findElement(webDriver).isDefined){
        click on linkText("Site Info")
      }
    } else {
      click on linkText(toolName)
    }
    eventually (switch to frame(0))
  }

  def logout() {
    switch to defaultContent
    click on linkText("Logout")
  }
}
