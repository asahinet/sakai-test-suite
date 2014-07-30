package com.anisakai.test.pageobjects

import com.anisakai.test.Config
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select

import scala.collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */

object UsersTool extends UsersTool

class UsersTool extends Page {
  var currentEid: String = null
  var currentFirstName: String = null
  var oldFirstName: String = null

  switch to defaultContent

  def userEid: TextField = textField("eid")

  def lastName: TextField = textField("last-name")

  def search: TextField = textField("search")

  def firstName: TextField = textField("first-name")

  def email: TextField = textField("email")

  def pw: PasswordField = pwdField("pw")

  def pw0: PasswordField = pwdField("pw0")

  def userTypeSelect: Select = new Select(webDriver.findElement(By.name("type")))

  def userTypeTextInput: TextField = textField("type")

  def createUser(eid: String, firstname: String, lastname: String, email: String, usertype: String, password: String) {
    Portal.xslFrameOne
    click on linkText("New User")
    this.userEid.value = eid
    this.firstName.value = firstname
    this.lastName.value = lastname
    this.email.value = email
    this.pw.value = password
    this.pw0.value = password

    if (Config.sakaiDistro.equals("ani")) {
      this.userTypeSelect.selectByVisibleText(usertype)
    } else {
      this.userTypeTextInput.value = usertype
    }

    click on name("eventSubmit_doSave")

    // if we get an error that the user exists, click cancel, that is ok
    if (className("alertMessage").findElement(webDriver).isDefined &&
      className("alertMessage").webElement(webDriver).getText().contains("user id is already in use")) {
      click on name("eventSubmit_doCancel")
    }
  }

  def findOrCreateUser(eid: String) {
    createUser(eid, faker.firstName(), faker.lastName(), eid + "@asdf.com", "registered", "password")
  }

  def randomUser(): String = {
    val eid = faker.numerify("#########")
    createUser(eid, faker.firstName(), faker.lastName(), eid + "@asdf.com", "registered", "password")
    return eid
  }

  def submitSearch() {
    click on linkText("Search")
  }

  def enterSearchText(search: String) {
    Portal.xslFrameOne
    this.search.value = search
  }

  def clickAllLinks() {
    var linkElements = webDriver.findElements(By.tagName("a"));
    var linkTexts = new Array[String](linkElements.length)
    var i: Int = 0;

    //extract the link texts of each link element
    for (e <- linkElements) {
      linkTexts(i) = e.getText();
      i = i + 1
    }

    linkTexts.foreach { (t: String) =>
      webDriver.findElement(By.linkText(t)).click();
      System.out.println("\"" + t + "\"" + " is working.");
      webDriver.navigate().back();
    }

  }

  def foundUser(eid: String): Boolean = {
    this.currentEid = eid;
    return partialLinkText(eid) != null
  }

  def editFirstName(firstName: String) {
    click on partialLinkText(currentEid)
    oldFirstName = this.firstName.value
    this.firstName.value = firstName
    this.currentFirstName = firstName
    click on name("eventSubmit_doSave")
  }

  def hasFirstNameChanged(): Boolean = {
    return cssSelector("h4:contains('" + currentFirstName + "')") != null
  }

}
