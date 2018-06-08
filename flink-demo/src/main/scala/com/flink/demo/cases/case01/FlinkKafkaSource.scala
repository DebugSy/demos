package com.flink.demo.cases.case01

import java.util.Properties

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.{JSONDeserializationSchema, SimpleStringSchema}
import org.apache.flink.streaming.api.scala._

/**
  * Created by DebugSy on 2018/6/8.
  */
class FlinkKafkaSource(bootstrap: String, topic: String) {

  implicit val objectNodeTypeInfo = TypeInformation.of(classOf[ObjectNode])//增加隐式转换
  implicit val jsonNodeTypeInfo = TypeInformation.of(classOf[JsonNode])//增加隐式转换
  implicit val typeInfo = TypeInformation.of(classOf[(String,Int)])//增加隐式转换

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

    val counts: DataStream[(String, Int)] = source.map(record => (record.get("uid").asText(), record.get("click_count").asInt()))
      .keyBy(x => x._1)
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
