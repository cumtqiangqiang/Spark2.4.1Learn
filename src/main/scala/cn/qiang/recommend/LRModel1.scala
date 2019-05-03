package cn.qiang.recommend

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

object LRModel1 {
  def main(args: Array[String]): Unit = {
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
    println(lrModel.coefficientMatrix)

  }
}
