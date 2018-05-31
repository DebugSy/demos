package com.flink.demo.api.streaming

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

/**
  * Created by DebugSy on 2018/5/31.
  */
class TimestampExtractor extends AssignerWithPeriodicWatermarks[String]{
  override def getCurrentWatermark: Watermark = new Watermark(System.currentTimeMillis())

  override def extractTimestamp(element: String, previousElementTimestamp: Long): Long = {
    val strings = element.trim.split("\\s")
    println(element)
    strings(1).toLong
  }
}
