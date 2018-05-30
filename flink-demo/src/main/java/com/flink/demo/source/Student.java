package com.flink.demo.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.Serializable;

/**
 * Created by DebugSy on 2018/5/17.
 */
public class Student implements SourceFunction,Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void run(SourceContext ctx) throws Exception {

	}

	public void cancel() {

	}
}
