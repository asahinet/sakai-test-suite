package com.anisakai.test.cucumber.stepdefs

import cucumber.api.scala.{EN, ScalaDsl}
import cucumber.runtime.PendingException

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 9/30/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
class AssignmentTest extends ScalaDsl with EN with TearDown{
  Given("""^I'm logged in as an instructor$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  When("""^I create an assignment in Course site$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Then("""^I see an assignment listed$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Then("""^I can view the assignment as instructor$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Given("""^I'm logged in as a student$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Given("""^that student has been added to my course$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }
  When("""^I open the assignment listed$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Then("""^I should be able to submit the assignment$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Given("""^I have created a course with an assignment$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  When("""^I edit the existing assignment$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  When("""^I change the title and date$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Then("""^the updated assignment shows the new title and date$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Given("""^I am logged in as an instructor$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  When("""^I delete the assignment$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Then("""^it should no longer be in the table$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }

  Then("""^the student should no longer have access$"""){ () =>
    //// Express the Regexp above with the code you wish you had
    throw new PendingException()
  }
}
