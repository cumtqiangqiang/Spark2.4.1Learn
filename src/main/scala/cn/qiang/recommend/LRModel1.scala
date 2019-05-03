package cn.qiang.recommend

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

object LRModel1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession.builder()
      .appName("LRModel1")
      .master("local")
      .getOrCreate()
    val training = spark.read.format("libsvm")
      .load("./src/main/resources/sample_libsvm_data")
    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)
    val lrModel = lr.fit(training)
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")
    val mlr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)
      .setFamily("multinomial")

    val mlrModel = mlr.fit(training)
    println(s"Multinomial coefficients: ${mlrModel.coefficientMatrix}")
    println(s"Multinomial intercepts: ${mlrModel.interceptVector}")


  }
}
