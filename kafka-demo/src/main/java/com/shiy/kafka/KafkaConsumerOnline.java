package com.shiy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by DebugSy on 2018/2/1.
 */
public class KafkaConsumerOnline {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.112.128:9092");
		props.put("group.id", "logkeeper");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

		String topic = "carpo.logkeeper";
		List<PartitionInfo> partitions = consumer.partitionsFor(topic);

		if (partitions == null || partitions.isEmpty()){
			throw new IllegalArgumentException("no partition found for topic :" + topic);
		}

		List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
		for (PartitionInfo pi : partitions){
			TopicPartition topicPartition = new TopicPartition(pi.topic(), pi.partition());
			topicPartitions.add(topicPartition);
		}

		consumer.subscribe(Arrays.asList(topic));
		while (true){
			ConsumerRecords<String, String> records = consumer.poll(3000);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
			}
		}

	}

}
