package com.anisakai.test.pageobjects

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/26/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteManageTool extends Page {
  //var siteId : String
  var siteTitle : String = null

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

  def createRandomSite() {
    click on linkText("New")
    click on radioButton("course")
    click on id("submitBuildOwn")

    textField("id-Subject:1").value = faker.letterify("????")

    textField("id-Course:1").value = faker.numerify("###")

    textField("id-Section:1").value = faker.numerify("#")

    textField("uniqname").value = "admin"

    click on cssSelector("[value=Continue]")

    textArea("short_description").value = faker.sentence(2)

    textField("siteContactName").value ="Your Mom"

    click on cssSelector("[value=Continue]")

    click on checkbox("all")

    click on name("Continue")

    textField("emailId").value = faker.firstName() + "." + faker.lastName() + "@yourmom.com"

    click on name("Continue")

    click on name("eventSubmit_doUpdate_site_access")

    siteTitle = xpath("//table[@class='itemSummary']//tr[1]//td[1]").element.text

    click on "addSite"

    eventually {
      switch to frame(0)
    }

    textField("search").value = siteTitle;

    click on cssSelector("[value=Search]")

    click on checkbox("site1")

    click on linkText("Edit")

  }

}
