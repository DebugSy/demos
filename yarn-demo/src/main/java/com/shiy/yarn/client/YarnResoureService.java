package com.shiy.yarn.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.QueueInfo;
import org.apache.hadoop.yarn.api.records.YarnClusterMetrics;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Map yarnMetrics;

	public void init(){
		yarnClient = YarnClient.createYarnClient();
		YarnConfiguration conf = new YarnConfiguration();
		conf.set("yarn.timeline-service.enabled", "false");
		yarnClient.init(conf);
		try{
			LOG.info("Connecting YARN to get queues info......");
			yarnClient.start();
			this.queueInfo = getAllQueueInfo();
			this.yarnMetrics = getYarnMetrics();
			LOG.info("get Yarn queues info successfully.");
			yarnClient.stop();
		}catch (Exception e){
			LOG.error("error message : ", e);
			LOG.warn("use default yarn resource config");
			this.yarnMetrics = defaultConfig();
		}
	}

	private Map defaultConfig(){
		LOG.warn("Unable to connect to yarn! Perform the default configuration.");
		LOG.warn("If you want to change the configuration, please specify the parameters[carpo.root.tenant.maxram,carpo.root.tenant.maxcpus] in the grassland-env.sh");
		Map yarnMetrics = new HashMap();
		String rootMaxRam = System.getProperty("carpo.root.tenant.maxram");
		String rootMaxCpus = System.getProperty("carpo.root.tenant.maxcpus");
		if (rootMaxRam != null && !rootMaxRam.isEmpty() && rootMaxCpus != null && !rootMaxCpus.isEmpty()){
			yarnMetrics.put("totalMB", ResourceControlUtils.getLongFromString(rootMaxRam));
			yarnMetrics.put("totalVirtualCores", Integer.parseInt(rootMaxCpus));
		} else {
			yarnMetrics.put("totalMB", 1024000);
			yarnMetrics.put("totalVirtualCores", 1000);
		}
		return yarnMetrics;
	}

	private Map<String, Float> getAllQueueInfo() {
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

	private Map getYarnMetrics(){
		Map queueInfo = new HashMap<>();
		try {
			Configuration configuration = yarnClient.getConfig();
			configuration.getValByRegex("yarn.nodemanager.resource.memory-mb");
			long totalMemory = Long.parseLong(configuration.get("yarn.nodemanager.resource.memory-mb"));
			int totalCpus = Integer.parseInt(configuration.get("yarn.nodemanager.resource.cpu-vcores"));
			int numNodeManagers = 0;
			YarnClusterMetrics yarnClusterMetrics = yarnClient.getYarnClusterMetrics();
			numNodeManagers = yarnClusterMetrics.getNumNodeManagers();
			LOG.info("node memory : {}, node vcores : {}, node number : {}", totalMemory, totalCpus, numNodeManagers);
			if (numNodeManagers != 0){
				queueInfo.put("totalMB", totalMemory * numNodeManagers);
				queueInfo.put("totalVirtualCores", totalCpus * numNodeManagers);
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

	public Map<String, Long> getQueue(String queueName){
		if (this.queueInfo == null || this.yarnMetrics == null){
			init();
		}
		Float capacityPercent = this.queueInfo.get(queueName);
		Map yarnMetrics = this.yarnMetrics;
		long totalMB = (long) yarnMetrics.get("totalMB");
		int totalVirtualCores = (int) yarnMetrics.get("totalVirtualCores");
		Map<String, Long> queue = new HashMap<>();
		queue.put("totalMB", (long) Math.round(totalMB * capacityPercent));
		queue.put("totalVirtualCores", (long) Math.round(totalVirtualCores * capacityPercent));
		return queue;
	}

	public Map<String, Float> getQueueInfo() {
		if (this.queueInfo == null){
			init();
		}
		return this.queueInfo;
	}

	public Map getYarnMetricsInfo(){
		if (this.yarnMetrics == null){
			init();
		}
		return this.yarnMetrics;
	}

	public static void main(String[] args) {
		YarnResoureService yarnResoureService = new YarnResoureService();
		yarnResoureService.init();
	}

}
