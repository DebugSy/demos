package com.shiy.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by DebugSy on 2017/9/21.
 */
public class KafkaOffsetCheck {

	private static final Logger logger = LoggerFactory.getLogger(KafkaOffsetCheck.class);

	private Map<String, String> topicMap = new HashMap<>();

	private KafkaConsumer<String, String> setConsumer(String brokers, String groupId) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokers);
		props.put("group.id", groupId);
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000"); //定时commit的周期
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		return new KafkaConsumer<>(props);
	}

	public Map<String, String> config(String topic, String group){
		topicMap.put(topic, group);
		return topicMap;
	}

	public List<String> run(String brokers, String topicName, String group){
		List<String> topicsErr = new ArrayList<>();
		Map<String, String> topicGroupMap = config(topicName, group);
		for (String topic : topicGroupMap.keySet()){
			logger.info("check topic : {}", topic);
			String groupId = topicGroupMap.get(topic);
			KafkaConsumer<String, String> consumer = setConsumer(brokers, groupId);
			String topicErr = checkTopicOffset(consumer, topic);
			if (topicErr != null){
				topicsErr.add(topicErr);
			}
		}
		return topicsErr;
	}

	private String checkTopicOffset(KafkaConsumer consumer, String topic) {
		List<PartitionInfo> partitions = consumer.partitionsFor(topic);

		List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
		for (PartitionInfo pi : partitions) {
			TopicPartition topicPartition = new TopicPartition(pi.topic(), pi.partition());
			topicPartitions.add(topicPartition);
		}
		consumer.assign(topicPartitions);

		Map<Integer, Long> partitionOffsetMap = new HashMap();
		for (TopicPartition tp : topicPartitions) {
			int partition = tp.partition();
			long position = consumer.position(tp);
			partitionOffsetMap.put(partition, position);
		}

		consumer.seekToEnd(topicPartitions);
		long messageBacklogs = 0;
		for (TopicPartition tp : topicPartitions) {
			long lastOffset = consumer.position(tp);
			long offset = partitionOffsetMap.get(tp.partition());
			logger.info("topic:{}, partition:{}, offset:{}, lastOffset:{}", topic, tp.partition(), offset, lastOffset);
			System.out.printf("topic:%s, partition:%d, offset:%d, lastOffset:%d\n", topic, tp.partition(), offset, lastOffset);
			if (offset < lastOffset) {
				long difference = lastOffset - offset;
				messageBacklogs += difference;
			}
		}

		if (messageBacklogs > 0) {
			logger.error("The messages of the topic is not consumed : {}", topic);
			System.out.printf("The messages of the topic is not consumed : %s\n", topic);
			return topic;
		}

		return null;
	}

	public static KafkaOffsetCheck checkBuilder() {
		return new KafkaOffsetCheck();
	}

	public static void main(String[] args) {
		List<String> topics = null;

		if (args.length != 3){
			throw new IllegalArgumentException("args error!");
		}

		topics = checkBuilder().run(args[0], args[1], args[2]);


		if (topics.size() == 0){
			logger.info("all kafka topic are normal.");
			System.out.println("all kafka topic are normal.");
			return;
		}

		for (String topic : topics){
			logger.info("There is surplus message in topic:{}.",topic);
			System.out.println("There is surplus message in topic:" + topic);
		}

	}

}
