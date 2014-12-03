package com.anisakai.test.pageobjects

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

  def addWebContent(): String = {
    switch to defaultContent
    //Portal.xslFrameOne
    click on xpath("//a[@title='Edit']")
    switch to defaultContent
//    if (Config.client == "sgu" || Config.client == "davis") {
//      switch to frame(0)
//    }
    textField("title-of-page").value = "Web Content"
    textField("source").value = Config.targetServer
    click on name("sakai.update")

    return Config.targetServer
  }

  def isDisplayed(webPage: String): Boolean = {
    //Portal.xslFrameOne
    return xpath("//iframe[@src='" + webPage + "']").findElement(webDriver).isDefined
  }
}
