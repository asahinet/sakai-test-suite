package com.anisakai.test.pageobjects

import java.util

import com.anisakai.test.Config
import org.openqa.selenium.By
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/26/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
object SiteManageTool extends SiteManageTool

class SiteManageTool extends Page {
  def manageAccess(publish: Boolean, globalAccess: Boolean) {
    Portal.xslFrameOne
    click on linkText("Manage Access")
    click on radioButton("publish")

    //TODO actually look at parameters sent in

    click on cssSelector("[value=Update]")
  }


  def createSiteWithSitesTool(siteType: String, siteTitle: String, siteId: String): Boolean = {
    Portal.xslFrameOne
    click on linkText("New Site")
    textField("id").value = siteId
    textField("title").value = siteTitle
    textField("type").value = siteType
    click on name("eventSubmit_doSave")

    // if we get an error that the site exists, click cancel, that is ok
    if (className("alertMessage").findElement(webDriver).isDefined &&
      className("alertMessage").webElement(webDriver).getText.contains("The site id " + siteId + " is already in use")) {
      click on name("eventSubmit_doCancel")
      return false
    }


    true
  }

  def verifyUserHasRole(eid: String, role: String) {
    // TODO table fun
  }

  def membershipDoesNotExist(eid: String): Boolean = {
    // if we get an error that the site exists, click cancel, that is ok
    if (className("information").findElement(webDriver).isDefined &&
      className("information").webElement(webDriver).getText.contains("The following participants are already members of this site and cannot be re-added: '" + eid + "'")) {
      return false
    }
    true
  }

  def bulkAddUsers(eids: ListBuffer[String], role: String) {
    eids.foreach(e => addUserWithRole(e, role))
  }

  def addUserWithRole(eid: String = "", role: String, bulk: Boolean = false, eids: ListBuffer[String] = null) {
    Portal.xslFrameOne
    click on linkText("Add Participants")
    if (bulk) {
      var eidString = new StringBuilder
      eids.foreach(e => eidString.append(e + "\n"))
      textArea("content::officialAccountParticipant").value = eidString.toString
    } else {
      textArea("content::officialAccountParticipant").value = eid
    }
    click on cssSelector("[value=Continue]")

    if (membershipDoesNotExist(eid)) {
      if (xpath("//input[@value='" + role + "']").findElement(webDriver).isDefined) {
        click on xpath("//input[@value='" + role + "']")
      } else if (role.toLowerCase == "student") {
        click on xpath("//input[@value='access']")
      } else {
        click on xpath("//input[@value='maintain']")
      }
      click on cssSelector("[value=Continue]")
      click on cssSelector("[value=Continue]")
      click on cssSelector("[value=Finish]")
    } else {
      click on cssSelector("[value=Cancel]")
    }
  }

  def findSiteAndEdit(siteTitle: String): Boolean = {
    var found = false
    Portal.xslFrameOne
    textField("search").value = siteTitle
    Config.client match {
      case "wvsu" => click on linkText("Search")
      case _ => click on cssSelector("[value=Search]")
    }

    if (className("instruction").webElement(webDriver).getText.contains("No sites were found")) {
      found = false
    } else {
      checkbox("site1").select()
      click on linkText("Edit")
      found = true
    }
    found
  }

  def createProjectSite(title: String, shortDescription: String,
                        longDescription: String, contactName: String): String = {
    createSite("project", shortDescription, longDescription, contactName,
      populateProjectMetaData: (Map[String, String]) => Unit,
      Map(("title", title)))
  }

  def createCourseSite(subject: String, section: String, course: String,
                       shortDescription: String, longDescription: String,
                       contactName: String, contactEmail: String, tools: List[String] = Nil): String = {
    createSite("course", shortDescription, longDescription, contactName,
      populateCourseMetaData: (Map[String, String]) => Unit,
      Map(("subject", subject), ("section", section), ("course", course)))
  }

  def populateCourseMetaData(args: Map[String, String]): Unit = {
    textField("id-Subject:1").value = args("subject")
    textField("id-Course:1").value = args("course")
    textField("id-Section:1").value = args("section")
    textField("uniqname").value = "admin"
    click on cssSelector("[value=Continue]")
  }

  def populateProjectMetaData(args: Map[String, String]) {
    textField("title").value = args("title")
  }

  def editSite(shortDescription: String, longDescription: String, contactName: String) {

    click on linkText("Edit Site Information")
    textArea("short_description").value = shortDescription
    textField("siteContactName").value = contactName
    click on cssSelector("[value=Continue]")

    click on cssSelector("[value=Finish]")
  }

  def createSite(siteType: String, shortDescription: String, longDescription: String,
                 contactName: String,
                 siteMetaData: (Map[String, String]) => Unit, siteMetaDataArgs: Map[String, String], tools: List[String] = Nil): String = {
    val maintainRole = getMaintainRole
    Portal.goToTool("Site Setup")
    Portal.getToFrameZero
    click on linkText("New")
    click on radioButton(siteType.toLowerCase)
    click on id("submitBuildOwn")

    siteMetaData(siteMetaDataArgs)
    val siteTitle = faker.letterify("????????")
    textField("title").value = siteTitle
    textArea("short_description").value = shortDescription
    textField("siteContactName").value = contactName
    click on cssSelector("[value=Continue]")

    addTools(false, tools)
    click on xpath("//*[@value='Request Site']")

    eventually {
      switch to defaultContent
    }
    siteTitle
  }

