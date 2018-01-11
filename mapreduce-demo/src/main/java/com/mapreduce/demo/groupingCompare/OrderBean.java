package com.mapreduce.demo.groupingCompare;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/11.
 */
public class OrderBean implements WritableComparable<OrderBean> {

	private String id;

	private double amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	@Override
	public int compareTo(OrderBean o) {
		return this.amount < o.getAmount() ? 1 : -1;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(id);
		out.writeDouble(amount);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.id = in.readUTF();
		this.amount = in.readDouble();
	}

	@Override
	public String toString() {
		return id + "\t" + amount;
	}
}
