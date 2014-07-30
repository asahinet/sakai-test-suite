package com.anisakai.test.pageobjects

import scala.io.Source

object AddTool extends AddTool

class AddTool extends Page {
  var tools = Source.fromFile("tools.txt").getLines.toList
}
