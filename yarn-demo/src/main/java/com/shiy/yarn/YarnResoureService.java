package com.shiy.yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.QueueInfo;
import org.apache.hadoop.yarn.api.records.YarnClusterMetrics;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DebugSy on 2017/11/6.
 */
public class YarnResoureService {

	private static final Logger LOG = LoggerFactory.getLogger(YarnResoureService.class);

	private YarnClient yarnClient;

	private Map<String, Float> queueInfo;

	@PostConstruct
	public void init(){
		yarnClient = YarnClient.createYarnClient();
		YarnConfiguration conf = new YarnConfiguration();
		yarnClient.init(conf);
		LOG.info("Connecting YARN to get queues info......");
		yarnClient.start();
		this.queueInfo = getAllQueueInfo();
		for (String queue : queueInfo.keySet()){
			System.out.println(queue + " : " + queueInfo.get(queue));
		}
		LOG.info("get Yarn queues info successfully.");
		yarnClient.stop();
	}

	public Map<String, Float> getAllQueueInfo() {
		Map<String, Float> queueInfoMap = new HashMap<>();
		try {
			List<QueueInfo> allQueues = yarnClient.getAllQueues();
			for (QueueInfo queueInfo : allQueues){
				queueInfoMap.put(queueInfo.getQueueName(), queueInfo.getCapacity());
			}
		} catch (YarnException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queueInfoMap;
	}

	public Map<String, Double> getQueue(String queueName){
		Float capacityPercent = queueInfo.get(queueName);
		Map<String, Long> yarnMetrics = getYarnMetrics();
		long totalMB = yarnMetrics.get("totalMB");
		long totalVirtualCores = yarnMetrics.get("totalVirtualCores");
		Map<String, Double> queue = new HashMap<>();
		queue.put("totalMB", Math.floor(totalMB * capacityPercent));
		queue.put("totalVirtualCores", Math.floor(totalVirtualCores * capacityPercent));
		return queue;
	}

	public Map getYarnMetrics(){
		Map queueInfo = new HashMap<>();
		try {
			Configuration configuration = yarnClient.getConfig();
			long totalMemory = Long.parseLong(configuration.get("yarn.nodemanager.resource.memory-mb"));
			int totalCpus = Integer.parseInt(configuration.get("yarn.nodemanager.resource.cpu-vcores"));
			int numNodeManagers = 0;
			YarnClusterMetrics yarnClusterMetrics = yarnClient.getYarnClusterMetrics();
			numNodeManagers = yarnClusterMetrics.getNumNodeManagers();
			if (numNodeManagers != 0){
				queueInfo.put("totalMemory", totalMemory * numNodeManagers);
				queueInfo.put("totalCpus", totalCpus * numNodeManagers);
			} else {
				throw new RuntimeException("can't find yarn configuration.");
			}
			queueInfo.put("yarn.resourcemanager.address", configuration.get("yarn.resourcemanager.address"));

		} catch (YarnException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queueInfo;
	}

}
