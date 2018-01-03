package com.mapreduce.demo.flowsum;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/3.
 */
public class FlowCountSortBean implements WritableComparable<FlowCountSortBean>{

	private long upFlow;

	private long dFlow;

	private long sumFlow;

	public FlowCountSortBean() {
	}

	public FlowCountSortBean(long upFlow, long dFlow) {
		this.upFlow = upFlow;
		this.dFlow = dFlow;
		this.sumFlow = upFlow + dFlow;
	}

	public void set(long upFlow, long dFlow) {
		this.upFlow = upFlow;
		this.dFlow = dFlow;
		this.sumFlow = upFlow + dFlow;
	}

	public long getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(long upFlow) {
		this.upFlow = upFlow;
	}

	public long getdFlow() {
		return dFlow;
	}

	public void setdFlow(long dFlow) {
		this.dFlow = dFlow;
	}

	public long getSumFlow() {
		return sumFlow;
	}

	public void setSumFlow(long sumFlow) {
		this.sumFlow = sumFlow;
	}

	/**
	 * 序列化方法
	 */
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(upFlow);
		out.writeLong(dFlow);
		out.writeLong(sumFlow);
	}

	/**
	 * 反序列化方法
	 * 注意：反序列化的顺序跟序列化的顺序完全一致
	 */
	@Override
	public void readFields(DataInput in) throws IOException {
		this.upFlow = in.readLong();
		this.dFlow = in.readLong();
		this.sumFlow = in.readLong();
	}

	@Override
	public String toString() {
		return upFlow + "\t" + dFlow + "\t" + sumFlow;
	}


	@Override
	public int compareTo(FlowCountSortBean o) {
		return this.sumFlow>o.getSumFlow()?1:-1;
	}
}
