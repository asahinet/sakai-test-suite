package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects.MessagesCRUD
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/23/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
class MessagesCRUDTest extends ScalaDsl with EN with TearDown {
  var messageSub: String = ""

  When( """^I add a message to messages$""") { () =>
    messageSub = MessagesCRUD.addMessage()
  }

  Then( """^The created message should be visible in the list$""") { () =>
    assertTrue(MessagesCRUD.isAdded(messageSub))
  }

  When( """^I add a message with an attachment to messages$""") { () =>
    messageSub = MessagesCRUD.addMessage(true)
  }

}
