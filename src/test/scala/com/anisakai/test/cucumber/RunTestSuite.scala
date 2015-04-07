package com.anisakai.test.cucumber

import com.anisakai.test.Config
import org.junit.AfterClass
import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.CucumberOptions

@RunWith(classOf[Cucumber])
@CucumberOptions(tags = Array("@suite"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
object RunTestSuite {
  @AfterClass
  def tearDown {
    Config.webDriver.close
  }
}