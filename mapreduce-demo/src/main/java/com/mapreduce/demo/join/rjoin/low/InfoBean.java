package com.mapreduce.demo.join.rjoin.low;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by DebugSy on 2018/1/4.
 */
public class InfoBean implements WritableComparable<InfoBean>{

	private int id;

	private String data;

	private String name;

	private int category_id;

	private double price;

	private int flag;

	public InfoBean() {
	}

	public InfoBean(int id, String data, String name, int category_id, double price, int flag) {
		this.id = id;
		this.data = data;
		this.name = name;
		this.category_id = category_id;
		this.price = price;
		this.flag = flag;
	}

	public void set(int id, String data, int flag){
		this.id = id;
		this.data = data;
		this.name = "";
		this.category_id = 0;
		this.price = 0;
		this.flag = flag;
	}

	public void set(String name, int category_id, double price, int flag){
		this.id = 0;
		this.data = "";
		this.name = name;
		this.category_id = category_id;
		this.price = price;
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public int compareTo(InfoBean o) {
		return this.id>o.getId()?1:-1;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(id);
		out.writeUTF(name);
		out.writeUTF(data);
		out.writeInt(category_id);
		out.writeDouble(price);
		out.writeInt(flag);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.id = in.readInt();
		this.name = in.readUTF();
		this.data = in.readUTF();
		this.category_id = in.readInt();
		this.price = in.readDouble();
		this.flag = in.readInt();
	}

	@Override
	public String toString() {
		return id + "\t" + name + "\t" + data + "\t" + category_id + "\t" + price;
	}
}
