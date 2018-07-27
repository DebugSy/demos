package com.flink.demo.cases.case02

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.runtime.state.{FunctionInitializationContext, FunctionSnapshotContext}
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction
import org.apache.flink.streaming.api.functions.sink.SinkFunction

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

/**
  * Created by DebugSy on 2018/6/13.
  */
class BufferingSink(threshold: Int = 0) extends SinkFunction[(String, Int)] with CheckpointedFunction{

  @transient
  private var checkpointedState: ListState[(String, Int)] = _

  private val bufferedElements = ListBuffer[(String, Int)]()

  override def invoke(value: (String, Int)): Unit = {

    val file = new File("E:/tmp/flink/output", "flink-buffered-sink-data.log")
    val fileWriter = new FileWriter(file, true)
    val writer: BufferedWriter = new BufferedWriter(fileWriter)

    bufferedElements += value
    val str = bufferedElements.toString()
    println(s"bufferedElements : $str")

    if (bufferedElements.size == threshold){
      for (element <- bufferedElements){
        //send it to the sink
        writer.write(element.toString())
        writer.flush()
        println("element.toString : " + element.toString())
      }
      bufferedElements.clear()
    }
  }

  override def snapshotState(context: FunctionSnapshotContext): Unit = {
    checkpointedState.clear()
    for (element <- bufferedElements){
      checkpointedState.add(element)
    }
  }

  override def initializeState(context: FunctionInitializationContext): Unit = {
    val descriptor = new ListStateDescriptor[(String, Int)](
      "buffered-elements",
      TypeInformation.of(new TypeHint[(String, Int)]() {})
    )

    checkpointedState = context.getOperatorStateStore.getListState(descriptor)

    if (context.isRestored){
      for (element <- checkpointedState.get()){
        bufferedElements += element
      }
    }
  }
}
