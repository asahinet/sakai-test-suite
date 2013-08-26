package com.anisakai.test.cucumber

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.junit.Cucumber.Options

@RunWith(classOf[Cucumber])
@Options(tags = Array("~@ignore"), format = Array("pretty", "html:target/cucumber-report"))
class RunCukesTest



