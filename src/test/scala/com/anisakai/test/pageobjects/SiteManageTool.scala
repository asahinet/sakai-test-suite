package com.anisakai.test.pageobjects

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
object SiteManageTool extends SiteManageTool {
}

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
    if (Config.client == "wvsu") {
      click on linkText("Search")
    } else {
      click on cssSelector("[value=Search]")
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
                       contactName: String, contactEmail: String, tools: Boolean = true): String = {
    createSite("course", shortDescription, longDescription, contactName,
      populateCourseMetaData: (Map[String, String]) => Unit,
      Map(("subject", subject), ("section", section), ("course", course)), tools)
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
                 siteMetaData: (Map[String, String]) => Unit, siteMetaDataArgs: Map[String, String], tools: Boolean = true): String = {
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
    siteTitle
  }

  def createRandomSite(siteType: String, tools: Boolean = true): String = {
    if (siteType.equalsIgnoreCase("Course")) {
      createCourseSite(faker.letterify("???"), faker.numerify("#"), faker.numerify("###"),
        faker.sentence(2), faker.sentence(2), faker.name(), faker.firstName() + "." + faker.lastName(), tools)
    } else {
      createProjectSite(siteType + " Test " + faker.numerify("###"), faker.sentence(2),
        faker.sentence(2), faker.name)
    }

  }

  def addTools(withSitesTool : Boolean = false, all : Boolean = true) {
    // We should only use this function upon creating a new site
    // otherwise we will see duplicate tools on an existing site
    Portal.xslFrameOne
    if (withSitesTool) {
      goToEditTools
    }

    if (all) { // adding all tools
      if (Config.sakaiVersion.startsWith("10.")) {
        clickAllTools
      } else {
        click on checkbox("all")
      }
    }

    click on cssSelector("[value=Continue]")

    if (all) { // we only want this if we are adding tools
      // Add the email address and the URL for the web content tool
      if (id("emailId").findElement(webDriver).isDefined) {
        textField("emailId").value = faker.lastName() + faker.numerify("####")
      }
      if (id("source_sakai.iframe").findElement(webDriver).isDefined) {
        textField("source_sakai.iframe").value = Config.targetServer
      }
      click on cssSelector("[value=Continue]")
    }

    if (Config.skin == "xsl") {
      click on name("review")
    } else {
      if (Config.sakaiVersion.startsWith(("10."))) {
        click on cssSelector("[value=Continue]")
      } else {
        click on cssSelector("[value=Finish]")
      }
    }

    click on xpath("//*[@value='Request Site']")

    eventually {
      switch to defaultContent
    }

  }

  def clickAllTools {
    val linkElements = webDriver.findElements(By.xpath("//input[@type='checkbox']")).asScala
    //see if it is already checked, if not then check it
    linkElements.foreach(e =>
      if (!e.isSelected) {
        e.click
      }
    )
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
