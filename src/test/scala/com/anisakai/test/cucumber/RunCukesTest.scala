package com.anisakai.test.cucumber

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.junit.Cucumber.Options
import org.junit.{AfterClass, After}
import com.anisakai.test.pageobjects.Page

@RunWith(classOf[Cucumber])
@Options(tags = Array("@adminusertool"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
class RunCukesTest


