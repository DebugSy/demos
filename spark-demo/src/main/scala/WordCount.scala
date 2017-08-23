import java.util
import java.util.{Arrays, List}

import org.apache.spark.api.java.{JavaPairRDD, JavaRDD}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by DebugSy on 2017/8/22.
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("JavaWordCount").master("local").getOrCreate

    val lines: RDD[String] = spark.read.textFile(args(0)).rdd

    val words: RDD[String] = lines.flatMap(line => line.split(" "))

    val pairs = words.map(word => (word, 1))

    val wordCounts = pairs.reduceByKey(_+_)

    wordCounts.foreach(wordNumberPair => println(wordNumberPair._1 + " : " + wordNumberPair._2))

    spark.stop()

  }

}
