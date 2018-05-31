package com.flink.demo.api.streaming;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * Created by DebugSy on 2018/5/31.
 */
public class SocketTimestampAndWaterDemo {

	public static void main(String[] args) throws Exception {

		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		DataStreamSource<String> source = env.socketTextStream("localhost", 9000);
		SingleOutputStreamOperator sumResult = source.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
			for (String word : value.split(" ")){
				out.collect(new Tuple2<>(word, 1));
			}
		})

				.keyBy(0)
				.timeWindow(Time.seconds(10), Time.seconds(5))
				.sum(1);

		sumResult.print();

		env.execute("window demo");

	}

}
