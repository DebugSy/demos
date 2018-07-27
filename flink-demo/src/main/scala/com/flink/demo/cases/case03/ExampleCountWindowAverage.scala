package com.flink.demo.cases.case03

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
  * Created by DebugSy on 2018/6/12.
  */
object ExampleCountWindowAverage extends App {

  val  env = StreamExecutionEnvironment.getExecutionEnvironment

  env.fromCollection(List(
    (1L, 3L),
    (1L, 5L),
    (1L, 7L),
    (1L, 4L),
    (1L, 2L)
  )).keyBy(_._1)
    .flatMap(new CountWindowAverage)
    .print()

//      .mapWithState( (in: (Long, Long), count: Option[Long]) =>
//        count match {
//          case Some(c) => ( (in._1, c), Some(c + in._2) )
//          case None => ( (in._1, 0), Some(in._2) )
//        }
//      )
//      .print()

  env.execute("ExampleManagedState")

}
