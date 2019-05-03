package cn.qiang.recommend

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation

import scala.collection.mutable.ListBuffer

object Example1 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession.builder()
      .appName("Example1")
      .master("local")
      .getOrCreate()
    val list = List(1,2,3,4)
    import spark.implicits._
    val data = Seq(
      Vectors.sparse(4,Seq((0,1.0),(3,-2.0))),
      Vectors.dense(4.0,5.0,0.0,3.0),
      Vectors.dense(6.0,7.0,0.0,8.0),
      Vectors.sparse(4,Seq((0,9.0),(3,1.0)))
    )
    val df = data.map(Tuple1.apply).toDF("features")
//    val df1 = data.toDS("features")
    df.show()
    val Row(coff1:Matrix) = Correlation.corr(df,"features").head()
    println(s"Pearson correlation matrix:\n $coff1")

    val Row(coff2:Matrix) = Correlation.corr(df,"features","spearman").head()
    println(s"Spearman correlation matrix:\n $coff2")





  }
}
