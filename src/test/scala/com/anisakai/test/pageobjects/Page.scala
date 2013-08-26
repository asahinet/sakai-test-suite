package com.anisakai.test.pageobjects

import org.scalatest.selenium._
import org.openqa.selenium.WebDriver
import org.apache.commons.lang.StringUtils

/**
 * User: tris
 * Date: 05/12/12
 */
abstract class Page extends WebBrowser {
  val systemProperties = System.getProperties()
  var host : String = {
    if (!StringUtils.isEmpty(systemProperties.getProperty("target.server"))) {
      systemProperties.getProperty("target.server")
    } else {
       "https://nightly.cle.rsmart.com/portal"
    }
  }

  implicit val webDriver: WebDriver = {

    if (!StringUtils.isEmpty(systemProperties.getProperty("target.browser"))) {
      var targetBrowser = systemProperties.getProperty("target.browser")
      if (targetBrowser.equalsIgnoreCase("chrome")) {
        if (StringUtils.isEmpty(systemProperties.getProperty("webdriver.chrome.driver"))){
          systemProperties.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver_mac")
        }
        Chrome.webDriver
      } else if (targetBrowser.equalsIgnoreCase("ie") || targetBrowser.equalsIgnoreCase("internetexplorer")) {
        InternetExplorer.webDriver
      } else if (targetBrowser.equalsIgnoreCase("safari")) {
        Safari.webDriver
      } else if (targetBrowser.equalsIgnoreCase("htmlunit")) {
        HtmlUnit.webDriver
      } else {
        Firefox.webDriver
      }
    } else {
      Firefox.webDriver
    }

 }

  def navigateToPage(url: String) {
    go to url

  }


}
