package com.mapreduce.demo.sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by DebugSy on 2018/1/12.
 */
public class SequenceFileDriver extends Configured implements Tool {

	/**
	 * Execute the command with the given arguments.
	 *
	 * @param args command specific arguments.
	 * @return exit code.
	 * @throws Exception
	 */
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"combine small files to sequencefile");
		job.setJarByClass(SequenceFileDriver.class);

		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		args=new String[]{"E:/tmp/develop/sequencefile/input","E:/tmp/develop/sequencefile/output"};
		SequenceFileDriver driver = new SequenceFileDriver();
		int exitCode = ToolRunner.run(driver, args);
		System.exit(exitCode);

	}

}
