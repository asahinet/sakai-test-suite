package com.anisakai.test.pageobjects

import org.scalatest.selenium.{Firefox, Chrome}

/**
 * User: tris
 * Date: 05/12/12
 */
abstract class Page extends Firefox{
  var host : String = "https://nightly.cle.rsmart.com/portal"

  def navigateToPage(url: String) {
    go to url
  }
}
