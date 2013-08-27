package com.anisakai.test.cucumber

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.junit.Cucumber.Options
import org.junit.{AfterClass, After}
import com.anisakai.test.pageobjects.Page

@RunWith(classOf[Cucumber])
@Options(tags = Array("~@ignore"), format = Array("pretty", "html:target/cucumber-report"))
class RunCukesTest




