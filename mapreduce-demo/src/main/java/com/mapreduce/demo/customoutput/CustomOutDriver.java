package com.mapreduce.demo.customoutput;

import com.mapreduce.demo.groupingCompare.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class CustomOutDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration, "Custom OutputFormat");

		job.setJarByClass(CustomOutDriver.class);

		job.setMapperClass(CustomOutMapper.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setOutputFormatClass(CustomFileOutputFormat.class);

		Path inpputPath = new Path("E:/tmp/develop/customOutput/input");
		Path outputPath = new Path("E:/tmp/develop/customOutput/output");
		FileInputFormat.setInputPaths(job, inpputPath);
		//虽然自定义了outputFormat,这里也要指定输出路径，因为还有_success等文件
		FileOutputFormat.setOutputPath(job, outputPath);

		//不需要reduce
		job.setNumReduceTasks(3);

		boolean completion = job.waitForCompletion(true);

		if (!completion){
			File outputDir = new File(outputPath.toString());
			outputDir.deleteOnExit();
		}
		System.exit(completion?0:1);
	}

}
