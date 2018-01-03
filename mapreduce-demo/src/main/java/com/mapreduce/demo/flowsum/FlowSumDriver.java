package com.mapreduce.demo.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/3.
 */
public class FlowSumDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration configuration = new Configuration();

		Job job = Job.getInstance(configuration, "flowSum");

		job.setJarByClass(FlowSumDriver.class);

		job.setMapperClass(FlowSumMapper.class);
		job.setReducerClass(FlowSumReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);

		FileInputFormat.setInputPaths(job, new Path("file:///E:/tmp/develop/flowsum/input/flow.log"));
		FileOutputFormat.setOutputPath(job, new Path("file:///E:/tmp/develop/flowsum/output"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);
	}

}
