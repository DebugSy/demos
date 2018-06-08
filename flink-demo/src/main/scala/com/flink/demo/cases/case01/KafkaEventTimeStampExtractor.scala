package com.flink.demo.cases.case01

import com.fasterxml.jackson.databind.node.{ObjectNode, TextNode}
import com.fasterxml.jackson.databind.JsonNode
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark


/**
  * Created by DebugSy on 2018/6/8.
  */
class KafkaEventTimeStampExtractor extends AssignerWithPeriodicWatermarks[ObjectNode]{
  override def getCurrentWatermark: Watermark = new Watermark(System.currentTimeMillis())

//  override def extractTimestamp(element: ObjectNode, previousElementTimestamp: Long): Long = {
//    val eventTime = element.get("event_time")
//    eventTime.asLong()
//}

  override def extractTimestamp(element: ObjectNode, previousElementTimestamp: Long): Long = {
  //val jsonObject = new TextNode(element)
  //jsonObject.get("event_time").asLong()
//    System.currentTimeMillis()
    element.get("event_time").asLong()
}
}
