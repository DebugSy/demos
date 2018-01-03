package com.mapreduce.demo.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2017/12/22.
 */
public class WordcountDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		if (args == null || args.length == 0) {
			args = new String[2];
			args[0] = "file:///E:/tmp/develop/wordcount/input/data.txt";
			args[1] = "file:///E:/tmp/develop/wordcount/output";
//			args[0] = "hdfs://sandbox.hortonworks.com:8020/tmp/shiy/develop/data/wordcount.txt";
//			args[1] = "hdfs://sandbox.hortonworks.com:8020/tmp/shiy/develop/data/output";
		}

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "WordCount");

		//指定本程序的jar包所在的本地路径
//		job.setJar("/home/hadoop/wordcount.jar");
		job.setJarByClass(WordcountDriver.class);

		//指定本业务job要使用的mapper和reducer业务类
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordcountReducer.class);

		//指定mapper输出数据的kv类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		//指定最终输出数据的kv类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		//指定job的原始输入文件的目录
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		//指定job的输出结果所在目录
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		//将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
		/*job.submit();*/
		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);


	}

}
