package com.anisakai.test.pageobjects

import com.anisakai.test.util.FileUtil
import org.openqa.selenium.By

import scala.collection.mutable.ListBuffer

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
    click on cssSelector("Add Text")
    val text = Portal.richTextEditor()
    click on cssSelector("[value=Save]")
    return text
  }

  def add(addType: String, contentType: String): String = {
    val title: String = faker.letterify("??????")
    click on linkText("Add " + addType)
    if (contentType.equalsIgnoreCase("url")) {
      textField("mm-url").value = "https://nightly.cle.rsmart.com/portal"
      click on cssSelector("[value=Save]")
      return "https://nightly.cle.rsmart.com/portal"
    } else {
      webDriver.findElement(By.className("mm-file")).sendKeys(FileUtil.createTempFile(title, ".txt", faker.paragraph(2)))
      click on cssSelector("[value=Save]")
      return title
    }
  }

  def addQuiz(): String = {
    val title: String = faker.letterify("??????")
    textField("authorIndexForm:title").value = title
    singleSel("authorIndexForm:assessmentTemplate").value = "3"
    click on cssSelector("[value=Create]")
    singleSel("assesssmentForm:parts:0:changeQType").value = "6"
    textField("itemForm:answerptr").value = "100"
    textArea("itemForm:_id73_textinput").value = faker.paragraph()
    click on cssSelector("[value=Save]")
    click on linkText("Publish")
    click on cssSelector("[value=Publish]")
    return title
  }

  def addSubpage(): String = {
    val title: String = faker.letterify("??????")
    click on linkText("Add Subpage")
    textField("subpage-title").value = title
    click on cssSelector("[value=Create]")
    click on xpath("//a[contains(@title,'Reset')]")
    return title
  }

  def link(linkType: String, title: String) {
    click on linkText("Add " + linkType)
    click on xpath("//input[@title='" + title + "']")
    click on cssSelector("[value=Use selected item]")
  }

  def linkQuiz(): String = {
    return "TODO"
  }

  def viewAddition(addition: ListBuffer[String]): Boolean = {
    var isFound: Boolean = true
    addition.toList.takeWhile(isFound => true).foreach(text =>
      if (!xpath("//*[contains(text(),'" + text + "')]").findElement(webDriver).isDefined) {
        isFound = false
      } else {
        isFound = true
      })

    return isFound
  }

}
