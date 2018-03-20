package com.shiy.rpc.akka

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by DebugSy on 2018/2/10.
  */
class AkkaWorker(masterHost: String, masterPort: Int) extends Actor{

  var master: ActorSelection = _

  override def preStart(): Unit = {
    master = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/AkkaMaster")
    master ! "connect"
  }

  override def receive: Receive = {

    case "reply" => {
      println("a reply form master")
    }

  }
}

object Worker {
  def main(args: Array[String]) {
    val host = args(0)
    val port = args(1).toInt
    val masterHost = args(2)
    val masterPort = args(3).toInt
    // 准备配置
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem老大，辅助创建和监控下面的Actor，他是单例的
    val actorSystem = ActorSystem("WorkerSystem", config)
    actorSystem.actorOf(Props(new AkkaWorker(masterHost, masterPort)), "Worker")
    actorSystem.awaitTermination()
  }
}
