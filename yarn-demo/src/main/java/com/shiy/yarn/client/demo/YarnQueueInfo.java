package com.shiy.yarn.client.demo;

/**
 * Created by DebugSy on 2018/2/8.
 */
public class YarnQueueInfo {

	private String queue;

	private long totalMemory;

	private long totalVcores;

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getTotalVcores() {
		return totalVcores;
	}

	public void setTotalVcores(long totalVcores) {
		this.totalVcores = totalVcores;
	}
}
