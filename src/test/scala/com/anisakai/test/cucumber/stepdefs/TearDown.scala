package com.anisakai.test.cucumber.stepdefs

import cucumber.api.java.{Before, After}
import cucumber.api.Scenario
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import com.anisakai.test.Config
import com.anisakai.test.pageobjects.Portal

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 9/3/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
trait TearDown {

  @After
  def tearDown(result: Scenario) {
    if (result.isFailed) {
      if (webDriver.isInstanceOf[TakesScreenshot]) {
        val screenshot = webDriver.asInstanceOf[TakesScreenshot].getScreenshotAs(OutputType.BYTES);
        try {
          result.embed(screenshot, "image/png")
        } catch {
          case e: Exception => e.printStackTrace()
        }
      }
    }
  }

  val webDriver: WebDriver = Config.webDriver

}
