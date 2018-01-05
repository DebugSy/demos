package com.mapreduce.demo.join.rjoin.low;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/4.
 */
public class RJoinDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration configuration = new Configuration();

		Job job = Job.getInstance(configuration, "RJoin Demo");

		job.setJarByClass(RJoinDriver.class);

		job.setMapperClass(RJoinMapper.class);
		job.setReducerClass(RJoinReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(InfoBean.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(InfoBean.class);

		TextInputFormat.addInputPath(job, new Path("file:///E:/tmp/develop/rjoin/input"));
		TextOutputFormat.setOutputPath(job, new Path("file:///E:/tmp/develop/rjoin/output"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);

	}

}
