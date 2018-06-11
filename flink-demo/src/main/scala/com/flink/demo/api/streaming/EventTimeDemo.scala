package com.flink.demo.api.streaming

import com.flink.demo.SocketWindowWordCount.WordWithCount
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created by DebugSy on 2018/5/31.
  */
object EventTimeDemo {

  def main(args: Array[String]): Unit = {

    implicit val wordWithCountInfo = TypeInformation.of(classOf[(Char, Int)])

    val env = StreamExecutionEnvironment.createLocalEnvironment()
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val source = env.socketTextStream("localhost", 9002)
      .assignTimestampsAndWatermarks(new TimestampExtractor)

    val result = source.flatMap(w => w.trim.split("\\s")(0))
      .map(word => (word, 1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(10), Time.seconds(5))
      .allowedLateness(Time.seconds(5000))
      .sum(1)

    println("--------------")

    result.print()

    env.execute("eventTime window demo")


  }

}
