package com.flink.demo.official;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * Created by DebugSy on 2018/5/9.
 */
public class WindowsWordCount {

	public static void main(String[] args) throws Exception {

		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

		DataStream<Tuple2<String, Integer>> dataStream = env.socketTextStream("localhost", 9001)
				.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
					public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
						for (String word : value.split(" ")) {
							out.collect(new Tuple2<String, Integer>(word, 1));
						}
					}
				})
				.keyBy(0)
				.timeWindow(Time.seconds(5))
				.sum(1);

		dataStream.print();

		env.execute("Window WordCount");
	}

}
