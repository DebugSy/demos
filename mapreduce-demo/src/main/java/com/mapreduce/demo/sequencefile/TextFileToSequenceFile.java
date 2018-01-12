package com.mapreduce.demo.sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class TextFileToSequenceFile {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);

		job.setJarByClass(TextFileToSequenceFile.class);

//		job.setMapperClass(TextFileMapper.class);
//
//		job.setReducerClass(TextFileReducer.class);

//		job.setInputFormatClass(FileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileInputFormat.setInputPaths(job, new Path("E:/tmp/develop/sequencefile/input"));
		FileOutputFormat.setOutputPath(job, new Path("E:/tmp/develop/sequencefile/output"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:-1);

	}

}
