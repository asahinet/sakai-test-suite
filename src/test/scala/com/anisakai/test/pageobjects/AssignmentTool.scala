package com.anisakai.test.pageobjects

import java.text.SimpleDateFormat
import java.util.Calendar
import com.anisakai.test.Config
import org.openqa.selenium.{ElementNotVisibleException, By}
import org.scalatest.exceptions.TestFailedDueToTimeoutException

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/2/13
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
object AssignmentTool extends AssignmentTool

class AssignmentTool extends Page {
  val isTen : Boolean = Config.sakaiVersion.startsWith("10.") //Sakai 10.x
  val cal = Calendar.getInstance

  def goToAdd {
    Portal.xslFrameOne
    click on linkText("Add")
    Portal.getToFrameZero
  }


  // The turnItIn and correct booleans relate to turn it in assignments.
  // If you just want to create a regular assignment using the assignments
  // tool call this method parameterless
  def assignment(turnItIn: Boolean = false, correct: Boolean = false): String = {
    Portal.xslFrameOne
    val dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
    val dayFormat = new SimpleDateFormat("dd")
    val monthFormat = new SimpleDateFormat("MM")
    val yearFormat = new SimpleDateFormat("yyyy")
    val hourFormat = new SimpleDateFormat("hh")
    val minFormat = new SimpleDateFormat("mm")
    val ampmFormat = new SimpleDateFormat("a")
    cal.add(Calendar.HOUR, -2)
    val today = cal.getTime
    cal.add(Calendar.DAY_OF_YEAR, 3)
    val tomorrow = cal.getTime

    val assignmentTitle = faker.letterify("?????? ???????")
    val openDate = dateFormat.format(today).toLowerCase
    val dueDate = dateFormat.format(tomorrow).toLowerCase
    val openDay = Integer.parseInt(dayFormat.format(today))
    val dueDay = Integer.parseInt(dayFormat.format(tomorrow))
    val openMonth = Integer.parseInt(monthFormat.format(today))
    val dueMonth = Integer.parseInt(monthFormat.format(tomorrow))
    val openYear = Integer.parseInt(yearFormat.format(today))
    val dueYear = Integer.parseInt(yearFormat.format(tomorrow))
    val openHour = Integer.parseInt(hourFormat.format(today))
    val dueHour = Integer.parseInt(hourFormat.format(tomorrow))
    var openMin = Integer.parseInt(minFormat.format(today))
    var dueMin = Integer.parseInt(minFormat.format(tomorrow))
    val amOrPm = ampmFormat.format(today).toLowerCase

    // Round down to nearest factor of 5
    dueMin -= dueMin % 5
    openMin -= openMin % 5

    // Time can't be less than zero
    if (dueMin < 0) {
      dueMin = 0
      openMin = 0
    }

    textField("new_assignment_title").value = assignmentTitle

      // Set open date
    if (!isTen) {
      singleSel("new_assignment_openmonth").value = openMonth.toString
      singleSel("new_assignment_openday").value = openDay.toString
      singleSel("new_assignment_openyear").value = openYear.toString
      singleSel("new_assignment_openhour").value = openHour.toString
      singleSel("new_assignment_openmin").value = openMin.toString
      singleSel("new_assignment_openampm").value = amOrPm.toUpperCase
      // Set due date
      singleSel("new_assignment_duemonth").value = dueMonth.toString
      singleSel("new_assignment_dueday").value = dueDay.toString
      singleSel("new_assignment_dueyear").value = dueYear.toString
      singleSel("new_assignment_duehour").value = dueHour.toString
      singleSel("new_assignment_duemin").value = dueMin.toString
      singleSel("new_assignment_dueampm").value = amOrPm.toUpperCase
      // Set close date
      singleSel("new_assignment_closemonth").value = dueMonth.toString
      singleSel("new_assignment_closeday").value = dueDay.toString
      singleSel("new_assignment_closeyear").value = dueYear.toString
      singleSel("new_assignment_closehour").value = dueHour.toString
      singleSel("new_assignment_closemin").value = dueMin.toString
      singleSel("new_assignment_closeampm").value = amOrPm.toUpperCase
    } else { // Sakai 10
      click on id("opendate")
      try {
        eventually(click on xpath("//button[.='Now']"))
      } catch {
        case visible: ElementNotVisibleException => println("Not to worry, accept the currently entered date and move along")
        case timeout: TestFailedDueToTimeoutException => println("timeout")
      }
      click on xpath("//button[.='Done']")
      // accept default due and close dates
    }

    if (correct) { // Turn it in
      singleSel("new_assignment_submission_type").value = "5"
    } else {
      singleSel("new_assignment_submission_type").value = "3"
    }

    singleSel("new_assignment_grade_type").value = "3"
    if (textField("new_assignment_grade_points").isEnabled) {
      textField("new_assignment_grade_points").value = "100"
      if (!isTen) click on name("new_assignment_add_to_gradebook")
    }

    checkbox("new_assignment_check_add_due_date").select
    if (turnItIn)
      checkbox("new_assignment_use_review_service").select
    Portal.richTextEditor
    Portal.xslFrameOne
    click on name("post")

    eventually(switch to defaultContent)
    switch to frame(0)

    assignmentTitle
  }

