package com.zookeeper.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by DebugSy on 2017/11/29.
 */
public class ZookeeperClientTest {

	private ZookeeperClient zkClient;

	@Before
	public void init(){
		zkClient = new ZookeeperClient("sandbox.hortonworks.com:2181");
	}

	@Test
	public void testExist() throws KeeperException, InterruptedException {
		Stat stat = zkClient.exist("/zk_demo", false);
		System.out.println(stat.toString());
	}

}
