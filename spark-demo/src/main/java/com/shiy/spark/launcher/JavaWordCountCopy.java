package com.shiy.spark.launcher;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class JavaWordCountCopy {

    public static void main(String[] args) {


        SparkSession sparkSession = SparkSession.builder().appName("Java World Count").master("local").getOrCreate();

        SparkContext sc = sparkSession.sparkContext();

        JavaSparkContext javaSparkContext = new JavaSparkContext(sc);

        List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        JavaRDD<Integer> javaRDD = javaSparkContext.parallelize(array);

        JavaRDD<Integer> javaRDD1 = javaRDD.sortBy(v1 -> v1, false, 4);

        List<Integer> collect = javaRDD1.collect();

        for (Integer i : collect){
            System.out.println(i);
        }

    }

}
