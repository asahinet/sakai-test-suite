package com.anisakai.test.pageobjects

import org.openqa.selenium.{By, WebElement}
import java.util.concurrent.TimeUnit
import com.anisakai.test.Config
import org.scalatest.concurrent.Eventually
import com.github.javafaker.Faker

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 8/30/13
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
class Google extends Page with Eventually {
  def text: TextField = textField("q")

  def searching() {
    click on name("btnG")
  }

  def clickBooks() {
    click on linkText("Books")
  }

  def enterText(text: String) {
    this.text.value = text
  }

  def gotResults() : Boolean = {
    return webDriver.getCurrentUrl.contains("http://www.amazon.com/books-used-books-textbooks/b?ie=UTF8&node=283155")
  }

}
