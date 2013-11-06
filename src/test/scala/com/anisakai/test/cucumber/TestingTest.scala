//Used to test the test scripts being written. Not part of test suite.
package com.anisakai.test.cucumber

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber
import cucumber.api.junit.Cucumber.Options

@RunWith(classOf[Cucumber])
@Options(tags = Array("@lessonbuilder"), glue = Array("com.anisakai.test.cucumber.stepdefs"), format = Array("progress", "html:target/cucumber-report"))
class TestingTest




