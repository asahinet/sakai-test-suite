package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects._
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 11/4/13
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */

class GoogleDocsTest extends ScalaDsl with EN with TearDown {

  var doc = ""

  When( """^I add a google doc with '(.+)' dates$""") { (settings: String) =>
    if (settings.equalsIgnoreCase("default")) doc = GoogleDocs.addDoc("default")
    else if (settings.equalsIgnoreCase("past")) doc = GoogleDocs.addDoc("past")
    else if (settings.equalsIgnoreCase("future")) doc = GoogleDocs.addDoc("future")
  }

  Then( """^the resource '(.+)' be visible$""") { (settings: String) =>
    assertTrue(GoogleDocs.docAdded(doc, settings))
  }

  When( """^I remove all google documents$""") { () =>
    GoogleDocs.cleanup
  }

}
