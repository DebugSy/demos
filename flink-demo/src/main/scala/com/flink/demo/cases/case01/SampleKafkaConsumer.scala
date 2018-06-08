package com.flink.demo.cases.case01


import java.util.Arrays
import java.util.List
import java.util.Properties
import collection.JavaConversions._

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}

/**
  * Created by DebugSy on 2018/6/8.
  */
class SampleKafkaConsumer(bootstrap: String, topics: List[String]) {

  var kafkaConsumer: KafkaConsumer[String, String] = _

  private[this]  def init(): Unit = {
    val props = configKafka()
    kafkaConsumer = new KafkaConsumer[String, String](props)
  }
  init()

  private[this] def configKafka(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrap)
    props.put("group.id", "test")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props
  }

  def consumer() = {
    kafkaConsumer.subscribe(topics)
    kafkaConsumer.poll(2000)
  }

  def show(records: ConsumerRecords[String, String]): Unit = {
    for (record: ConsumerRecord[String, String] <- records)
      println(s"topic=${record.topic()} offset=${record.offset()} " +
        s"key=${record.key()} value=${record.value()} ")
  }

}

object SampleKafkaConsumer{

  def main(args: Array[String]): Unit = {
    val kafkaConsumer = new SampleKafkaConsumer("localhost:9092", Arrays.asList("shiy01"))
    while (true)
      kafkaConsumer.show(kafkaConsumer.consumer())
  }

}
