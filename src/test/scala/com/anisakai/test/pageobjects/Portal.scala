package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.apache.commons.lang.WordUtils
import org.openqa.selenium.{UnhandledAlertException, By, NoSuchElementException}
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
        if (Config.client == "learnersedge" || Config.client == "sgu" || Config.client == "ucsc-ext") {
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
        click on xpath("//a[@title='More Sites']")
        if (Config.skin == "neo")
          textField("txtSearch").value = siteName
        partialLinkText(siteName).findElement(webDriver).isDefined
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
    if (toolName.equals("Site Setup") || toolName.equals("Site Editor")) {
      if (webDriver.findElement(By.className("title")).getText.contains(toolName)) {
        click on xpath("//a[contains(@title,'Reset')]")
      } else {
        click on xpath("//*[.='Site Setup'] | //*[.='Site Editor'] | //*[.='Site Info'] | //*[.='Worksite Setup']")
        if (reset) {
          click on xpath("//a[contains(@title,'Reset')]")
        }
      }

    } else {
      //      If we are on the correct tool reset it, otherwise go to the tool
      if (webDriver.findElement(By.className("title")).getText.contains(toolName)) {
        click on xpath("//a[contains(@title,'Reset')]")
      } else {
        // Different names for the same thing
        toolName.toLowerCase match {
          case lessons if lessons == "lessons builder" || lessons == "lessons" || lessons == "lessonbuilder" => click on linkText(WordUtils.capitalizeFully(lessons))
          case forums if forums == "forums" || forums == "discussions" => click on linkText(WordUtils.capitalizeFully(forums))
          case messages if messages == "messages" || messages == "course email" => click on linkText(WordUtils.capitalizeFully(messages))
          case _ => click on linkText(WordUtils.capitalizeFully(toolName))
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
    val text = faker.lorem.paragraph(2)
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
      case e: NoSuchElementException => {
        //e.printStackTrace()
      }
    }
  }
}
