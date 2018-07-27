package com.flink.demo.cases.case03

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.util.Collector
import org.slf4j.LoggerFactory
import org.slf4j.LoggerFactory
/**
  * Created by DebugSy on 2018/6/12.
  */
class CountWindowAverage extends RichFlatMapFunction[(Long, Long), (Long, Long)]{

  private var sum: ValueState[(Long, Long)] = _

  private final val LOG = LoggerFactory.getLogger(classOf[CountWindowAverage])

  override def flatMap(value: (Long, Long), out: Collector[(Long, Long)]): Unit = {

    val tmpCurrentSum = sum.value

    val currentSum = if (tmpCurrentSum != null)
      tmpCurrentSum
    else
      (0L, 0L)

    val newSum = (currentSum._1 + value._1, currentSum._2 + value._2)

    sum.update(newSum)

    if (newSum._1 >= 2){
      out.collect((value._1, newSum._2 / newSum._1))
      sum.clear()
    }

  }

  override def open(parameters: Configuration): Unit = {
    sum = getRuntimeContext.getState(
      new ValueStateDescriptor[(Long, Long)]("average", createTypeInformation[(Long, Long)])
    )
  }
}