  def isViewable(assignmentTitle: String): Boolean = {
    click on linkText(assignmentTitle)
    Portal.xslFrameOne
    webDriver.findElement(By.className("discTria")).getText.contains(assignmentTitle)
  }

  def isAdded(eventTitle: String): Boolean = {
    Portal.xslFrameOne
    linkText(eventTitle).element.isDisplayed
  }

  def openAssignment(assignmentTitle: String) {
    switch to defaultContent
    click on xpath("//a[@title='Reset']")
    eventually(Portal.xslFrameOne)
    click on linkText(assignmentTitle)
    eventually(switch to defaultContent)
    switch to frame(0)
  }

  def studentSubmitAssignment: Boolean = {
    Portal.xslFrameOne
    Portal.richTextEditor
    Portal.xslFrameOne
    click on name("post")
    xpath("//*[@class='success']").element.isDisplayed
  }

  def goToEdit(assignmentTitle: String) : Array[String] = {
    Portal.xslFrameOne
    click on xpath("//a[.='Edit " + assignmentTitle + "']")
    Portal.xslFrameOne

    val current = new Array[String](2)
    if (!isTen) {
      current(0) = textField("new_assignment_title").value
      current(1) = singleSel("new_assignment_dueday").value
    } else {
      current(0) = textField("new_assignment_title").value
    }
    current
  }


  def edit: String = {
    cal.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrow = cal.getTime
    val dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
    val dayFormat = new SimpleDateFormat("dd")
    val newTitle = faker.letterify("?????? ???????")
    val dueDate = dateFormat.format(tomorrow).toLowerCase
    val dueDay = Integer.parseInt(dayFormat.format(tomorrow))
    textField("new_assignment_title").value = newTitle
    if (!isTen) {
      singleSel("new_assignment_dueday").value = dueDay.toString
      singleSel("new_assignment_closeday").value = dueDay.toString
    } else {
      val due = id("duedate").webElement
      val close = id("closedate").webElement
      due.clear
      due.sendKeys(dueDate)
      close.clear
      close.sendKeys(dueDate)
    }
    click on name("post")
    newTitle
  }

  def verifyEdit(assignmentTitle: String, oldData: Array[String]): Boolean = {
    click on xpath("//a[.='Edit " + assignmentTitle + "']")
    Portal.xslFrameOne
    if (!isTen
      && textField("new_assignment_title").value != oldData(0)
      && singleSel("new_assignment_dueday").value != oldData(1)
      && singleSel("new_assignment_closeday").value != oldData(1)) {
      true
    } else if (isTen && textField("new_assignment_title").value != oldData(0)) {
      true
    } else {
      false
    }
  }

  def deleteAssignment(assignmentTitle: String) {
    Portal.xslFrameOne
    eventually(checkbox(xpath("//label[@class='skip' and contains(text(),'" + assignmentTitle + "')]/preceding-sibling::input[@type='checkbox']")).select)
    click on name("eventSubmit_doDelete_confirm_assignment")
    click on name("eventSubmit_doDelete_assignment")
  }

  def removed(assignmentTitle: String): Boolean = !linkText(assignmentTitle).findElement(webDriver).isDefined

}
