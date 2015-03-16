package com.anisakai.test.cucumber

import com.anisakai.test.Config
import cucumber.api.CucumberOptions
import org.junit.AfterClass
import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber

@RunWith(classOf[Cucumber])
@CucumberOptions(tags = Array("@setup"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
object SetupTest {
  @AfterClass
  def tearDown {
    Config.webDriver.close
  }
}