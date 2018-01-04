package com.mapreduce.demo.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/4.
 */
public class CompressDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration configuration = new Configuration();

		/**
		 * 	配置map支持压缩
		 *
		 */
		configuration.setBoolean(Job.MAP_OUTPUT_COMPRESS, true);
		configuration.setClass(Job.MAP_OUTPUT_COMPRESS_CODEC, GzipCodec.class, CompressionCodec.class);

		 /**
		 * 	配置reduce支持mapreduce压缩
		 * 	mapreduce.output.fileoutputformat.compress=false
		 *	mapreduce.output.fileoutputformat.compress.codec=org.apache.hadoop.io.compress.DefaultCodec
		 *	mapreduce.output.fileoutputformat.compress.type=RECORD
		 */
//		configuration.setBoolean(Job.fil, false);
//		configuration.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.DefaultCodec");
//		configuration.set("mapreduce.output.fileoutputformat.compress.type", "RECORD");
		Job job = Job.getInstance(configuration);

		job.setMapperClass(CompressMapper.class);
		job.setReducerClass(CompressReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.setInputPaths(job, new Path("file:///E:/tmp/develop/wordcount/input/data.txt"));

		FileOutputFormat.setOutputPath(job, new Path("file:///E:/tmp/develop/wordcount/output2"));

		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);

	}

}
