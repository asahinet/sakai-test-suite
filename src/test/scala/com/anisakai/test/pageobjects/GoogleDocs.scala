package com.anisakai.test.pageobjects

import java.util.Calendar

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 11/1/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */

object GoogleDocs extends GoogleDocs

class GoogleDocs extends Page {

  var docName = "Hi.doc"
  val cal = Calendar.getInstance
  cal.add(Calendar.DATE, 1)
  val past = cal.get(Calendar.MONTH) - 2
  val future = cal.get(Calendar.MONTH) + 2

  def addDoc(settings: String): String = {
    if (settings == "past") {
      docName = "Lorem.txt"
    } else if (settings == "future") {
      docName = "resources.doc"
    }
    Portal.xslFrameOne
    click on xpath("//li[contains(text(),'Add')]")
    click on xpath("//a[.='Link to Google Document']")
    if (xpath("//button[.=Proceed to Google]").findElement(webDriver).isDefined) {
      click on xpath("//button[.=Proceed to Google]")
      switch to defaultContent
      textField("Email").value = "rsmartsupp@gmail.com"
      textField("Passwd").value = "r5m@rt!!"
      click on id("signIn")
      click on id("submit_approve_access")
      eventually {
        Portal.xslFrameOne
      }
    }
    if (xpath("//a[.='documents']/../img[@src='img/folder.gif']").findElement(webDriver).isDefined) click on xpath("//a[.='documents']")
    click on xpath("//a[.='" + docName + "']")
    click on xpath("//*[@value='Next']")

    if (settings == "past") {
      checkbox("retractCheck").select()
      singleSel("endDateBean.month").value = past.toString
    } else if (settings == "future") {
      click on id("hideItemRadio")
      checkbox("retractCheck").select()
      singleSel("endDateBean.month").value = future.toString
    }
    click on xpath("//*[@value='Finish']")
    docName
  }

  def docAdded(doc: String, settings: String): Boolean = {
    Portal.xslFrameOne
    if (xpath("//a[contains(text(),'" + doc + "')]").findElement.isDefined) {
      if (settings == "should") true else false
    } else {
      if (settings == "should") false else true
    }
  }

  def cleanup() {
    Portal.xslFrameOne
    click on id("selectall")
    click on id("delete-button")
    click on xpath("//input[@value='Remove']")
  }

}
