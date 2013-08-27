package com.anisakai.test.pageobjects

import org.scalatest.selenium._
import org.openqa.selenium.{WebDriver}
import com.anisakai.test.Config
import com.github.javafaker.Faker

/**
 *
 */
abstract class Page extends WebBrowser {
  val faker: Faker = new Faker()

  implicit val webDriver: WebDriver = Config.webDriver

  def navigateToPage(url: String) {
    go to url
  }
}
