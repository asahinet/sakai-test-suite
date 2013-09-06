package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._
import com.anisakai.test.pageobjects.{Google, Page, Portal}
/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 8/30/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
class GoogleSearch extends ScalaDsl with EN {
  lazy val page = new Google()

  Given( """^I am on the '(.+)' page$""") {  (url: String) =>
    page.navigateToPage(url)
  }
  When("""^I enter '(.+)' for search term$"""){ (text: String) =>
    page.enterText(text)
  }
  When("""^I click the searching button"""){ () =>
    page.searching()
  }
  When("""^I click the Books link""") { () =>
    page.clickBooks()
  }
  Then("""^I should see the Amazon Books page"""){ () =>
    assertTrue(page.gotResults())
  }

}
