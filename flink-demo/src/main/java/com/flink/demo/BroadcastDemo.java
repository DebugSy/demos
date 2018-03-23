package com.flink.demo;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.configuration.Configuration;

import java.util.List;

/**
 * Created by DebugSy on 2018/3/23.
 */
public class BroadcastDemo {

	public static void main(String[] args) {

		ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();

		final DataSet<Integer> toBroadcast = env.fromElements(1, 2, 3);
		DataSet<String> data = env.fromElements("India", "UAS", "UK").map(new RichMapFunction<String, String>() {

			private List<Integer> toBroadcast;

			@Override
			public void open(Configuration parameters) throws Exception {
				this.toBroadcast = getRuntimeContext().getBroadcastVariable("country");
			}

			@Override
			public String map(String s) throws Exception {
				int sum = 0;
				for (int a : toBroadcast){
					sum += a;
				}
				return s.toUpperCase() + sum;
			}
		}).withBroadcastSet(toBroadcast, "country");

		try {
			data.print();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
