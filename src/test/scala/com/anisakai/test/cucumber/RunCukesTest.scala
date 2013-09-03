package com.anisakai.test.cucumber

import _root_.cucumber.api.junit.Cucumber
import _root_.cucumber.api.junit.Cucumber.Options
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@Options(tags = Array("~@ignore"), format = Array("pretty", "html:target/cucumber-report", "json:target/cucumber.json"))
class RunCukesTest



