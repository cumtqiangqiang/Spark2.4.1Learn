package cn.qiang.streaming

import org.apache.log4j.lf5.LogLevel
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.log4j.{ Level, Logger }

object Streaming {
  def main(args: Array[String]): Unit = {
    val f = (a: String, b: String) => a + "_" + b
    val conf = new SparkConf().setMaster("local[2]")
      .setAppName("StreamingTest")
    val ssc = new StreamingContext(conf, Seconds(1))
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val socketDstream = ssc.socketTextStream("localhost", 9999)

    val words = socketDstream.flatMap(line => line.split(" "))
    //    val windowWords = words.window(Seconds(3),Seconds(1))
    val windowWords = words.reduceByWindow(f(_,_), Seconds(3), Seconds(1))

    windowWords.print()
    ssc.start()
    ssc.awaitTermination()


  }

}

