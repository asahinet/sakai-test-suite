package com.anisakai.test.pageobjects

import org.openqa.selenium.{By, WebElement}
import java.util.concurrent.TimeUnit
import com.anisakai.test.Config
import org.scalatest.concurrent.Eventually
import com.github.javafaker.Faker
import org.openqa.selenium.NoSuchElementException

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
object Portal extends Portal

class Portal extends Page with Eventually{
//  def eid: TextField = textField("eid")
//  def password: PasswordField = pwdField("pw")

  def login() {
    submit()
  }

  def login(eid : String, password : String) {
    go to Config.targetServer

    logout()

    enterEid(eid)
    enterPassword(password)
    submit()
  }

  def enterEid(eid: String) {
    textField("eid").value = eid
  }

  def enterPassword(password: String) {
    pwdField("pw").value = password
  }

  def isMyWorkspace() : Boolean = {
    return webDriver.findElement(By.className("siteTitle")).getText().startsWith("My Workspace")
  }

  def isEnrolled(siteName: String) : Boolean = {
    switch to defaultContent
    // if we are already on the right site, skip this
    if (!className("siteTitle").element.text.contains(siteName)) {
      //If site is listed return true, if not click on "More Sites"
      if (webDriver.findElements(By.partialLinkText(siteName)).size() != 0) {
        return true
      } else {
        click on cssSelector("a[title='More Sites']")
        textField("txtSearch").value = siteName
        if (webDriver.findElements(By.partialLinkText(siteName)).size() != 0) {
          return true
        } else {
          return false
        }
      }
    } else {
      return true
    }
  }

  def gotoSiteDirectly(siteId : String) {
    go to Config.targetServer + "/site/" + siteId
  }

  def gotoSite(siteName : String) {
    switch to defaultContent

    // if we are already on the right site, skip this
    if (!className("siteTitle").element.text.contains(siteName)) {
      //If site is listed, click on site, if not click on "More Sites"
      if (webDriver.findElements(By.partialLinkText(siteName)).size() != 0) {
        click on partialLinkText(siteName)
      } else {
        click on cssSelector("a[title='More Sites']")
        textField("txtSearch").value = siteName
        click on partialLinkText(siteName)
      }
    }
  }

  def gotoSite(siteName : String, siteType : String) {
    switch to defaultContent

    // if we are already on the right site, skip this
    click on linkText("Home")
    if (!className("siteTitle").element.text.contains(siteName)) {
      //If site is listed, click on site, if not click on "More Sites"
      if (webDriver.findElements(By.partialLinkText(siteName)).size() != 0) {
        click on partialLinkText(siteName)
      } else {
        click on cssSelector("a[title='More Sites']")
        textField("txtSearch").value = siteName
        //If site is found go to site, if not create the site
        if (webDriver.findElements(By.partialLinkText(siteName)).size() != 0) {
          click on partialLinkText(siteName)
        } else {
          click on cssSelector("a[title='Close this drawer']")
          if (!isMyWorkspace()) {
            click on cssSelector("a[title='My Workspace']")
          }
          createSite(siteType)
        }
      }
    }
  }

  def createSite(siteType: String) {
    gotoTool("Site Setup")
    var siteTitle = SiteManageTool.createRandomSite(siteType)
    gotoSite(siteTitle)
  }


  def gotoTool(toolName : String) {
    gotoTool(toolName, false)
  }


  def gotoTool(toolName : String, reset : Boolean) {
    switch to defaultContent
    if (toolName.equals("Site Setup") && !Config.sakaiDistro.equalsIgnoreCase("ani"))  {
      if (linkText("Worksite Setup").findElement(webDriver).isDefined) {
        click on linkText("Worksite Setup")
      } else if (linkText("Site Info").findElement(webDriver).isDefined){
        click on linkText("Site Info")
      }
    } else {
      click on linkText(toolName)
    }

    if (reset)  {
      click on xpath("//a[contains(@title,'Reset')]")
    }

    eventually (switch to frame(0))
  }

  def richTextEditor() {
    switch to frame(xpath("//iframe[contains(@title,'Rich text editor')]"))
    webDriver.switchTo().activeElement().sendKeys(faker.paragraph(4))
    webDriver.switchTo().defaultContent()
    switch to frame(0)
  }

  def logout() {
    try {
      switch to defaultContent
      val logoutLink = webDriver.findElement(By.linkText("Logout"));
      if (logoutLink != null && logoutLink.isDisplayed) {
        click on linkText("Logout")
      }
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => {
        //e.printStackTrace()
      }
    }
  }
}
