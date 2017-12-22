package com.zookeeper.demo;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class DistributedServer {

    //获取Zk连接

    //利用zk连接注册服务信息

    //启动业务逻辑

    private static final Logger LOG = LoggerFactory.getLogger(DistributedServer.class);

    private static final String SGROUP = "/zk_demo/servers/";

    private ZooKeeper zk = null;

    public DistributedServer(String connectString, int timeout) {
        try {
            zk = new ZooKeeper(connectString, timeout, new Watcher() {
                public void process(WatchedEvent event) {
                    //触发事件
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String register(String name) throws KeeperException, InterruptedException {
        String path = zk.create(SGROUP, name.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(name + "is online.");
        return path;
    }

    public void handleBussiness(String hostname) throws InterruptedException {
        System.out.println(hostname + "start working......");
        Thread.sleep(Long.MAX_VALUE);
    }


    public static void main(String[] args) throws KeeperException, InterruptedException {
        DistributedServer distributedServer = new DistributedServer("sandbox.hortonworks.com:2181", 3000);
        long random = new Random().nextLong();
        LOG.info("Server name is DistributedServer-" + random);
        distributedServer.register("DistributedServer-" + random);
        distributedServer.handleBussiness("DistributedServer");
    }
}
