package cn.qiang.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

object SparkSql {
  case class Student(name:String,age:Long)
  case class People(name:String,age:Long,weight:Int,address:String)
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession
      .builder()
      .appName("SparkSqlExample")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    val df = spark.read.json("./src/main/resources/student")

    df.groupBy("age").count().show()

    val caseClassDS = Seq(Student("qiang",22)).toDS()

//    caseClassDS.show()

    val primitiveDS = Seq((1,2,3),(2,3,4),(3,4,5)).toDS()
    val primitiveDF = primitiveDS.toDF("name","age","weight")
    primitiveDF.show()
    val  arr = primitiveDS.collect()
//    for (a <- arr){
//      println(a)
//    }

    val peopleDS = spark.read.json("./src/main/resources/student").as[Student]
    peopleDS.show()
    println("--------------pDF---------------------")
    val  pDF = peopleDS.toDF("name","age")
    pDF.show()


    val peopleDF = spark.sparkContext
      .textFile("./src/main/resources/people")
      .map(line=>line.split(","))
      .map(t=>People(t(0),t(1).trim().toInt,t(2).trim.toInt,t(3)))
      .toDF()
    peopleDF.createOrReplaceTempView("people")
    val tenDF = spark.sql("SELECT *FROM people WHERE age BETWEEN 13 AND 19 ")
    tenDF.show()



  }

}
