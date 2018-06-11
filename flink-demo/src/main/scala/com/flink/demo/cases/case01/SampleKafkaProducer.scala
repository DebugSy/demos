package com.flink.demo.cases.case01

import java.text.SimpleDateFormat
import java.util.{Date, Properties, Random}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.sling.commons.json.JSONObject

/**
  * Created by DebugSy on 2018/6/7.
  */
class SampleKafkaProducer {

  var kafkaProducer: KafkaProducer[String,String] = _

  private val dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  private var pointer = -1

  def init(bootstrap: String): Unit = {
    val properties = configKafka(bootstrap)
    kafkaProducer = new KafkaProducer[String,String](properties)
  }

  private def configKafka(bootstrap: String): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrap)
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  def produce(topic: String) = {
    val msg = prepareMessage()
    val producerRecord  = new ProducerRecord[String, String](topic, msg)
    val outDate = dataFormat.format(new Date()).toString
    println(s"$outDate : $msg")
    kafkaProducer.send(producerRecord)
  }

  def prepareMessage(): String = {
    val random = new Random()
    val event = new JSONObject()
    event.put("uid", getUserId)
      .put("event_time", System.currentTimeMillis().toString)
      .put("os_type", "Android")
      .put("click_count", random.nextInt(10))
    event.toString
  }

  private def getUserId(): String = {
    val users = Array(
      "4A4D769EB9679C054DE81B973ED5D768", "8dfeb5aaafc027d89349ac9a20b3930f",
      "011BBF43B89BFBF266C865DF0397AA71", "f2a8474bf7bd94f0aabbd4cdd2c06dcf",
      "068b746ed4620d25e26055a9f804385f", "97edfc08311c70143401745a03a50706",
      "d7f141563005d1b5d0d3dd30138f3f62", "c8ee90aade1671a21336c721512b817a",
      "6b67c8c700427dee7552f81f3228c927", "a95f22eabc4fd4b580c011a3161a9d9d")

    pointer = pointer + 1
    if(pointer >= users.length) {
      pointer = 0
      users(pointer)
    } else {
      users(pointer)
    }
  }

}

object SampleKafkaProducer{

  def main(args: Array[String]): Unit = {
    val producer = new SampleKafkaProducer
    producer.init("localhost:9092")
    while (true){
      producer.produce("shiy01")
      Thread.sleep(1000)
    }
  }

}
