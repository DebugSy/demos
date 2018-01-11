package com.mapreduce.demo.groupingCompare;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class GroupingDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration, "Grouping Compare");

		job.setJarByClass(GroupingDriver.class);

		job.setMapperClass(GroupingMapper.class);
		job.setReducerClass(GroupingReducer.class);

		job.setMapOutputKeyClass(OrderBean.class);
		job.setMapOutputValueClass(NullWritable.class);

		job.setOutputKeyClass(OrderBean.class);
		job.setOutputValueClass(NullWritable.class);

		//设置自定义分区器
		job.setPartitionerClass(GroupingPartitioner.class);

		job.setGroupingComparatorClass(GroupingCompare.class);

		Path inpputPath = new Path("E:/tmp/develop/groupingComparator/input");
		Path outputPath = new Path("E:/tmp/develop/groupingComparator/output");
		FileInputFormat.setInputPaths(job, inpputPath);
		FileOutputFormat.setOutputPath(job, outputPath);

		//reduce数要和order id一样
		job.setNumReduceTasks(3);

		boolean completion = job.waitForCompletion(true);

		if (!completion){
			File outputDir = new File(outputPath.toString());
			outputDir.deleteOnExit();
		}
		System.exit(completion?0:1);

	}

}