  def createRandomSite(siteType: String, tools: List[String] = Nil): String = {
    siteType.toLowerCase match {
      case "course" => createCourseSite(faker.letterify("???"), faker.numerify("#"), faker.numerify("###"),
        faker.sentence(2), faker.sentence(2), faker.name(), faker.firstName() + "." + faker.lastName(), tools)
      case _ => createProjectSite(siteType + " Test " + faker.numerify("###"), faker.sentence(2),
        faker.sentence(2), faker.name)
    }
  }


  /*
  * The extraClick boolean in the following methods is used because when certain tools are added they require
  * further information before we can proceed. This inserts an extra screen between selecting tools and confirming
  * their selection. In the clickTools method we ensure that if the tool isn't already selected, when we actually
  * do select it we flag the extra click. We add a final check in addExtraToolInfo mostly because of Virtual Meeting.
  * Sometimes when the tool is added it requires confirmation of Site ID, sometimes it doesn't. If it is there the
  * extraClick returns true.
  */

  def addTools(withSitesTool : Boolean = false, tools: List[String] = Nil, siteID: String = "") {
    if (!siteID.equals("")) {
      Portal.goToAdminWorkspace
      Portal.goToTool("Site Setup", true)
      Portal.xslFrameOne
      xpath("//*[@id='search']").webElement.sendKeys(Config.defaultCourseSiteTitle)
      click on cssSelector("[value=Search]")
      click on id("site1")
      click on cssSelector("[title=Edit]")
    }
    click on linkText("Edit Tools")
    Portal.xslFrameOne

    var extraClick = false

    if (tools == Nil) {
      // adding tools
      extraClick = clickTools()
    } else {
      extraClick = clickTools(tools)
    }
    click on cssSelector("[value=Continue]")
    if (extraClick) {
      if (!tools.isEmpty) {
        tools.foreach {
          e => e match {
            case "Web Content" => extraClick = addExtraToolInfo(url = true)
            case "Email Archive" => extraClick = addExtraToolInfo(email = true)
            case "ANI Virtual Meeting" => extraClick = addExtraToolInfo(vm = true)
            case _ => // continue
          }
        }
      } else {
        // if the list is empty we are adding all tools
        addExtraToolInfo(true, true, true)
      }
      if (extraClick) { click on cssSelector("[value=Continue]") }
    }

    Config.skin match {
      case "xsl" => click on name("review")
      case _ => click on cssSelector("[value=Finish]")
    }
  }

  // This is used to add the information required on the extra screen
  def addExtraToolInfo(email: Boolean = false, url: Boolean = false, vm: Boolean = false): Boolean = {
    var extraClick = false
    if (vm) {
      if (id("1_SITE_ID").findElement(webDriver).isDefined) {
        textField("1_SITE_ID").value = Config.defaultCourseSiteId
        extraClick = true
      }
    }
    if (email) {
      if (id("emailId").findElement(webDriver).isDefined) {
        textField("emailId").value = faker.lastName() + faker.numerify("####")
        extraClick = true
      }
    }
    if (url) {
      if (id("source_sakai.iframe").findElement(webDriver).isDefined) {
        textField("source_sakai.iframe").value = faker.lastName() + faker.numerify("####")
        extraClick = true
      }
    }
    if (vm && email && url) {
      extraClick = true
    }

    return extraClick
  }

  // This is used to click all the tools if parameterless, or certain tools if a list of tools is sent
  def clickTools(tools: List[String] = Nil) : Boolean = {
    var extraClick = false
    click on partialLinkText("Plugin Tools")
    if (tools == Nil) {
      val toolList = webDriver.findElements(By.xpath("//input[@type='checkbox']")).asScala
      // see if it is already checked, if not then check it
      toolList.foreach(e =>
        if (!e.isSelected) {
          e.click
        }
      )
      extraClick = true
    } else {
      var tool = xpath("//*").webElement
      tools.foreach {
        e => tool = webDriver.findElement(By.xpath("//label[contains(text(), '"+e+"')]/../input"))
          // see if it is already checked, if not then check it
          if (!tool.isSelected) {
            tool.click
            e.toLowerCase match {
              case "web content" | "email archive" | "ani virtual meeting" | "external tool" | "lesson builder" => extraClick = true
              case _ => // continue
            }
          }
      }
    }
    extraClick
  }

  def getMaintainRole : String = {
    Portal.goToAdminWorkspace
    Portal.goToTool("Realms")
    Portal.xslFrameOne
    webDriver.findElement(By.xpath("//a[contains(text(), '!site.template.course')]/../../following-sibling::td[@headers='Maintain']")).getText
  }

  def goToEditTools {
    click on linkText("Edit Tools")
  }
}
