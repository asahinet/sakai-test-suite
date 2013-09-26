package com.anisakai.test

import org.apache.commons.lang.StringUtils
import org.openqa.selenium.WebDriver
import org.scalatest.selenium._
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import java.util.concurrent.TimeUnit
import com.anisakai.PhantomJSDriverObject

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/26/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */

object Config extends Config

class Config {
  val systemProperties = System.getProperties()
  val targetServer: String = loadProperty ("target.server","https://nightly.cle.rsmart.com/portal")
  val sakaiVersion : String = loadProperty("sakai.version", "2.9.1")
  val sakaiDistro : String = loadProperty("sakai.distro", "ani")

  def loadProperty (name : String, defaultValue : String) = {
    if (!StringUtils.isEmpty(systemProperties.getProperty(name))) {
      systemProperties.getProperty(name)
    } else {
      defaultValue
    }
  }

  val webDriver: WebDriver = {
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

      //TODO probably should make the timeout configurable
      selectedDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
      selectedDriver
    }

}
