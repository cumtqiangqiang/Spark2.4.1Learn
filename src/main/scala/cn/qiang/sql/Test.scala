package cn.qiang.sql

import java.io.File


object Test {
  def main(args: Array[String]): Unit = {
    val dir = new File("..")
    val filePath = dir.getAbsolutePath
    println(filePath)
  }
}
