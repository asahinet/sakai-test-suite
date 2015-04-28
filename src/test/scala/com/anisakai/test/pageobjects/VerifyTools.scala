package com.anisakai.test.pageobjects

import java.util.concurrent.TimeUnit

import com.anisakai.test.Config
import org.openqa.selenium.By

import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/9/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
object VerifyTools extends VerifyTools

class VerifyTools extends Page {

  def checkTools: Boolean = {
    var fails = new ListBuffer[String]
    var groupName: String = null

    if (Config.skin == "neo") {
      groupName = "toolMenuLink"
    } else {
      groupName = "icon-sakai-"
    }
    try {
      for (i <- 0 until webDriver.findElements(By.xpath("//*[contains(@class, '" + groupName + "')]")).size) {
        switch to defaultContent
        val toolName = webDriver.findElements(By.xpath("//*[contains(@class, '" + groupName + "')]")).get(i).getText
        click on webDriver.findElements(By.xpath("//*[contains(@class, '" + groupName + "')]")).get(i)

        // This is done to avoid a long wait in between clicking each tool, but we don't want it too fast where the browser can't keep up
        webDriver.manage.timeouts.implicitlyWait(500, TimeUnit.MILLISECONDS)

        // If there is an iframe present we want to check inside of that iframe
        if (!xpath("//iframe[contains(@title,'" + toolName + "')]").findElement(webDriver).isDefined) {
          if (xpath("//h3[contains(text(), 'Error')]").findElement(webDriver).isDefined)
            fails += toolName
        } else {
          switch to frame(webDriver.findElement(By.xpath("//iframe[contains(@title,'" + toolName + "')]")))
          if (xpath("//h3[contains(text(), 'Error')]").findElement(webDriver).isDefined)
            fails += toolName
        }
        webDriver.manage.timeouts.implicitlyWait(Config.timeout.toInt, TimeUnit.SECONDS)
      }
    } catch {
      case e: Exception =>
        e.printStackTrace
        return isSuccess(fails.toList, true)
    }
    isSuccess(fails.toList)
  }

  // If no failures have been added to the list and no exception was caught we can pass the test
  def isSuccess(fails: List[String], exception: Boolean = false): Boolean = {
    if (fails.nonEmpty || exception) {
      println("Failed Tools:")
      fails.foreach(e => println(e))
      true
    } else {
      false
    }
  }

}
