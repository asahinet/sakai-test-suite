package com.anisakai.test.pageobjects

import org.openqa.selenium.By
import java.util.Calendar
import scala.util.Random
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: gareth
 * Date: 10/2/13
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
object AssignmentTool extends AssignmentTool

class AssignmentTool extends Page {
  val cal = Calendar.getInstance
  cal.add(Calendar.DATE, 1)

  def gotoAdd() {
    if (webDriver.findElement(By.xpath("//*[@class = 'current']")).getText != "Assignment List")
      click on linkText("Assignment List")
    click on linkText("Add")
    switch to defaultContent
    switch to frame(0)
  }

  def assignment() : String ={
    return assignment(false, false)
  }

  def assignment(turnItIn: Boolean, correct: Boolean) : String = {
    val assignmentTitle = faker.letterify("?????? ???????")
    val day = cal.get(Calendar.DAY_OF_MONTH) - 1
    val dueday = cal.get(Calendar.DAY_OF_MONTH) + 1
    val month = cal.get(Calendar.MONTH) + 1
    val year = cal.get(Calendar.YEAR)

    val rand = new Random();
    val hour = rand.nextInt(12) + 1;
    var am_pm = "AM"
    textField("new_assignment_title").value = assignmentTitle
    //Set open date
    singleSel("new_assignment_openmonth").value = month.toString()
    singleSel("new_assignment_openday").value = day.toString()
    singleSel("new_assignment_openyear").value = year.toString()
    singleSel("new_assignment_openhour").value = hour.toString()
    singleSel("new_assignment_openmin").value = "0"
    singleSel("new_assignment_openampm").value = am_pm
    //Set due date
    singleSel("new_assignment_duemonth").value = month.toString()
    singleSel("new_assignment_dueday").value = dueday.toString()
    singleSel("new_assignment_dueyear").value = year.toString()
    singleSel("new_assignment_duehour").value = hour.toString()
    singleSel("new_assignment_duemin").value = "0"
    singleSel("new_assignment_dueampm").value = am_pm
    //Set close date
    singleSel("new_assignment_closemonth").value = month.toString()
    singleSel("new_assignment_closeday").value = dueday.toString()
    singleSel("new_assignment_closeyear").value = year.toString()
    singleSel("new_assignment_closehour").value = hour.toString()
    singleSel("new_assignment_closemin").value = "0"
    singleSel("new_assignment_closeampm").value = am_pm
    if (correct) {
      singleSel("new_assignment_submission_type").value = "5"
    } else {
      singleSel("new_assignment_submission_type").value = "3"
    }

    singleSel("new_assignment_grade_type").value = "3"
    if (textField("new_assignment_grade_points").isEnabled) {
      textField("new_assignment_grade_points").value = "100"
      click on name("new_assignment_add_to_gradebook")
    }

    checkbox("new_assignment_check_add_due_date").select()
    if (turnItIn)
      checkbox("new_assignment_use_review_service").select()
    Portal.richTextEditor()
    click on name("post")

    eventually (switch to defaultContent)
    switch to frame(0)

    return assignmentTitle
  }

  def isAdded(assignmentTitle : String) : Boolean = {
    click on linkText(assignmentTitle)
    switch to defaultContent
    switch to frame(0)
    return webDriver.findElement(By.className("discTria")).getText().contains(assignmentTitle)
  }

  def openAssignment(assignmentTitle: String) {
    click on linkText(assignmentTitle)
    eventually (switch to defaultContent)
    switch to frame(0)
  }

  def submitAssignment() : Boolean = {
    Portal.richTextEditor()
    click on name("post")
    return xpath("//*[@class='success']").element.isDisplayed
  }

  def gotoEdit(assignmentTitle : String) : Array[String] = {
    click on xpath("//a[.='Edit "+assignmentTitle+"']")
    switch to defaultContent
    switch to frame(0)

    var current = new Array[String](2)
    current(0) = textField("new_assignment_title").value
    current(1) = singleSel("new_assignment_dueday").value

    return current
  }

  def edit(current : Array[String]) : String = {
    val newTitle = faker.letterify("?????? ???????")
    textField("new_assignment_title").value = newTitle
    singleSel("new_assignment_dueday").value = (current(1).toInt + 1).toString
    singleSel("new_assignment_closeday").value = (current(1).toInt + 1).toString
    click on name("post")
    return newTitle
  }

  def verifyEdit(assignmentTitle : String, current : Array[String]) : Boolean = {
    click on xpath("//a[.='Edit "+assignmentTitle+"']")
    switch to defaultContent
    switch to frame(0)
    if (textField("new_assignment_title").value != current(0) && singleSel("new_assignment_dueday").value != current(1) && singleSel("new_assignment_closeday").value != current(1)) {
      return true
    } else {
      return false
    }
  }

  def deleteAssignment(assignmentTitle : String) {
    checkbox(xpath("//label[@class='skip' and contains(text(),'"+ assignmentTitle +"')]/preceding-sibling::input[@type='checkbox']")).select()
    click on name("eventSubmit_doDelete_confirm_assignment")
    click on name("eventSubmit_doDelete_assignment")
  }

  def removed(assignmentTitle : String) : Boolean = {
    if (webDriver.findElements(By.linkText(assignmentTitle)).isEmpty())
      return true
    else
      return false
  }

}
