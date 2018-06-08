package com.flink.demo.api.streaming

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created by DebugSy on 2018/5/31.
  */
object SocketTimestampAndWaterDemoScala {

  def main(args: Array[String]): Unit = {

    implicit val wordWithCountInfo = TypeInformation.of(classOf[(String, Int)])

    val env = StreamExecutionEnvironment.createLocalEnvironment()
    val source = env.socketTextStream("localhost", 9001)
    val result = source.flatMap(w => w.split("\\s"))
      .map(word => (word, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(30), Time.seconds(10))
      .sum(1)

    result.print()

    env.execute("scala window demo")


  }

}
