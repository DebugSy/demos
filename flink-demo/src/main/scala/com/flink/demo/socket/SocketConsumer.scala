package com.flink.demo.socket

import java.io.DataInputStream
import java.net.Socket

/**
  * Created by DebugSy on 2018/5/31.
  */
object SocketConsumer {

  def main(args: Array[String]): Unit = {

    val socket = new Socket("localhost", 9002)
    val in = socket.getInputStream
    val din = new DataInputStream(in)
    while (true){
      val msg = din.readUTF()
      println(msg)
    }


  }

}
