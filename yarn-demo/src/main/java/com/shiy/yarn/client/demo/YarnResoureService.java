package com.shiy.yarn.client.demo;

import com.shiy.yarn.client.ResourceControlUtils;
import org.apache.hadoop.yarn.server.resourcemanager.webapp.dao.*;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DebugSy on 2017/11/6.
 */
public class YarnResoureService {

	private static final Logger LOG = LoggerFactory.getLogger(YarnResoureService.class);

	private Map<String, YarnQueueInfo> queueInfo;

	private Map yarnMetrics;

	private String yarnRmUrl = "http://node2:8088";

	private WebTarget resource;
	private Response response ;
	private Client client;

	private long timeout = 10000;

	private final String RESOURCEMETRICS = "/ws/v1/cluster/metrics";
	private final String RESOURCESCHEDULER = "/ws/v1/cluster/scheduler";

	private void init(){

		try {
			this.client = ClientBuilder.newClient();
			client.property(ClientProperties.CONNECT_TIMEOUT, timeout);
			client.property(ClientProperties.READ_TIMEOUT, timeout);
			resource = client.target(yarnRmUrl + RESOURCEMETRICS);
			response = resource.request()
					.accept(MediaType.APPLICATION_JSON)
					.get();
			Object metricsEntity = response.getEntity();
			ClusterMetricsInfo metrics = (ClusterMetricsInfo) metricsEntity;
			this.yarnMetrics = getYarnMetrics(metrics);

			resource = client.target(yarnRmUrl + RESOURCESCHEDULER);
			response = resource.request().
					accept(MediaType.APPLICATION_JSON)
					.get();
			Object schedulerEntity = response.getEntity();

			this.queueInfo = getAllQueueInfo(metrics, schedulerEntity);
			LOG.info("get Yarn queues info successfully.");
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

	private Map<String, YarnQueueInfo> getAllQueueInfo(ClusterMetricsInfo metrics, Object schedulerEntity) {
		Map<String, YarnQueueInfo> queueInfoMap = new HashMap<>();
		try{
			if(schedulerEntity instanceof CapacitySchedulerInfo){
				CapacitySchedulerInfo schedulerInfo = (CapacitySchedulerInfo) schedulerEntity;
				ArrayList<CapacitySchedulerQueueInfo> queueInfoList = schedulerInfo.getQueues().getQueueInfoList();
				for (CapacitySchedulerQueueInfo qInfo : queueInfoList){
					YarnQueueInfo queueInfo = new YarnQueueInfo();
					queueInfo.setQueue(qInfo.getQueueName());
					float maxCapacity = qInfo.getMaxCapacity();
					queueInfo.setTotalMemory(Math.round(metrics.getTotalMB() * maxCapacity));
					queueInfo.setTotalVcores(Math.round(metrics.getTotalVirtualCores() * maxCapacity));
					queueInfoMap.put(qInfo.getQueueName(), queueInfo);
				}
			} else if (schedulerEntity instanceof FairSchedulerInfo){
				FairSchedulerInfo schedulerInfo = (FairSchedulerInfo) schedulerEntity;
				FairSchedulerQueueInfo rootQueueInfo = schedulerInfo.getRootQueueInfo();
				Collection<FairSchedulerQueueInfo> childQueues = rootQueueInfo.getChildQueues();
				for (FairSchedulerQueueInfo qInfo : childQueues){
					YarnQueueInfo queueInfo = new YarnQueueInfo();
					ResourceInfo maxResources = qInfo.getMaxResources();
					queueInfo.setQueue(qInfo.getQueueName());
					queueInfo.setTotalMemory(maxResources.getMemorySize());
					queueInfo.setTotalVcores(maxResources.getvCores());
					queueInfoMap.put(qInfo.getQueueName(), queueInfo);
				}
			} else {
				FifoSchedulerInfo schedulerInfo = (FifoSchedulerInfo) schedulerEntity;
				YarnQueueInfo queueInfo = new YarnQueueInfo();
				queueInfo.setQueue(schedulerInfo.getQueueName());
				queueInfo.setTotalMemory(metrics.getTotalMB());
				queueInfo.setTotalVcores(metrics.getTotalVirtualCores());
				queueInfoMap.put(schedulerInfo.getQueueName(), queueInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queueInfoMap;
	}

	private Map getYarnMetrics(ClusterMetricsInfo metrics){
		Map queueInfo = new HashMap<>();

		if (metrics != null){
			queueInfo.put("totalMB", metrics.getTotalMB());
			queueInfo.put("totalVirtualCores", metrics.getTotalVirtualCores());
		} else {
			throw new RuntimeException("can't find yarn configuration.");
		}
		return queueInfo;
	}

	public Map<String, Long> getQueue(String queueName){
		if (this.queueInfo == null || this.yarnMetrics == null){
			init();
		}
		YarnQueueInfo queueInfo = this.queueInfo.get(queueName);
		Map<String, Long> queue = new HashMap<>();
		queue.put("totalMB", queueInfo.getTotalMemory());
		queue.put("totalVirtualCores", queueInfo.getTotalVcores());
		return queue;
	}

//	public Map<String, Float> getQueueInfo() {
//		if (this.queueInfo == null){
//			init();
//		}
//		return this.queueInfo;
//	}

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
