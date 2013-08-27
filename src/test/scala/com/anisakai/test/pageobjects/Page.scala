package com.anisakai.test.pageobjects

import org.scalatest.selenium._
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.{WebDriver}
import org.apache.commons.lang.StringUtils
import org.openqa.selenium.phantomjs.{PhantomJSDriver, PhantomJSDriverService}
import org.openqa.selenium.remote.DesiredCapabilities
import java.util.concurrent.TimeUnit

/**
 * User: tris
 * Date: 05/12/12
 */

object PhantomJSDriverObject {
  def apply(capabilities: DesiredCapabilities) = new PhantomJSDriver(capabilities)
}

abstract class Page extends WebBrowser {
  val systemProperties = System.getProperties()
  var host: String = {
    if (!StringUtils.isEmpty(systemProperties.getProperty("target.server"))) {
      systemProperties.getProperty("target.server")
    } else {
      "https://nightly.cle.rsmart.com/portal"
    }
  }


  implicit val webDriver: WebDriver = {
    var selectedDriver: WebDriver = null

    if (!StringUtils.isEmpty(systemProperties.getProperty("target.browser"))) {
      var targetBrowser = systemProperties.getProperty("target.browser")
      if (targetBrowser.equalsIgnoreCase("chrome")) {
        if (StringUtils.isEmpty(systemProperties.getProperty("webdriver.chrome.driver"))) {
          systemProperties.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver_mac")
        }
        selectedDriver = Chrome.webDriver
      } else if (targetBrowser.equalsIgnoreCase("ie") || targetBrowser.equalsIgnoreCase("internetexplorer")) {
        selectedDriver = InternetExplorer.webDriver
      } else if (targetBrowser.equalsIgnoreCase("safari")) {
        selectedDriver = Safari.webDriver
      } else if (targetBrowser.equalsIgnoreCase("htmlunit")) {
        selectedDriver = HtmlUnit.webDriver
      } else if (targetBrowser.equalsIgnoreCase("phantomjs")) {

        if (StringUtils.isEmpty(systemProperties.getProperty("webdriver.phantomjs.binary"))) {
          systemProperties.setProperty("webdriver.phantomjs.binary", "phantomjs/phantomjs-1.9.1-macosx")
        }

        val capabilities = new DesiredCapabilities
        capabilities.setJavascriptEnabled(true)
        capabilities.setCapability("takesScreenshot", false)
        capabilities.setCapability(
          PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
          systemProperties.getProperty("webdriver.phantomjs.binary"))
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Array("--ignore-ssl-errors=yes", "--web-security=false", "--ssl-protocol=any"));

        selectedDriver = PhantomJSDriverObject(capabilities)

      }

    }

    if (selectedDriver == null) {
      selectedDriver = Firefox.webDriver
    }
    selectedDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    selectedDriver
  }

  def navigateToPage(url: String) {
    go to url

  }


}
