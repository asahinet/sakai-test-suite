package com.anisakai.test.cucumber

import com.anisakai.test.Config
import org.junit.AfterClass
import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.CucumberOptions

//Change the tag to whatever script you are developing

@RunWith(classOf[Cucumber])
@CucumberOptions(tags = Array("@assignment"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
object InDevTest {
  @AfterClass
  def tearDown {
    Config.webDriver.close
  }
}