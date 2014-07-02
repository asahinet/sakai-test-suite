package com.anisakai.test.pageobjects
import org.openqa.selenium.{By, WebElement}
import scala.io.Source
import scala.collection.mutable.ListBuffer

object AddTool extends AddTool

class AddTool extends Page {
  var tools = Source.fromFile("tools.txt").getLines.toList
}
