package com.mapreduce.demo.combinefile.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class CombineDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration, "mapreduce combine file");

		job.setJarByClass(CombineDriver.class);

		job.setInputFormatClass(CombineInputFormat.class);

		FileInputFormat.setInputPaths(job, new Path("E:/tmp/develop/combineFile/input"));
		FileOutputFormat.setOutputPath(job, new Path("E:/tmp/develop/combineFile/output2"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);
	}

}
