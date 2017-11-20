package com.shiy.yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.QueueInfo;
import org.apache.hadoop.yarn.api.records.YarnClusterMetrics;
import org.apache.hadoop.yarn.client.api.AMRMClient;
import org.apache.hadoop.yarn.client.api.NMClient;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by DebugSy on 2017/11/6.
 */
public class YarnClientDemo {

	private YarnClient yarnClient;

	private AMRMClient amrmClient;

	private NMClient nmClient;

	private final String YARNMETRICSURL = "http://node/ws/v1/cluster/metrics";

	public void init(){
		YarnConfiguration conf = new YarnConfiguration();
//		conf.set("yarn.resourcemanager.address", "node3:8050");//yarn.nodemanager.resource.memory-mb

		yarnClient = YarnClient.createYarnClient();
		yarnClient.init(conf);
		System.out.println("Connecting to Yarn on node3:8050 ...");
		yarnClient.start();

	}

	public void getQueueInfo() throws IOException, YarnException {
		init();

//		Configuration amrmClientConfig = amrmClient.getConfig();
//		Iterator<Map.Entry<String, String>> iterator = amrmClientConfig.iterator();
//		while (iterator.hasNext()){
//			Map.Entry<String, String> entry = iterator.next();
//			System.out.println(entry.getKey() + " : " + entry.getValue());
//		}


//		Configuration yarnConfig = yarnClient.getConfig();
//		Iterator<Map.Entry<String, String>> iterator = yarnConfig.iterator();
//		while (iterator.hasNext()){
//			Map.Entry<String, String> entry = iterator.next();
//			System.out.println(entry.getKey() + " : " + entry.getValue());
//		}

		List<QueueInfo> allQueues = yarnClient.getAllQueues();
		for ( QueueInfo queueInfo : allQueues ){
			System.out.println("queue name : " + queueInfo.getQueueName());
			System.out.println("queue size : " + queueInfo.getCapacity());
		}
	}

	public void getTotalInfo() {
		init();
		long memorySize = amrmClient.getClusterNodeCount();
		System.out.println(memorySize);
	}
}
