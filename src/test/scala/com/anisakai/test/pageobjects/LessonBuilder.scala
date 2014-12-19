package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import com.anisakai.test.util.FileUtil
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}

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
  val action = new Actions(webDriver)
  val webWait = new WebDriverWait(webDriver, 5)

  def addText : String = {
    Portal.xslFrameOne
    click on xpath("//span[.='Add Content']/..")
    action.moveToElement(webDriver.findElement(By.linkText("Add Text")))
    webWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Add Text")))
    click on linkText("Add Text")
    val text = Portal.richTextEditor
    click on cssSelector("[value=Save]")
    text
  }

  def add(addType: String, contentType: String): String = {
    val title: String = faker.letterify("??????")
    Portal.xslFrameOne
    click on xpath("//span[.='Add Content']/..")
    if (addType == "multimedia") {
      action.moveToElement(webDriver.findElement(By.linkText("Embed content on page"))) // Keeps the drawer open
      webWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Embed content on page")))
      click on linkText("Embed content on page")
    } else {
      action.moveToElement(webDriver.findElement(By.linkText("Embed content on page")))
      webWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Embed content on page")))
      click on linkText("Add Content Link")
    }
    if (contentType.equalsIgnoreCase("url")) {
      webWait.until(ExpectedConditions.presenceOfElementLocated(By.id("mm-url")))
      textField("mm-url").value = Config.targetServer
      click on cssSelector("[value=Save]")
      Config.targetServer
    } else {
      webDriver.findElement(By.id("mm-file")).sendKeys(FileUtil.createTempFile(title, ".txt", faker.paragraph(2)))
      click on cssSelector("[value=Save]")
      title
    }
  }

  def addQuiz : String = {
    val title: String = faker.letterify("??????")
    textField("authorIndexForm:title").value = title
    singleSel("authorIndexForm:assessmentTemplate").value = "3"
    click on cssSelector("[value=Create]")
    singleSel("assesssmentForm:parts:0:changeQType").value = "6"
    textField("itemForm:answerptr").value = "100"
    textArea("itemForm:_id73_textinput").value = faker.paragraph
    click on cssSelector("[value=Save]")
    click on linkText("Publish")
    click on cssSelector("[value=Publish]")
    title
  }

  def addSubpage : String = {
    val title: String = faker.letterify("??????")
    Portal.xslFrameOne
    click on xpath("//span[.='Add Content']/..")
    action.moveToElement(webDriver.findElement(By.linkText("Add Subpage"))) // Keep drawer open
    webWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Add Subpage")))
    click on linkText("Add Subpage")
    textField("subpage-title").value = title
    click on cssSelector("[value=Create]")
    click on xpath("//a[contains(@title,'Reset')]")
    title
  }

  def link(linkType: String, title: String) {
    Portal.xslFrameOne
    click on xpath("//span[.='Add Content']/..")
    action.moveToElement(webDriver.findElement(By.linkText("Add " + linkType)))
    webWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Add " + linkType)))
    click on linkText("Add " + linkType)
    click on xpath("//input[@title='" + title + "']")
    click on cssSelector("[value=Use selected item]")
  }

  def viewAddition(addition: ListBuffer[String]): Boolean = {
    var isFound: Boolean = true
    addition.foreach { text =>
      if (!xpath("//*[contains(text(),'" + text + "')]").findElement(webDriver).isDefined) {
        return false
      }
    }
    true
  }

}
