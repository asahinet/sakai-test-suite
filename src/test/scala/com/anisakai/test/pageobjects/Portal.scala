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

  def getToFrameZero {
    switch to defaultContent
    switch to frame(0)
  }

  def xslFrameOne {
    if (Config.defaultPortal == "xsl")  {
      if (Config.client == "learnersedge") {
        getToFrameZero
      } else {
        switch to defaultContent
        switch to frame(1)
      }
    } else {
      getToFrameZero
    }
  }

  def login() {
    submit()
  }

  def login(eid : String, password : String) {
    go to Config.targetServer

    logout()

    enterEid(eid)
    enterPassword(password)
    submit
  }

  def enterEid(eid: String) {
    textField("eid").value = eid
  }

  def enterPassword(password: String) {
    pwdField("pw").value = password
  }

  def isMyWorkspace() : Boolean = {
    if (Config.defaultPortal == "xsl") {
        return webDriver.findElement(By.id("siteTitle")).getText().startsWith("My Workspace")
    } else {
      return webDriver.findElement(By.className("siteTitle")).getText().startsWith("My Workspace")
    }
  }

  def isAdminWorkspace() : Boolean = {
    if (Config.defaultPortal == "xsl") {
      return webDriver.findElement(By.id("siteTitle")).getText().startsWith("Administration Workspace")
    } else {
      return webDriver.findElement(By.className("siteTitle")).getText().startsWith("Administration Workspace")
    }
  }

  def isEnrolled(siteName: String) : Boolean = {
    switch to defaultContent
    var siteTitle = webDriver.findElement(By.xpath("//*"))
    if (Config.defaultPortal == "xsl") {
      siteTitle = webDriver.findElement(By.id("siteTitle"))
    } else {
      siteTitle = webDriver.findElement(By.className("siteTitle"))
    }
    // if we are already on the right site, skip this
    if (!siteTitle.getText.contains(siteName)) {
      //If site is listed return true, if not click on "More Sites"
      if (partialLinkText(siteName).findElement(webDriver).isDefined) {
        return true
      } else {
        click on cssSelector("a[title='More Sites']")
        if (Config.defaultPortal == "neo")
          textField("txtSearch").value = siteName
        if (partialLinkText(siteName).findElement(webDriver).isDefined) {
          return true
        } else {
          return false
        }
      }
    } else {
      return true
    }
  }

  def gotoAdminWorkspace () {
    switch to defaultContent
    click on linkText("Administration Workspace")
  }

  def gotoSiteDirectly(siteId : String) {
    var server = Config.targetServer
    if (server.contains("/xlogin")){
      server = server.dropRight(7)
    }
    if (server.contains("/xsl-portal")) {
      server = server.dropRight(11)
    }
    if (Config.defaultPortal == "neo") {
      go to server + "/site/" + siteId
    } else {
      go to server + "/xsl-portal/site/" + siteId
    }
  }

  def gotoSite(siteName : String) {
    switch to defaultContent
    var siteTitle = webDriver.findElement(By.xpath("//*"))
    if (Config.defaultPortal == "xsl") {
      siteTitle = webDriver.findElement(By.id("siteTitle"))
    } else {
      siteTitle = webDriver.findElement(By.className("title"))
    }
    // if we are already on the right site, skip this
    if (!siteTitle.getText.contains(siteName)) {
      //If site is listed, click on site, if not click on "More Sites"
      if (partialLinkText(siteName).findElement(webDriver).isDefined) {
        click on partialLinkText(siteName)
      } else {
        click on cssSelector("a[title='More Sites']")
        if (Config.defaultPortal == "neo")
          textField("txtSearch").value = siteName
        click on partialLinkText(siteName)
      }
    }
  }

  def gotoSite(siteName : String, siteType : String) {
    switch to defaultContent

    // if we are already on the right site, skip this
    if (Config.defaultPortal == "neo")
      click on linkText("Home")

    if (!webDriver.findElement(By.id("siteTitle")).getText.contains(siteName)) {
          //If site is listed, click on site, if not click on "More Sites"
      if (partialLinkText(siteName).findElement(webDriver).isDefined) {
        click on partialLinkText(siteName)
      } else {
        click on cssSelector("a[title='More Sites']")
        if (Config.defaultPortal == "neo")
          textField("txtSearch").value = siteName
        //If site is found go to site, if not create the site
        if (partialLinkText(siteName).findElement(webDriver).isDefined) {
          click on partialLinkText(siteName)
        } else {
          if (Config.defaultPortal == "neo") {
            click on cssSelector("a[title='Close this drawer']")
            if (!isMyWorkspace()) {
              click on cssSelector("a[title='My Workspace']")
            }
          } else {
            click on cssSelector("a[title='More Sites']")
            if (!isAdminWorkspace()) {
              click on cssSelector("a[title='Administration Workspace']")
            }
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
      if (webDriver.findElement(By.className("title")).getText == toolName) {
        click on xpath("//a[contains(@title,'Reset')]")
      } else {
        click on linkText(toolName)
      }
    }

    if (reset)  {
      switch to defaultContent
      click on xpath("//a[contains(@title,'Reset')]")
    }

  }

  def richTextEditor(): String = {
    val text = faker.paragraph(2)
    switch to frame(xpath("//iframe[contains(@title,'Rich text editor')]"))
    webDriver.switchTo.activeElement.sendKeys(text)
    webDriver.switchTo.defaultContent
    getToFrameZero
    return text
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
