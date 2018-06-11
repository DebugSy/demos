package com.flink.demo

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time


/**
  * Created by DebugSy on 2018/5/7.
  */

object SocketWindowWordCount {

  def main(args: Array[String]): Unit = {

    implicit val wordWithCountInfo = TypeInformation.of(classOf[WordWithCount])

//    val port: Int = try {
//      ParameterTool.fromArgs(args).getInt("port")
//    } catch {
//      case e: Exception => {
//        System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'")
//        return
//      }
//    }

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI()

    val text = env.socketTextStream("localhost", 9000, '\n')

    val windowCounts = text.flatMap(w => w.split("\\s"))
      .map( w => WordWithCount(w, 1))
      .keyBy("word")
      .timeWindow(Time.seconds(5), Time.seconds(1))
      .sum("count")

    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")

  }

  case class WordWithCount(word: String, count: Long)

}
