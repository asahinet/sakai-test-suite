package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{ScalaDsl, EN}
import com.anisakai.test.pageobjects.LessonBuilder
import junit.framework.Assert._
import cucumber.runtime.PendingException
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 11/5/13
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
class LessonBuilderTest extends ScalaDsl with EN with TearDown {

  var addition = new ListBuffer[String]

  When("""^I add text to the lessons tool$"""){ () =>
    addition += LessonBuilder.addText()
  }

  Then("""^I can view the addition as an instructor$"""){ () =>
    assertTrue(LessonBuilder.viewAddition(addition))
    addition.clear()
  }

  When("""^I add '(.+)' multimedia to the lessons tool$"""){ (mType: String) =>
    addition += LessonBuilder.addMultimedia(mType)
  }

  When("""^I add a '(.+)' resource to the lessons tool$"""){ (rType: String) =>
    addition += LessonBuilder.addResource(rType)
  }

  When("""^I add two subpages to the lesson tool$"""){ () =>
    addition += LessonBuilder.addSubpage()
  }

  When("""^I create an assignment$"""){ () =>
  //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  When("""^I add the assignment to the lesson tool$"""){ () =>
    addition += LessonBuilder.linkAssignment()
  }

  When("""^I create a quiz$"""){ () =>
  //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  When("""^I add the quiz to the lesson tool$"""){ () =>
    addition += LessonBuilder.linkQuiz()
  }

}
