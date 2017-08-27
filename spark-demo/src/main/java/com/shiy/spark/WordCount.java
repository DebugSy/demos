package com.shiy.spark;

import org.apache.commons.lang.ArrayUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by DebugSy on 2017/8/24.
 */
public class WordCount {

	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) {

		SparkSession sparkSession = new SparkSession.Builder().appName("word count").master("local").getOrCreate();

		JavaRDD<String> lines = sparkSession.read().textFile("E:/tmp/data.txt").toJavaRDD();

		JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

//		words.mapToPair()

	}

}
