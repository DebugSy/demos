package com.mapreduce.demo.combinefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class CombineFileDriver{

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration, "Combine File");

		job.setJarByClass(CombineFileDriver.class);

		job.setMapperClass(CombineFileMapper.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		job.setInputFormatClass(CombineFileInputFormat.class);

		FileInputFormat.setInputPaths(job, new Path("E:/tmp/develop/combineFile/input"));
		FileOutputFormat.setOutputPath(job, new Path("E:/tmp/develop/combineFile/output"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);
	}

}
