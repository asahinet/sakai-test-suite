package com.anisakai.test.pageobjects

import org.scalatest.selenium._
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import com.anisakai.test.Config
import com.github.javafaker.Faker
import org.scalatest.concurrent.Eventually
import java.io.{IOException, File}
import org.apache.commons.io.FileUtils

import cucumber.api.Scenario
import java.util.Date
import cucumber.api.java.After

/**
 *
 */
abstract class Page extends WebBrowser with Eventually {
  val faker: Faker = new Faker()

  implicit val webDriver: WebDriver = Config.webDriver

  def navigateToPage(url: String) {
    go to url
  }



}
