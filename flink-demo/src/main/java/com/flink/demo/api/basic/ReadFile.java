package com.flink.demo.api.basic;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.function.Predicate;

/**
 * Created by DebugSy on 2018/5/30.
 */
public class ReadFile {

	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		String path = ReadFile.class.getResource("/datastream/data.csv").getPath();
		DataStreamSource<String> source = env.readTextFile(path);
		DataStream<Tuple3<Integer,String,Integer>> splited = source.map(ReadFile::map);
		KeyedStream<Tuple3<Integer, String, Integer>, Tuple> keyedStream = splited.keyBy(2);
		keyedStream.print();
		JobExecutionResult result = env.execute("read file");
		long netRuntime = result.getNetRuntime();
		System.out.println(netRuntime);
	}

	private static Tuple3<Integer, String, Integer> map(String line) {
		String[] strings = line.split(" ");
		return new Tuple3(Integer.valueOf(strings[0]), strings[1], Integer.valueOf(strings[2]));
	}
}