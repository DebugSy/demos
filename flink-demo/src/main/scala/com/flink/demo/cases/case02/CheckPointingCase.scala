package com.flink.demo.cases.case02

import java.util.Properties

import com.fasterxml.jackson.databind.node.ObjectNode
import com.flink.demo.cases.case01.KafkaEventTimeStampExtractor
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.{JSONDeserializationSchema, JSONKeyValueDeserializationSchema}

/**
  * Created by DebugSy on 2018/6/11.
  */
object CheckPointingCase {

  private val env = StreamExecutionEnvironment.getExecutionEnvironment

  def main(args: Array[String]): Unit = {

    env.enableCheckpointing(1 * 60 * 1000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.setStateBackend(new FsStateBackend("file:///E:/tmp/flink/StateBackend"))

//    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)

    val props = new Properties()
    props.setProperty("bootstrap.servers", "localhost:9092")

    env.addSource(new FlinkKafkaConsumer010[ObjectNode]("shiy01", new JSONDeserializationSchema(), props))
      .assignTimestampsAndWatermarks(new KafkaEventTimeStampExtractor)
      .map(record => Tuple2(record.get("uid").asText(), record.get("click_count").asInt()))
      .keyBy(_._1)
      .timeWindow(Time.seconds(20), Time.seconds(10))
      .sum(1)
      .addSink(new BufferingSink(10))

    env.execute("checkpointing case")


  }

}
