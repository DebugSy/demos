package com.mapreduce.demo.groupingCompare;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class GroupingPartitioner extends Partitioner<OrderBean, NullWritable> {
	@Override
	public int getPartition(OrderBean orderBean, NullWritable nullWritable, int numPartitions) {
		String orderId = orderBean.getId();
		return (orderId.hashCode() & Integer.MAX_VALUE) % numPartitions;
	}
}
