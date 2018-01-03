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

		//mapreduce任务最好是不要一直new对象
		FlowCountSortBean bean = new FlowCountSortBean();
		Text v = new Text();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			String[] splits = line.split("\t");
			bean.set(Long.parseLong(splits[1]), Long.parseLong(splits[2]));
			v.set(splits[0]);
			context.write(bean, v);
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

		FileInputFormat.setInputPaths(job, new Path("file:///D:/tmp/develop/flowsum/input/flow2.log"));
		FileOutputFormat.setOutputPath(job, new Path("file:///D:/tmp/develop/flowsum/sort_output"));

		boolean completion = job.waitForCompletion(true);
		System.exit(completion?0:1);
	}

}
