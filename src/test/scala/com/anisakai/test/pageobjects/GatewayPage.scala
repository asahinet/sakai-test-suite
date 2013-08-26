package com.anisakai.test.pageobjects

import org.openqa.selenium.{By, WebElement}
import java.util.concurrent.TimeUnit

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/23/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
class GatewayPage extends Page {
  def eid: TextField = textField("eid");
  def password: PasswordField = pwdField("pw");

  def login() {
    submit();
  }

  def login(eid : String, password : String) {
    webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    go to host
    enterEid(eid)
    enterPassword(password)
    submit()
  }

  def enterEid(eid: String) {
    this.eid.value = eid
  }

  def enterPassword(password: String) {

    this.password.value = password
  }

  def isMyWorkspace() : Boolean = {
    return webDriver.findElement(By.className("siteTitle")).getText().startsWith("My Workspace")
  }

  def logout() {
    switch to defaultContent
    click on linkText("Logout")
  }
}
