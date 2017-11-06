package com.shiy.spark.launcher;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by DebugSy on 2017/9/15.
 */
public class JavaWordCount {

	private static final Pattern SPACE = Pattern.compile(" |,|\"");

	public static void main(String[] args) {

//		if (args.length <  1){
//			throw new IllegalArgumentException("请指定读取的文件位置");
//		}

		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("WordCount");
		sparkConf.setMaster("local");

		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		String filePath = "/tmp/shiy/data.csv";

		JavaRDD<String> lines = sc.textFile(filePath);

		JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

		JavaPairRDD<String, Integer> javaPairRDD = words.mapToPair(s -> new Tuple2<>(s, 1));

		JavaPairRDD<String, Integer> wordCounts = javaPairRDD.reduceByKey(((v1, v2) -> v1 + v2));

		wordCounts.foreach(wordCount -> System.out.println(wordCount._1 + "------" + wordCount._2 + " times."));

	}

}
