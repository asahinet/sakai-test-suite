package com.anisakai.test.cucumber

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.CucumberOptions

@RunWith(classOf[Cucumber])
@CucumberOptions(tags = Array("@suite"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
class RunTestSuite




