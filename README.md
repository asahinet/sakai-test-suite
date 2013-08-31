Sakai Test Suite
======================

This project's aim it to create a regression suite that runs against a community or ANI (formerly rSmart)
version of Sakai. It performs actions via direct browser interactions.

The tests are feature or behavior driven (BDD) using cucumber.  These tests are then implemented in scala using the [cucumber-jvm](https://github.com/cucumber/cucumber-jvm)
via cucumber-scala and cucumber-junit.  The ScalaTest library is used for the [Selenium DSL](http://www.scalatest.org/user_guide/using_selenium),
which ends up pushing the Selenium WebDriver, but in a more natural and simply way.  The Selenium DSL will let you get directly at the
[Selenium WebDriver](http://selenium.googlecode.com/svn/trunk/docs/api/java/index.html) object when you need more power.

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

Next you'll need to create your scala test. These will extra the variables from your cucumber text file
and delegate calls to your page object.

    class LoginTest extends ScalaDsl with EN {
       lazy val gatewayPage = new GatewayPage()


       Given( """^I am on the '(.+)' gateway page$""") {
         (url: String) =>
           gatewayPage.navigateToPage(url)
       }

       When( """^I enter '(.+)' for user""") {
         (eid: String) =>
           gatewayPage.enterEid(eid)
       }

       When( """^I enter '(.+)' for password""") {
         (password: String) =>
           gatewayPage.enterPassword(password)
       }

       When( """^I click the Login button""") {
         gatewayPage.login();
       }


       Then( """^I should see my workspace""") {
           assertTrue(gatewayPage.isMyWorkspace())
       }

       After() {
         gatewayPage.webDriver.quit();
       }
     }


If you simply run the test before creating the Scala file, the output from junit will actually tell you what
you method will look like, then you can simply copy and paste that in, and address the dynamic parts and implement.

For example, you would see something like this

     You can implement missing steps with the snippets below:

     Given("""^I am logged on as 'admin'$"""){ () =>
       //// Express the Regexp above with the code you wish you had
       throw new PendingException()
     }

### Create Page Objects

The idea of the page object it to encapsulate the actual WebDriver logic into one place.  In the beginning it seems
like a waste of time and business work, but over time you will understand the advantage of this.  The Selenium code
end up getting tied to xpath, or cssSelector, or other text on the page.  This things can change and break your tests.
By isolating this stuff you shield you code base from impact of these changes.  Also, this aids with reuse as your test
suite grows.  Luckily in Scala we have traits, which means we can finally do multiple inheritance.  It makes reuse of
Page Object super easy.

    class GatewayPage extends Page{
       def eid: TextField = textField("eid");
       def password: PasswordField = pwdField("pw");

       def login() {
         submit();
       }

       def enterEid(eid: String) {
         this.eid.value = eid;
       }

       def enterPassword(password: String) {

         this.password.value = password;
       }

       def isMyWorkspace() : Boolean = {
         return webDriver.findElementByClassName("siteTitle").getText().startsWith("My Workspace");
       }
    }

Running the Tests
-----------------

### Use maven

 mvn install

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

    The distribution of Sakai you are using.  Validate strings are "ani", "rsmart", "community".  There are a few UI
    differences in the ani distribution that this property will toggle, allowing the suite to be run against multiple
    distributions.

- sakai.version

    The version of Sakai you are testing against.  This isn't used yet, but one day it will be.


Example invocation with some properties...

-Dtarget.browser=firefox -Dtarget.server=https://nightly.cle.rsmart.com/portal


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

This is a good reference for sorting out xpath and css syntax for indexing elements
https://www.simple-talk.com/dotnet/.net-framework/xpath,-css,-dom-and-selenium-the-rosetta-stone/

Sakai lack of consistency regarding the use of buttons can be daunting to get it right.  I've found that using the
label of the button in a cssSelector works most of the time

    click on cssSelector("[value=Continue]")

Running Firefox/Chrome Headless in AWS via Jenkins
--------------------------------------------------

Getting firefox to run in AWS is not so obvious, follow this guide:
http://itsallabtamil.blogspot.com/2013/02/setting-up-chrome-firefox-ec2-selenium-java.html

I setup Xfvb to run as a service and then set my display for jenkins following this:
http://www.jpalomaki.fi/?p=403

Once you get that setup, install the cucumber reporting plugin for jenkins:
http://www.masterthought.net/section/cucumber-reporting

