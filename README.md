Sakai Test Suite
======================

This project's aim it to create a regression suite that runs against a community or ANI (formerly rSmart)
version of Sakai. It performs actions via direct browser interactions.

The tests are feature or behavior driven (BDD) using cucumber.  These tests are then implemented in scala using the [cucumber-jvm](https://github.com/cucumber/cucumber-jvm)
via cucumber-scala and cucumber-junit.  The ScalaTest library is used for the [Selenium DSL](http://www.scalatest.org/user_guide/using_selenium),
which ends up pushing the Selenium WebDriver, but in a more natural and simpler way.  The Selenium DSL will let you get directly at the
[Selenium WebDriver](http://selenium.googlecode.com/svn/trunk/docs/api/java/index.html) object when you need more power.

Requirements
-------------

[Maven 3.2.3+](http://maven.apache.org/download.cgi)

[Scala 2.10.4+](http://www.scala-lang.org/download/2.10.4.html)

Creating a Test
---------------

### Create Test Scenario

Create a text file with a ".feature" extension in the resources area.

    Feature: Test CLE Login

         Scenario: CLE Local User Login
         Given I am on the 'https://nightly.cle.rsmart.com/portal' gateway page
         When I enter 'admin' for user
         And I enter 'admin' for password
         And I click the Login button
         Then I should see my workspace

### Create The Scala Test

Next you'll need to create your scala test. These will extract the variables from your cucumber text file
and delegate calls to your page object. Create a Scala file in the scala/com.anisakai.test/cucumber/stepdefs directory

If you simply run the test before creating the Scala file, the output from junit will actually tell you what
you method will look like, then you can simply copy and paste that in, and address the dynamic parts and implement.
The output will give both scala and java version of the code, you only want the scala output.

For example, you would see something like this

     You can implement missing steps with the snippets below:

     Given("""^I am on the 'https://nightly.cle.rsmart.com/portal' gateway page$"""){ () =>
       //// Express the Regexp above with the code you wish you had
       throw new PendingException()
     }

The variables, as in the case above will be outputted from junit as a string. These should be changed to a regex which will then be extracted as a variable.
This allows for reuse of the scenario throughout the test suite.

    Given( """^I am on the '(.+)' gateway page$""") {
      (url: String) =>
        gatewayPage.navigateToPage(url)
    }

Your final test would then look like this

    class LoginTest extends ScalaDsl with EN with TearDown {

      Given( """^I am on the '(.+)' gateway page$""") {
        (url: String) =>
          Portal.navigateToPage(url)
          Portal.logout
      }

      When( """^I enter '(.+)' for user""") {
        (eid: String) =>
          Portal.enterEid(eid)
      }

      When( """^I enter '(.+)' for password""") {
        (password: String) =>
          Portal.enterPassword(password)
      }

      When( """^I click the Login button""") {
        Portal.login
      }

      Then( """^I should see my workspace""") {
        assertTrue(Portal.isMyWorkspace)
      }
    }

### Create Page Objects

We've tried just recording tests using the straight Selenium IDE in firefox and while its easy to start,
it becomes hard to finish, as the maintenance of it quickly becomes unbearable.

The idea of the page object is to encapsulate the actual WebDriver logic into one place.  In the beginning it seems
like a waste of time and busy work, but over time you will understand the advantage of this.  The Selenium code
ends up getting tied to xpath, or cssSelector, or other text on the page.  Then things can change and break your tests.
By isolating this stuff you shield you code base from impact of these changes.  Also, this aids with reuse as your test
suite grows.

    object Portal extends Portal

    class Portal extends Page{
       def eid: TextField = textField("eid");
       def password: PasswordField = pwdField("pw");

       def login {
         submit
       }

       def enterEid(eid: String) {
         this.eid.value = eid;
       }

       def enterPassword(password: String) {
         this.password.value = password;
       }

       def isMyWorkspace: Boolean = webDriver.findElementByClassName("siteTitle").getText.startsWith("My Workspace")

    }

Running the Tests
-----------------

### Use maven

 mvn test

### Use your IDE

Invoke RunCukesTest in the normal way you run junit tests in your IDE.  In Intellij, this is just a matter
of right clicking on the file name, and selecting run.

### Configuration

The following system properties are available.  These should be sent to the as JVM properties to your execution context.

- target.browser

    supports the following: firefox, chrome, ie, safari, phantomjs, or htmlunit (headless).  Defaults to firefox.

- target.server

    pull path to your Sakai instance (including the /portal)

- webdriver.chrome.driver

    If you are using chrome the chrome driver must be made available, see https://code.google.com/p/chromedriver/downloads/list to download the
    version for your system.  For more info, https://code.google.com/p/chromedriver/wiki/GettingStarted.  If you are running
    on a Mac platform, and your working directory is set to this project's root, you don't have to include this, it will be
    found automatically.

- webdriver.phantomjs.binary

    The location of the phantomjs binary if you choose to use this browser driver.
    The macos version is included. See http://phantomjs.org/download.html

- sakai.distro

    The distribution of Sakai you are using.  Validate strings are "ani" and "sakai".  There are a few UI
    differences in the ani distribution that this property will toggle, allowing the suite to be run against multiple
    distributions.

- sakai.version

    The version of Sakai you are testing against.  This isn't used yet, but one day it will be.

- sakai.admin.eid

    An eid for an admin user on the instance you are testing.  Defaults to "admin"

- sakai.admin.pwd

    The password for the admin user on the instance you are testing.  Defaults to "admin"

- google.email

	The account that is used for GoogleDocs in the Resources tool. If testing GoogleDocs this needs to be added

- google.pw

	The password for the google account. If testing GoogleDocs this needs to be added

- random.user.email

	This can be used to specify the email address to be used for bulk adding of users

- sakai.skin

	The skin that you are testing on. Valid strings are "neo" and "xsl". There are a few UI differences that this property will toggle. Defaults to "neo"

- sakai.client

	The client that is being tested against. Different clients may have different settings that cause UI differences and this setting will toggle those. Defaults to "nightly".

- driver.timeout

	The implicit timeout of the WebDriver. If it is looking for an element and cannot find it, the WebDriver will time out after this amount of time. Defaults to "5"



Example invocation with some properties...

    mvn -Dtarget.browser=firefox -Dtarget.server=https://nightly.cle.rsmart.com/portal

When using Jenkins to send system properties to the test suite use the -DargLine option in the "Goals and options" field

    mvn -DargLine="-Dtarget.browser=firefox" test

![ScreenShot](https://raw.github.com/johntbush/sakai-test-suite/master/img/maven_jenkins.png)


Tips and Tricks
---------------

In Sakai because of the iframes, make sure after you click on a tool, to switch into the iframe

      def gotoTool(toolName : String) {
        click on linkText(toolName)
        switch to frame(0)
      }

Get familiar with Selenium DSL, read http://www.scalatest.org/user_guide/using_selenium

In addition to id and name, you can use the following approaches to lookup elements,
just as you can do with Selenium's org.openqa.selenium.By class:

- xpath
- className
- cssSelector
- linkText
- partialLinkText
- tagName

Typically, you are going to be using your browser's Inspect feature to grab ids.  But that doesn't always work, because
there isn't one or its dynamic.  You can install Selenium IDE into firefox and then record what you are doing and look
at the selection items it figures out, this is a good way to learn some new ideas until you get good at it.

Sakai lack of consistency regarding the use of buttons can be daunting to get it right.  I've found that using the
label of the button in a cssSelector works most of the time

    click on cssSelector("[value=Continue]")

If all else fails, xpath is the most powerful tool you have to locate elements. [Xpath Checker](https://code.google.com/p/xpathchecker/) is a useful Firefox addon that interactively displays
the results of an xpath expression to ensure you are locating the correct element.
Used in combination with [this reference for sorting out xpath and css syntax for indexing elements](https://www.simple-talk.com/dotnet/.net-framework/xpath,-css,-dom-and-selenium-the-rosetta-stone/)
you should be able to locate any element you need.

Running Firefox/Chrome Headless in AWS via Jenkins
--------------------------------------------------

Getting firefox to run in AWS is not so obvious, follow this guide:
http://itsallabtamil.blogspot.com/2013/02/setting-up-chrome-firefox-ec2-selenium-java.html

I setup Xfvb to run as a service and then set my display for jenkins following this:
http://www.jpalomaki.fi/?p=403

Once you get that setup, install the cucumber reporting plugin for jenkins:
http://www.masterthought.net/section/cucumber-reporting





