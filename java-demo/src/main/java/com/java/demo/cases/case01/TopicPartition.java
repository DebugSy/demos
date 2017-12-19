package com.java.demo.cases.case01;

import java.io.Serializable;

/**
 * Created by DebugSy on 2017/8/3.
 */
public final class TopicPartition implements Serializable {
	private int hash = 0;
	private final int partition;
	private final String topic;

	public TopicPartition(String topic, int partition) {
		this.partition = partition;
		this.topic = topic;
	}

	public int partition() {
		return this.partition;
	}

	public String topic() {
		return this.topic;
	}

	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null) {
			return false;
		} else if(this.getClass() != obj.getClass()) {
			return false;
		} else {
			TopicPartition other = (TopicPartition)obj;
			if(this.partition != other.partition) {
				return false;
			} else {
				if(this.topic == null) {
					if(other.topic != null) {
						return false;
					}
				} else if(!this.topic.equals(other.topic)) {
					return false;
				}

				return true;
			}
		}
	}

	public String toString() {
		return this.topic + "-" + this.partition;
	}
}
