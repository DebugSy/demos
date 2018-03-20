package com.shiy.rpc.akka

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by DebugSy on 2018/2/10.
  */
class AkkaMaster extends Actor{

  println("connect invoked.")


  override def preStart(): Unit = {
    println("preStart invoked.")
  }

  override def receive: Receive = {

    case "hello" => println("hello")
    case "connect" => {
      println("a client connect ")
      sender ! "reply"
    }

  }

}
object AkkaMaster {

  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port  = args(1).toInt

    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val acterSystem = ActorSystem.create("MasterSystem", config)
    val master = acterSystem.actorOf(Props[AkkaMaster], "AkkaMaster")
    master ! "hello"
    acterSystem.awaitTermination()
  }

}