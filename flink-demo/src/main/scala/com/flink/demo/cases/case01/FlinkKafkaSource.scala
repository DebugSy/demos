package com.flink.demo.cases.case01

import java.util.Properties

import com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema

/**
  * Created by DebugSy on 2018/6/8.
  *
  * case01:
  * 将kafka作为source，提取记录的时间戳，统计10秒内同一个id出现的次数，时间窗口时20s
  */
class FlinkKafkaSource(bootstrap: String, topic: String) {

  private[this] final var env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment()

  val kafkaSource = new FlinkKafkaConsumer010[ObjectNode](topic, new JSONDeserializationSchema(), configKafka)

  def configKafka(): Properties = {
    val props = new Properties()
    props.setProperty("bootstrap.servers", bootstrap)
    props
  }

  def run(): Unit ={
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val source: DataStream[ObjectNode] = env.addSource(kafkaSource)
      .assignTimestampsAndWatermarks(new KafkaEventTimeStampExtractor);

    val counts: DataStream[(String, Int)] = source.map(record => Tuple2(record.get("uid").asText(), record.get("click_count").asInt()))
      .keyBy(0)
      .timeWindow(Time.seconds(20), Time.seconds(10))
        .sum(1)

    counts.printToErr();

    env.execute("flink kafka source")
  }

}

object FlinkKafkaSource{

  def main(args: Array[String]): Unit = {
    val flinkKafka = new FlinkKafkaSource("localhost:9092", "shiy01")
    flinkKafka.run()
  }

}
