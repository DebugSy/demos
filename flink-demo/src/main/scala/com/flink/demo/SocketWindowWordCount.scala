package com.flink.demo

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time


/**
  * Created by DebugSy on 2018/5/7.
  */

object SocketWindowWordCount {

  def main(args: Array[String]): Unit = {

    val port: Int = try {
      ParameterTool.fromArgs(args).getInt("port")
    } catch {
      case e: Exception => {
        System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'")
        return
      }
    }

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val text = env.socketTextStream("localhost", port, '\n')

    val windowCounts = text.flatMap(w => w.split("\\s"))
      .map( w => WordWithCount(w, 1))
      .keyBy("word")
      .timeWindow(Time.seconds(5), Time.seconds(1))
      .sum("count")

    windowCounts.print().setParallelism(1)

  }

  case class WordWithCount(word: String, count: Long)

}
