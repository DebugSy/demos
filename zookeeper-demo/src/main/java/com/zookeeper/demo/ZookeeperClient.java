package com.zookeeper.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by DebugSy on 2017/11/29.
 */
public class ZookeeperClient {

	private static final Logger LOG = LoggerFactory.getLogger(ZookeeperClient.class);

	private ZooKeeper zookeeper;

	private int sessionTimeout = 3000;

	public ZookeeperClient(String hosts, int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
		try {
			this.zookeeper = new ZooKeeper(hosts, sessionTimeout, new simpleWatcher());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ZookeeperClient(String hosts) {
		try {
			this.zookeeper = new ZooKeeper(hosts, sessionTimeout, new simpleWatcher());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stat exist(String path, boolean watch) throws KeeperException, InterruptedException {
		Stat stat = zookeeper.exists(path, watch);
		return stat;
	}

	public void create(String path) throws KeeperException, InterruptedException {
		String nodeCreated = zookeeper.create(path, "hellozk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}


}

class simpleWatcher implements Watcher {

	public void process(WatchedEvent event) {
		// 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
		System.out.println("触发事件. ");
		System.out.println("type :" + event.getType() + ". state:" + event.getState() + ". path:" + event.getPath());
	}
}
