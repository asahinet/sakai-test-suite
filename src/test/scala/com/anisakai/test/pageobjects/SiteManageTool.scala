package com.anisakai.test.pageobjects

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 8/26/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteManageTool extends Page {
  def createRandomSite() {
    click on linkText("New")
    click on radioButton("course")
    click on id("submitBuildOwn")

    textField("id-Subject:1").value = faker.letterify("aaaa")

    textField("id-Course:1").value = faker.numerify("###")

    textField("id-Section:1").value = faker.numerify("#")

    textField("uniqname").value = "admin"

    click on id("addButton")

    textArea("short_description").value = faker.sentence(10)

    textField("siteContactName").value ="Your Mom"

    // can't get this stupid button to work anyway I try, so invoking the javascript directly (hack!)
    executeScript("resetOption('continue');")

    click on checkbox("all")

    click on name("Continue")

    textField("emailId").value = faker.firstName() + "." + faker.lastName()

    click on name("Continue")

    click on name("eventSubmit_doUpdate_site_access")

    click on "addSite"
  }

}
