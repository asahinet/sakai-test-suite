package com.anisakai.test.cucumber

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.CucumberOptions

//Change the tag to whatever script you are developing

@RunWith(classOf[Cucumber])
@CucumberOptions(tags = Array("@sitecreate"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
class InDevTest


