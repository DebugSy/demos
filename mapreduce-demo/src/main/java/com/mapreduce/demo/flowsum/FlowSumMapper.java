package com.mapreduce.demo.flowsum;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/3.
 */
public class FlowSumMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		System.out.println("mapper key : " + key);
		String line = value.toString();
		String[] splits = line.split("\t");
		String phone = splits[1];
		long upFlow = Long.parseLong(splits[splits.length - 3]);
		long dFlow = Long.parseLong(splits[splits.length - 2]);
		FlowBean flowBean = new FlowBean(upFlow, dFlow);
		context.write(new Text(phone), flowBean);
	}
}
