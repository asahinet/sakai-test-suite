package com.anisakai.test.pageobjects

import scala.collection.mutable.ListBuffer
import org.openqa.selenium.By

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 11/5/13
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
object LessonBuilder extends LessonBuilder

class LessonBuilder extends Page {

  def addText(): String = {
    click on linkText("Add Text")
    val text = Portal.richTextEditor()
    click on linkText("Save")
    return text
  }

  def addMultimedia(mType: String): String = {
    return "TODO"
  }

  def addResource(rType: String): String = {
    return "TODO"
  }

  def addSubpage(): String = {
    return "TODO"
  }

  def linkAssignment(): String = {
    return "TODO"
  }

  def linkQuiz(): String = {
    return "TODO"
  }

  def viewAddition(addition: ListBuffer[String]): Boolean = {
    var isFound: Boolean = true
    addition.toList.takeWhile(isFound => true).foreach(text =>
      if(webDriver.findElements(By.xpath("//*[contains(text(),'"+text+"')]")).isEmpty) {
        isFound = false
      } else {
        isFound = true
      })

    return isFound
  }

}
