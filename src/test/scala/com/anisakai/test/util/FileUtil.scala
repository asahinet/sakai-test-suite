package com.anisakai.test.util

import scala.collection.JavaConversions._
import java.util.Date
import com.github.javafaker.Faker
import java.io.FileOutputStream

/**
 * Created with IntelliJ IDEA.
 * User: jbush
 * Date: 10/22/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
object FileUtil extends FileUtil

class FileUtil {
  val faker: Faker = new Faker()

  def createRandomTextFile(numOfParagraphs: Int): String = {
    val sb = new StringBuilder()
    faker.paragraphs(numOfParagraphs).toList.foreach{ sb.append(_) }
    createTextFile(sb.toString())
  }

  def createTempFile(fileName: String, ext: String, content: Array[Byte]): String = {
    val file = java.io.File.createTempFile(fileName, ext)
    val out = new FileOutputStream(file)
    out.write(content)
    out.close()
    return file.getAbsolutePath

  }

  def createTempFile(fileName: String, ext: String, content: String): String = {
    val file = java.io.File.createTempFile(fileName, ext)
    val out = new java.io.FileWriter(file)
    out.write(content)
    out.close()
    return file.getAbsolutePath

  }

  def createBinaryFile(content: Array[Byte]): String = {
    return createTempFile(String.valueOf(new Date().getTime()), ".txt", content)
  }


  def createTextFile(content: String): String = {
    return createTempFile(String.valueOf(new Date().getTime()), ".txt", content)
  }
}
