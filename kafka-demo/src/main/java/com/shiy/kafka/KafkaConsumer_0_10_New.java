package com.shiy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by DebugSy on 2017/8/30.
 */
public class KafkaConsumer_0_10_New {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test01");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		String topic = "test01";
		List<PartitionInfo> partitions = consumer.partitionsFor(topic);

		if (partitions == null || partitions.isEmpty()){
			throw new IllegalArgumentException("no partition found for topic :" + topic);
		}

		List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
		for (PartitionInfo pi : partitions){
			TopicPartition topicPartition = new TopicPartition(pi.topic(), pi.partition());
			topicPartitions.add(topicPartition);
		}

		consumer.assign(topicPartitions);
		consumer.seekToEnd(topicPartitions);

		Map<Integer, Long> partitionOffsetMap = new HashMap();
		for ( TopicPartition  tp : topicPartitions){
			partitionOffsetMap.put(tp.partition(), consumer.position(tp));
		}


		int count = 10;
		int offsetDelta = 1;
		while (offsetDelta != 0 && count > 0) {
			offsetDelta = 0;
			for (TopicPartition tp : topicPartitions) {
				long p = partitionOffsetMap.get(tp.partition());
				if (p > 0) {
					p--;
					count--;
					offsetDelta++;
					partitionOffsetMap.put(tp.partition(), p);
					if (count == 0) {
						break;
					}
				}
			}
		}

		for (TopicPartition tp : topicPartitions) {
			consumer.seek(tp, partitionOffsetMap.get(tp.partition()));
		}

		/**
		 * 	获取2条数据
		 */
		ConsumerRecords<String, String> records = consumer.poll(3000);
		for (ConsumerRecord<String, String> record : records) {
			System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
		}


	}

}
