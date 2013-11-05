package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects.{messages_CRUD, TurnItIn, AssignmentTool, Portal}
import junit.framework.Assert._
/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/23/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
class messages_CRUD_Test extends ScalaDsl with EN with TearDown {
  var messageSub: String = ""

  When("""^I add a message to messages$""") { () =>
    messageSub = messages_CRUD.addMessage()
  }

  Then("""^The created message should be visible in the list$""") { () =>
    assertTrue(messages_CRUD.isAdded(messageSub))
  }

  When("""^I add a message with an attachment to messages$""") { () =>
    messageSub = messages_CRUD.addMessage(true)
  }

}
