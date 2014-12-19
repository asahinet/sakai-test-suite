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
    if (theType.equalsIgnoreCase("forum")) {
      forum = Forums.createForum
    }
    else if (theType.equalsIgnoreCase("topic")) {
      topic = Forums.createTopic
    }
    else if (theType.equalsIgnoreCase("conversation")) {
      if (role.equalsIgnoreCase("student")) conversation = Forums.createConversation(true)
      else conversation = Forums.createConversation
    }
  }

  Then( """^The '(.+)' is added to the list$""") { (theType: String) =>
    if (theType.equalsIgnoreCase("forum")) {
      assertTrue(Forums.isAdded(theType, forum))
    }
    else if (theType.equalsIgnoreCase("topic")) {
      assertTrue(Forums.isAdded(theType, topic))
    }
    else if (theType.equalsIgnoreCase("conversation")) {
      assertTrue(Forums.isAdded(theType, conversation))
    }
    else if (theType.equalsIgnoreCase("reply")) {
      assertTrue(Forums.isAdded(theType, reply.substring(0, 10)))
    }
  }

  When( """^I reply to a message$""") { () =>
    reply = Forums.createReply
  }

  When( """^I '(.+)' an existing message$""") { (editOrDel: String) =>
    if (editOrDel == "edit") {
      newReply = Forums.editMessage
    }
    else {
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
