package com.mapreduce.demo.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * Created by DebugSy on 2018/1/3.
 */
public class FlowSumPartitioner extends Partitioner<Text, FlowBean> {

	private static HashMap<String, Integer> partitions = new HashMap<>();
	static {
		partitions.put("134", 0);
		partitions.put("135", 1);
		partitions.put("136", 2);
		partitions.put("137", 3);
		partitions.put("138", 4);
		partitions.put("139", 5);
	}

	@Override
	public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
		String key = text.toString();
		String partitionKey = key.substring(0, 3);
		return partitions.get(partitionKey)==null?6:partitions.get(partitionKey);
	}
}
