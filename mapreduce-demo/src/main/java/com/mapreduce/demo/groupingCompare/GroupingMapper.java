package com.mapreduce.demo.groupingCompare;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class GroupingMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

	private OrderBean orderBean = new OrderBean();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] split = line.split(",");
		orderBean.setId(split[0]);
		orderBean.setAmount(Double.parseDouble(split[2]));
		context.write(orderBean, NullWritable.get());
	}

}
