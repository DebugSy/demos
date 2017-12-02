package com.zookeeper.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


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
		Stat stat = zkClient.exist("/zk_demo/test_create_node", false);
		System.out.println(stat.toString());
	}

	@Test
	public void testCreateNode() throws KeeperException, InterruptedException {
		String path = zkClient.create("/zk_demo/test_create_node");
		System.out.println(path);
	}

	@Test
	public void testGetChildren() throws KeeperException, InterruptedException {
		List<String> childrens = zkClient.getChildren("/");
		for (String children : childrens){
			System.out.println("children:" + children);
		}
	}

	@Test
	public void testGetData() throws KeeperException, InterruptedException {
		byte[] data = zkClient.getData("/zk_demo");
		System.out.println(new String(data));
	}

	@Test
	public void testSetData() throws KeeperException, InterruptedException {
		byte[] data = "test_set_data_to_znode".getBytes();
		Stat stat = zkClient.setData("/zk_demo/test_create_node", data, 0);
		System.out.println(stat.toString());
	}

	@Test
	public void testDeleteZnode() throws KeeperException, InterruptedException {
		zkClient.deleteZnode("/zk_demo/test_create_node");
	}

}
