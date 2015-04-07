package com.anisakai.test.pageobjects

import org.openqa.selenium.By

object Forums extends Forums

class Forums extends Page {

  //Setting posting names
  var forum = faker.lorem.sentence(2)
  var topic = faker.lorem.sentence(2)
  var conversation = faker.lorem.sentence(2)
  var studConv = faker.lorem.sentence(2)
  var reply = ""
  var newReply = ""

  def createForum: String = {
    Portal.xslFrameOne
    click on linkText("New Forum")
    textField("revise:forum_title").value = forum
    click on xpath("//input[@value='Save']")
    forum
  }

  def createTopic: String = {
    Portal.xslFrameOne
    click on xpath("//a[contains(@title, '" + forum + "')]/following-sibling::a[@title='New Topic']")
    textField("revise:topic_title").value = topic
    click on xpath("//input[@value='Save']")
    topic
  }

  def createConversation(isStud: Boolean) = {
    var text = ""
    if (isStud) text = studConv
    else text = conversation
    reset
    Portal.xslFrameOne
    click on xpath("//a[.='" + topic + "']")
    click on linkText("Start a New Conversation")
    textField("dfCompose:df_compose_title").value = text
    click on xpath("//input[@value='Post']")
    text
  }

  def createConversation: String = createConversation(false)


  def createReply: String = {
    reset
    Portal.xslFrameOne
    click on xpath("//a[contains(@title,'" + topic + "')]")
    click on linkText(studConv)
    click on linkText("Reply to Initial Message")
    reply = Portal.richTextEditor
    Portal.xslFrameOne
    click on xpath("//input[@value='Post']")
    reply
  }

  def isAdded(theType: String, text: String): Boolean = {
    reset
    Portal.xslFrameOne
    if (theType == "conversation" || theType == "reply") {
      click on xpath("//a[contains(text(),'" + topic + "')]")
      if (theType == "reply") click on linkText(studConv)
    }
    xpath("//*[contains(text(),'" + text + "')]").findElement(webDriver).isDefined
  }

  def editMessage: String = {
    reset
    Portal.xslFrameOne
    click on xpath("//a[contains(@title, '" + topic + "')]")
    click on linkText(studConv)
    click on xpath("//p[contains(text(),'" + reply.substring(0, 10) + "')]/..//preceding-sibling::*//a[.='Edit']")
    newReply = Portal.richTextEditor
    click on xpath("//input[@value='Post Edited Message']")
    reset
    newReply
  }

  def reset {
    switch to defaultContent
    click on xpath("//a[contains(@title,'Reset')]")
  }

  def delMessage {
    reset
    Portal.xslFrameOne
    click on xpath("//a[contains(@title, '" + topic + "')]")
    click on linkText(studConv)
    click on xpath("//p[contains(text(),'" + reply.substring(0, 10) + "')]/..//preceding-sibling::*//a[.='Delete Message']")
    click on xpath("//input[@value='Delete']")
    reset
  }

  def cleanup {
    reset
    Portal.xslFrameOne
    while (webDriver.findElements(By.linkText("More")).size != 0) {
      Portal.xslFrameOne
      click on webDriver.findElements(By.linkText("More")).get(0)
      click on linkText("Delete Forum")
      click on id("revise:delete")
    }
  }


}
