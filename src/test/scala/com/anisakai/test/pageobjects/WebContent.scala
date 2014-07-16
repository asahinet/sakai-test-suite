package com.anisakai.test.pageobjects

import org.openqa.selenium.By
import com.anisakai.test.Config

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/30/13
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
object WebContent extends WebContent

class WebContent extends Page {

  def addWebContent() : String = {
    switch to defaultContent
    if (Config.defaultPortal == "xsl")
      Portal.getToFrameZero
    click on xpath("//a[@title='Edit']")
    switch to defaultContent
    textField("title-of-page").value = "Web Content"
    textField("source").value = Config.targetServer
    click on name("sakai.update")
    return "https://nightly.cle.rsmart.com/portal/"
  }

  def isDisplayed(webPage: String) : Boolean = {
    switch to defaultContent
    return xpath("//iframe[@src='"+webPage+"']").findElement(webDriver).isDefined
  }
}
