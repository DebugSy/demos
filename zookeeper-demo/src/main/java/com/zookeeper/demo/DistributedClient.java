package com.zookeeper.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributedClient {

    private static final Logger LOG = LoggerFactory.getLogger(DistributedClient.class);

    private static final String SGROUP = "/zk_demo/servers";

    private volatile List<String> servers = new ArrayList<String>(); //并发操作有可能会导致列表值不一致

    private ZooKeeper zk = null;

    public DistributedClient(String connectString, int sessionTimeout) throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                //触发事件
                LOG.info("trigger event ......");
                try {
                    servers = getAliveServer();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<String> getAliveServer() throws KeeperException, InterruptedException {
        List<String> childrens = zk.getChildren(SGROUP, true);
        for (String childen : childrens){
            byte[] data = zk.getData(SGROUP + "/" + childen, false, null);
            servers.add(new String(data));
            LOG.info("server is alive:{}", new String(data));
        }
        return servers;
    }

    public void handleBussiness() throws InterruptedException {
        System.out.println("client connecting server......");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributedClient client = new DistributedClient("sandbox.hortonworks.com:2181", 3000);
        List<String> aliveServer = client.getAliveServer();
        System.out.println(aliveServer);
        client.handleBussiness();
    }
}
