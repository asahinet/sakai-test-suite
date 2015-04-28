package com.anisakai.test.cucumber.stepdefs


import com.anisakai.test.pageobjects.Forums
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

class ForumsTest extends ScalaDsl with EN with TearDown {

  var forum: String = ""
  var topic: String = ""
  var conversation: String = ""
  var reply: String = ""
  var newReply: String = ""

  When( """^I create a new '(.+)' as an? '(.+)'$""") { (theType: String, role: String) =>
    theType.toLowerCase match {
      case "forum" => forum = Forums.createForum
      case "topic" => topic = Forums.createTopic
      case "conversation" => role.toLowerCase match {
        case "student" => conversation = Forums.createConversation(true)
        case _ => conversation = Forums.createConversation()
      }
    }
  }

  Then( """^The '(.+)' is added to the list$""") { (theType: String) =>
    theType.toLowerCase match {
      case "forum" => assertTrue(Forums.isAdded(theType, forum))
      case "topic" => assertTrue(Forums.isAdded(theType, topic))
      case "conversation" => assertTrue(Forums.isAdded(theType, conversation))
      case "reply" => assertTrue(Forums.isAdded(theType, reply.substring(0, 10)))
    }
  }

  When( """^I reply to a message$""") { () =>
    reply = Forums.createReply
  }

  When( """^I '(.+)' an existing message$""") { (editOrDel: String) =>
    if (editOrDel == "edit") {
      newReply = Forums.editMessage
    } else {
      Forums.delMessage
    }
  }

  Then( """^The message is changed$""") { () =>
    assertTrue(Forums.isAdded("reply", newReply.substring(0, 10)))
  }

  Then( """^The message is deleted$""") { () =>
    assertFalse(Forums.isAdded("reply", newReply.substring(0, 10)))
    Forums.cleanup
  }

}
