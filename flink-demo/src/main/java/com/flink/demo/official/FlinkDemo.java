package com.flink.demo.official;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by DebugSy on 2018/5/9.
 */
public class FlinkDemo {

	private final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

	public void readFile(String path) throws Exception {
		DataStream<String> input = env.readTextFile(path);
		DataStream<Integer> parsed = input.map(new MapFunction<String, Integer>() {
			public Integer map(String value) throws Exception {
				return Integer.parseInt(value);
			}
		});
		parsed.print();
		parsed.writeAsText(path+".txt");
		env.execute("read text file.");

	}


}
