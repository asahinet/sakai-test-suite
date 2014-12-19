package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.openqa.selenium.{UnhandledAlertException, By}
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatest.concurrent.Eventually

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
object Portal extends Portal

class Portal extends Page with Eventually {
  //  def eid: TextField = textField("eid")
  //  def password: PasswordField = pwdField("pw")

  def getToFrameZero {
    switch to defaultContent
    switch to frame(0)
  }

  def xslFrameOne {
    try {
      if (Config.skin == "xsl") {
        if (Config.client == "learnersedge" || Config.client == "sgu") {
          getToFrameZero
        } else {
          switch to defaultContent
          switch to frame(1)
        }
      } else {
        getToFrameZero
      }
    } catch {
      case ae: UnhandledAlertException => println("Unhandled Alert Exception is normal, test can still pass")
    } finally {
      getToFrameZero
    }

  }

  def login {
    submit
  }

  def login(eid: String, password: String) {
    go to Config.targetServer
    logout
    enterEid(eid)
    enterPassword(password)
    login
  }

  def enterEid(eid: String) = textField("eid").value = eid

  def enterPassword(password: String) = pwdField("pw").value = password

  def isMyWorkspace: Boolean = {
    if (Config.skin == "xsl") {
      webDriver.findElement(By.id("siteTitle")).getText.startsWith("My Workspace")
    } else {
      webDriver.findElement(By.className("siteTitle")).getText.startsWith("My Workspace")
    }
  }

  def isAdminWorkspace: Boolean = {
    if (Config.skin == "xsl") {
      webDriver.findElement(By.id("siteTitle")).getText.startsWith("Administration Workspace")
    } else {
      webDriver.findElement(By.className("siteTitle")).getText.startsWith("Administration Workspace")
    }
  }

  def isEnrolled(siteName: String): Boolean = {
    switch to defaultContent
    var siteTitle = webDriver.findElement(By.xpath("//*"))
    if (Config.skin == "xsl") {
      siteTitle = webDriver.findElement(By.id("siteTitle"))
    } else {
      siteTitle = webDriver.findElement(By.className("siteTitle"))
    }
    // if we are already on the right site, skip this
    if (!siteTitle.getText.contains(siteName)) {
      //If site is listed return true, if not click on "More Sites"
      if (partialLinkText(siteName).findElement(webDriver).isDefined) {
        true
      } else {
        click on cssSelector("a[title='More Sites']")
        if (Config.skin == "neo")
          textField("txtSearch").value = siteName
        if (partialLinkText(siteName).findElement(webDriver).isDefined) {
          true
        } else {
          false
        }
      }
    } else {
      true
    }
  }

  def goToAdminWorkspace {
    switch to defaultContent
    click on linkText("Administration Workspace")
  }

  def goToSiteDirectly(siteId: String) {

    var server = Config.targetServer

    // remove excess content of the URL
    if (server.contains("/xlogin")) {
      server = server.dropRight(7)
    }
    if (server.contains("/xsl-portal")) {
      server = server.dropRight(11)
    }
    if (Config.skin == "neo") {
      go to server + "/site/" + siteId
    } else {
      go to server + "/xsl-portal/site/" + siteId
    }
  }

  def goToSite(siteName: String) {
    switch to defaultContent
    val siteTitle = webDriver.findElement(By.xpath("//title")).getText
    // if we are already on the right site, skip this
    if (!siteTitle.contains(siteName)) {
      //If site is listed, click on site, if not click on "More Sites"
      if (partialLinkText(siteName).findElement(webDriver).isDefined) {
        click on partialLinkText(siteName)
      } else {
        click on xpath("//a[@title='More Sites']")
        if (Config.skin == "neo")
          textField("txtSearch").value = siteName
        click on partialLinkText(siteName)
      }
    }
  }

  def goToSite(siteName: String, siteType: String) {
    switch to defaultContent

    // if we are already on the right site, skip this
    if (Config.skin == "neo")
      click on linkText("Home")

    if (!webDriver.findElement(By.id("siteTitle")).getText.contains(siteName)) {
      //If site is listed, click on site, if not click on "More Sites"
      if (partialLinkText(siteName).findElement(webDriver).isDefined) {
        click on partialLinkText(siteName)
      } else {
        click on cssSelector("a[title='More Sites']")
        if (Config.skin == "neo")
          textField("txtSearch").value = siteName
        //If site is found go to site, if not create the site
        if (partialLinkText(siteName).findElement(webDriver).isDefined) {
          click on partialLinkText(siteName)
        } else {
          if (Config.skin == "neo") {
            click on cssSelector("a[title='Close this drawer']")
            if (!isMyWorkspace) {
              click on cssSelector("a[title='My Workspace']")
            }
          } else {
            click on cssSelector("a[title='More Sites']")
            if (!isAdminWorkspace) {
              click on cssSelector("a[title='Administration Workspace']")
            }
          }
          createSite(siteType)
        }
      }
    }
  }

  def createSite(siteType: String) {
    goToTool("Site Setup")
    goToSite(SiteManageTool.createRandomSite(siteType))
  }

  def goToTool(toolName: String, reset: Boolean = false) {
    // reset will refresh the tool before using it to ensure clean tool
    switch to defaultContent
    if (toolName.equals("Site Setup") || toolName.equals("Site Editor") && !Config.sakaiDistro.equalsIgnoreCase("ani")) {
//      Different names for the same thing
      if (linkText("Worksite Setup").findElement(webDriver).isDefined) {
        click on linkText("Worksite Setup")
      } else if (linkText("Site Info").findElement(webDriver).isDefined) {
        click on linkText("Site Info")
      }
    } else {
      //      If we are on the correct tool reset it, otherwise go to the tool
      //      XSL Portal uses element id instead of className

      if (webDriver.findElement(By.className("title")).getText.contains(toolName)) {
        click on xpath("//a[contains(@title,'Reset')]")
      } else {
        // Different names for the same thing
        if (toolName.equalsIgnoreCase("Lessons Builder")) {
          if (linkText("Lesson Builder").findElement(webDriver).isDefined) {
            click on linkText("Lesson Builder")
          } else if (linkText("Lessons").findElement(webDriver).isDefined) {
            click on linkText("Lessons")
          } else {
            click on linkText(toolName)
          }
        } else if (toolName.equalsIgnoreCase("Forums")) {
          if (linkText("Discussions").findElement(webDriver).isDefined) {
            click on linkText("Discussions")
          } else {
            click on linkText(toolName)
          }
        } else if (toolName.equalsIgnoreCase("Messages")) {
          if (linkText("Course Email").findElement(webDriver).isDefined) {
            click on linkText("Course Email")
          } else {
            click on linkText(toolName)
          }
        } else {
          click on linkText(toolName)
        }
        if (reset) {
          click on xpath("//a[contains(@title,'Reset')]")
        }
      }
    }
  }

  def richTextEditor: String = {
//    Used to insert text into any CKEditor
    def wait = new WebDriverWait(webDriver, 10)
    val text = faker.paragraph(2)
    var frameName = ""
    if (Config.sakaiVersion.startsWith("10.")) {
      frameName = "'Rich Text Editor'"
    } else {
      frameName = "'Rich text editor'"
    }
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@title, " + frameName + " )]")))
    switch to frame(xpath("//iframe[contains(@title," + frameName + ")]"))
    webDriver.switchTo.activeElement.sendKeys(text)
    webDriver.switchTo.defaultContent
    getToFrameZero
    text
  }

  def logout {
    try {
      switch to defaultContent
      val logoutLink = webDriver.findElement(By.linkText("Logout"))
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
