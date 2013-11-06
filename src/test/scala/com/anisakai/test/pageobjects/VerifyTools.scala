package com.anisakai.test.pageobjects

import org.openqa.selenium.{WebElement, By}
import scala.collection.mutable.ListBuffer
import java.util.List
import java.util.concurrent.TimeUnit


/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 9/9/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
object VerifyTools extends VerifyTools

class VerifyTools extends Page {
  def checkTools(): Boolean = {
    var fails = ListBuffer[String]()
    try {
      for (i <- 0 until webDriver.findElements(By.className("toolMenuLink")).size) {
        switch to defaultContent
        var toolName = webDriver.findElements(By.className("toolMenuLink")).get(i).getText()
        click on webDriver.findElements(By.className("toolMenuLink")).get(i)
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        var isPresent = !webDriver.findElements(By.xpath("//iframe[contains(@title,$toolName)]")).isEmpty
        if (!isPresent) {
          if (webDriver.findElement(By.className("portletMainWrap")).getText().contains("Error"))
            fails += toolName
        } else {
          switch to frame(webDriver.findElement(By.xpath("//iframe[contains(@title,$toolName)]")))
          if (!webDriver.findElements(By.className("portletBody")).isEmpty() && webDriver.findElements(By.className("portletBody")).get(0).getText().contains("Error"))
            fails += toolName
        }
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
      }
    } catch {
      case e: Exception => {
        e.printStackTrace()
        return hasFailed(fails, true)
      }
    }

    return hasFailed(fails)
  }

  def hasFailed(fails : ListBuffer[String]): Boolean = {
    return hasFailed(fails, false)
  }

  def hasFailed(fails : ListBuffer[String], failure: Boolean): Boolean = {
    if (fails.isEmpty || failure) {
       println("Failed Tools:\n")
       fails.foreach(e => println(e))
       true
    } else {
      false
    }
  }

}
