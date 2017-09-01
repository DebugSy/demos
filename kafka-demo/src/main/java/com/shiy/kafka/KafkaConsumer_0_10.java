package com.shiy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;


/**
 * Created by DebugSy on 2017/7/31.
 */
public class KafkaConsumer_0_10 {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList("test01"));//订阅topic

		TopicPartition topicPartition = new TopicPartition("test01", 0);
		consumer.poll(1000);//必须先poll在去移动指针

		//指定offset到开始和结束的地方
//		consumer.seekToEnd(Arrays.asList(topicPartition));
//		consumer.seekToBeginning(Arrays.asList(topicPartition));

		//获取当前指针的位置
		long position = consumer.position(topicPartition);
		System.out.println("position : " + position);

		/**
		 * 	获取2条数据
		 */
		long count = 2;
		consumer.seekToEnd(Arrays.asList(topicPartition));
		long position1 = consumer.position(topicPartition);//获取当前指针的位置
		consumer.seek(topicPartition, position1 - count);//将指针向前移count位
		ConsumerRecords<String, String> records = consumer.poll(1000);
		for (ConsumerRecord<String, String> record : records) {
			System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
		}


	}


}
