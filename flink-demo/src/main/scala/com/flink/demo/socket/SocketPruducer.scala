package com.flink.demo.socket

import java.io.DataOutputStream
import java.net.{ServerSocket, Socket}

/**
  * Created by DebugSy on 2018/5/31.
  */
object SocketPruducer {

  def main(args: Array[String]): Unit = {
    val socketServer = new ServerSocket(9002)
    println("before accept....")
    val socket = socketServer.accept()
    println("after accept....")
    val out = new DataOutputStream(socket.getOutputStream)
    var n = 0
    while (true){
      Thread.sleep(5000)
      var msg = "a " + System.currentTimeMillis()
      print("msg : " + msg+"\n")
      out.writeUTF(msg+"\n")
      if (n == 5){
        out.writeUTF(msg+"\n")
        msg = "b " + (System.currentTimeMillis() - 6000)
        print("msg : " + msg+"\n")
        out.writeUTF(msg+"\n")
      }
      out.flush()
      n+=1
    }
  }



}
