package com.anisakai.test.pageobjects

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/26/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
object SiteManageTool extends SiteManageTool

class SiteManageTool extends Page {

  def verifyUserHasRole(eid: String, role: String) {
    // TODO table fun
  }

  def addUserWithRole(eid: String, role: String){
    click on linkText("Add Participants" )
    textArea("content::officialAccountParticipant").value = eid
    click on "content::continue"
    click on cssSelector("[value=" + role + "]")
    click on cssSelector("[value=Continue]")
    click on cssSelector("[value=Continue]")
    click on cssSelector("[value=Finish]")

  }

  def findSiteAndEdit(siteTitle : String){
    textField("search").value = siteTitle;
    click on cssSelector("[value=Search]")

    click on checkbox("site1")
    click on linkText("Edit")
  }

  def createRandomSite(siteType : String)  : String = {
    click on linkText("New")
    click on radioButton(siteType.toLowerCase())
    click on id("submitBuildOwn")
    if (siteType.equalsIgnoreCase("Course")) {
      textField("id-Subject:1").value = faker.letterify("????")
      textField("id-Course:1").value = faker.numerify("###")
      textField("id-Section:1").value = faker.numerify("#")
      textField("uniqname").value = "admin"
      click on cssSelector("[value=Continue]")
      textArea("short_description").value = faker.sentence(2)
      textField("siteContactName").value ="Your Mom"
      click on cssSelector("[value=Continue]")
    } else {
      textField("title").value = siteType + " Test " + faker.numerify("###")
      textArea("short_description").value = faker.sentence(2)
      textField("siteContactName").value ="Your Mom"
      click on cssSelector("[value=Continue]")
    }
    click on checkbox("all")
    click on cssSelector("[value=Continue]")
    textField("emailId").value = faker.firstName() + "." + faker.lastName()
    click on cssSelector("[value=Continue]")
    click on cssSelector("[value=Continue]")

    val siteTitle = xpath("//table[@class='itemSummary']//tr[1]//td[1]").element.text

    click on "addSite"
    eventually {
      switch to frame(0)
    }

    return  siteTitle;

  }

}
