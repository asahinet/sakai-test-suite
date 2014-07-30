package com.anisakai.test.cucumber.stepdefs

import com.anisakai.test.pageobjects.{AssignmentTool, LessonBuilder, Portal}
import cucumber.api.scala.{EN, ScalaDsl}
import junit.framework.Assert._

import scala.collection.mutable.ListBuffer

class LessonBuilderTest extends ScalaDsl with EN with TearDown {

  var addition = new ListBuffer[String]

  When( """^I add text to the lessons tool$""") { () =>
    addition += LessonBuilder.addText()
  }

  Then( """^I can view the addition as an instructor$""") { () =>
    assertTrue(LessonBuilder.viewAddition(addition))
    addition.clear()
  }

  When( """^I add '(.+)' '(.+)' to the lessons tool$""") { (addType: String, contentType: String) =>
    addition += LessonBuilder.add(contentType, addType)
  }

  When( """^I add '(.+)' subpages to the lesson tool$""") { (count: Int) =>
    for (i <- 0 to count) {
      addition += LessonBuilder.addSubpage()
    }
  }

  When( """^I create an assignment$""") { () =>
    Portal.gotoTool("Assignments", true)
    AssignmentTool.gotoAdd()
    addition += AssignmentTool.assignment()
  }

  When( """^I add the '(.+)' to the lesson tool$""") { (linkType: String) =>
    Portal.gotoTool("Lesson Builder", true)
    LessonBuilder.link(linkType, addition(0))
  }

  When( """^I create a quiz$""") { () =>
    Portal.gotoTool("Tests & Quizzes", true)
    addition += LessonBuilder.addQuiz()
  }
}
