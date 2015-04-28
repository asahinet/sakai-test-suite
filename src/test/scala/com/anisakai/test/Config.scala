package com.anisakai.test

import org.apache.commons.lang.StringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.scalatest.selenium._
import org.openqa.selenium.remote.{DesiredCapabilities}
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
  val systemProperties = System.getProperties
  val targetServer: String = loadProperty("target.server", "https://nightly.cle.rsmart.com/portal")
  val sakaiVersion: String = loadProperty("sakai.version", "2.9.1")
  val sakaiDistro: String = loadProperty("sakai.distro", "ani")
  val defaultAdminEid: String = loadProperty("sakai.admin.eid", "admin")
  val defaultAdminPassword: String = loadProperty("sakai.admin.pwd", "admin")
  val defaultGoogleEmail: String = loadProperty("google.email", "default@gmail.com")
  val defaultGooglePW: String = loadProperty("google.pw", "password")
  val randomUserEmail: String = loadProperty("random.user.email", "goes@nowhere.com")
  val skin: String = loadProperty("sakai.skin", "neo")
  val client: String = loadProperty("sakai.client", "nightly")
  val timeout: String = loadProperty("driver.timeout", "5")

  val isTen: Boolean = sakaiVersion.startsWith("10.")

  def defaultCourseSiteId = "course-test-1"
  def defaultCourseSiteTitle = "Course Site Test 1"
  def defaultInstructorEid = "instructor1"
  def defaultStudentEid = "student01"
  def defaultInstructorPassword = "password"
  def defaultStudentPassword = "password"

  def loadProperty(name: String, defaultValue: String) = {
    if (!StringUtils.isEmpty(systemProperties.getProperty(name))) {
      systemProperties.getProperty(name)
    } else {
      defaultValue
    }
  }

  val webDriver: WebDriver = {
    var selectedDriver: WebDriver = null
    if (!StringUtils.isEmpty(systemProperties.getProperty("target.browser"))) {
      val targetBrowser = systemProperties.getProperty("target.browser")
      targetBrowser.toLowerCase match {
        case "chrome" =>
          if (StringUtils.isEmpty(systemProperties.getProperty("webdriver.chrome.driver"))) {
            systemProperties.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver-2.6_mac")
          }
          val capabilities = new DesiredCapabilities
          val options = new ChromeOptions
          options.addArguments("test-type")
          capabilities.setCapability("chrome.binary", "chromedriver/chromedriver-2.6_mac")
          capabilities.setCapability(ChromeOptions.CAPABILITY, options)
          selectedDriver = new ChromeDriver(capabilities)
        case "ie" | "internetexplorer" => selectedDriver = InternetExplorer.webDriver
        case "safari" => selectedDriver = Safari.webDriver
        case "htmlunit" => selectedDriver = HtmlUnit.webDriver
        case "firefox" => selectedDriver = Firefox.webDriver
        case "phantomjs" =>
          if (StringUtils.isEmpty(systemProperties.getProperty("webdriver.phantomjs.binary"))) {
            systemProperties.setProperty("webdriver.phantomjs.binary", "phantomjs/phantomjs-1.9.1-macosx")
          }
          val capabilities = new DesiredCapabilities
          capabilities.setJavascriptEnabled(true)
          capabilities.setCapability("takesScreenshot", false)
          capabilities.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            systemProperties.getProperty("webdriver.phantomjs.binary"))
          capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Array("--ignore-ssl-errors=yes", "--web-security=false", "--ssl-protocol=any"))
          selectedDriver = PhantomJSDriverObject(capabilities)
        case _ => selectedDriver = Firefox.webDriver

      }
    } else {
      Firefox.firefoxProfile.setAcceptUntrustedCertificates(true)
      selectedDriver = Firefox.webDriver
    }
    selectedDriver.manage.timeouts.implicitlyWait(timeout.toInt, TimeUnit.SECONDS)
    selectedDriver
  }

}
