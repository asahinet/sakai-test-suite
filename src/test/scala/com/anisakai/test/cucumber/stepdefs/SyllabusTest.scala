package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import com.anisakai.test.pageobjects._
import junit.framework.Assert._

class SyllabusTest extends ScalaDsl with EN with TearDown {
  var syllabusName: String = ""
  When("""^I add a syllabus to the course$""") { () =>
    syllabusName = SyllabusTool.addSyllabus()
  }

  When("""^I redirect a syllabus to a URL$""") { () =>
    syllabusName = SyllabusTool.addSyllabus(true)
  }

  Then("""^I should view the syllabus$"""){ () =>
    assertTrue(SyllabusTool.syllabusExists(syllabusName))
  }

  When("""^I edit the syllabus$"""){ () =>
    syllabusName = SyllabusTool.editSyllabus(syllabusName)
  }

  Then("""^I should view the redirect$"""){ () =>
    assertTrue(SyllabusTool.redirectExists(syllabusName))
  }

  When("""^I remove all syllabus$""") { () =>

  }

}
