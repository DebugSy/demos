package com.mapreduce.demo.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/3.
 */
public class FlowCountSortApp {

	static class FlowCountSortMapper extends Mapper<LongWritable, Text, FlowCountSortBean, Text> {
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			FlowCountSortBean bean = new FlowCountSortBean();
			String line = value.toString();
			String[] splits = line.split("\t");
			bean.setUpFlow(Long.parseLong(splits[1]));
			bean.setdFlow(Long.parseLong(splits[2]));
			bean.setSumFlow(Long.parseLong(splits[3]));
			context.write(bean, new Text(splits[0]));
		}
	}

	static class FlowCountSortReducer extends Reducer<FlowCountSortBean, Text, Text, FlowCountSortBean>{
		@Override
		protected void reduce(FlowCountSortBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			Text value = values.iterator().next();
			context.write(value, key);
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();

		Job job = Job.getInstance(configuration, "flowCountSort");

		job.setJarByClass(FlowCountSortApp.class);

		job.setMapOutputKeyClass(FlowCountSortBean.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowCountSortBean.class);

		job.setMapperClass(FlowCountSortMapper.class);
		job.setReducerClass(FlowCountSortReducer.class);

		FileInputFormat.setInputPaths(job, new Path("file:///E:/tmp/develop/flowsum/input/flowSort.log"));
		FileOutputFormat.setOutputPath(job, new Path("file:///E:/tmp/develop/flowsum/sort_output2"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);
	}

}
