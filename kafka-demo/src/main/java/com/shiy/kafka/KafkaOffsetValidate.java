package com.shiy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class KafkaOffsetValidate {

    public void currentOffset(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test03");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000"); //定时commit的周期
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        String topic = "test01";
        List<PartitionInfo> partitions = consumer.partitionsFor(topic);

        List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
        for (PartitionInfo pi : partitions){
            TopicPartition topicPartition = new TopicPartition(pi.topic(), pi.partition());
            topicPartitions.add(topicPartition);
        }

        TopicPartition topicPartition0 = new TopicPartition(topic, 0);
        TopicPartition topicPartition1 = new TopicPartition(topic, 1);
        TopicPartition topicPartition2 = new TopicPartition(topic, 2);
        TopicPartition topicPartition3 = new TopicPartition(topic, 3);
        TopicPartition topicPartition4 = new TopicPartition(topic, 4);
        consumer.assign(topicPartitions);
//        consumer.subscribe(Arrays.asList("test01"));
        long position0 = consumer.position(topicPartition0);
        long position1 = consumer.position(topicPartition1);
        long position2 = consumer.position(topicPartition2);
        long position3 = consumer.position(topicPartition3);
        long position4 = consumer.position(topicPartition4);
        System.out.println("position : " + position0);
        System.out.println("position : " + position1);
        System.out.println("position : " + position2);
        System.out.println("position : " + position3);
        System.out.println("position : " + position4);

        /**
         * 	获取2条数据
         */
        ConsumerRecords<String, String> records = consumer.poll(3000);
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n",record.partition(), record.offset(), record.key(), record.value());
        }

        consumer.seekToEnd(topicPartitions);

        long position00 = consumer.position(topicPartition0);
        long position11 = consumer.position(topicPartition1);
        long position22 = consumer.position(topicPartition2);
        long position33 = consumer.position(topicPartition3);
        long position44 = consumer.position(topicPartition4);
        System.out.println("position - " + position00);
        System.out.println("position - " + position11);
        System.out.println("position - " + position22);
        System.out.println("position - " + position33);
        System.out.println("position - " + position44);



//        consumer.commitAsync();
    }

}
