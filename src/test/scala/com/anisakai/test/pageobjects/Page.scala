package com.anisakai.test.pageobjects

import org.scalatest.selenium._
import org.openqa.selenium.{WebDriver}
import com.anisakai.test.Config

/**
 * User: tris
 * Date: 05/12/12
 */



abstract class Page extends WebBrowser {

  implicit val webDriver: WebDriver = Config.webDriver

  def navigateToPage(url: String) {
    go to url
  }
}
