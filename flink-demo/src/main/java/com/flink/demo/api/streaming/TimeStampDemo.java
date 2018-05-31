package com.flink.demo.api.streaming;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.io.FileInputFormat;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by DebugSy on 2018/5/30.
 */
public class TimeStampDemo{


	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		String path = "file:///E:/tmp/flink/data";
		PojoTypeInfo<MyEvent> typeInformation = (PojoTypeInfo<MyEvent>)TypeExtractor.getForClass(MyEvent.class);
		FileInputFormat inputFormat = new PojoCsvInputFormat(null, typeInformation, new String[]{"id", "name", "age", "timestamp"});

		DataStreamSource<MyEvent> source = env.readFile(inputFormat, path, FileProcessingMode.PROCESS_CONTINUOUSLY, 100, typeInformation);

		SingleOutputStreamOperator<MyEvent> withTimestampsAndWatermarks = source.filter(event -> event.getAge() != 1)
				//依据记录中的指生成时间戳和水印
				.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<MyEvent>() {
					@Override
					public long extractAscendingTimestamp(MyEvent element) {
						return element.getTimestamp();
					}
				});

		SingleOutputStreamOperator<MyEvent> outputStreamOperator = withTimestampsAndWatermarks.keyBy(event -> event.getAge())
				.timeWindow(Time.seconds(30))
				.max("id");

		outputStreamOperator.map(new MapFunction<MyEvent, Object>() {
			@Override
			public Object map(MyEvent event) throws Exception {
				System.out.println(event.getId() +"\t"+ event.getName() +"\t"+ event.getAge() +"\t" + event.getTimestamp());
				return null;
			}
		});

		env.execute("timestamp demo");

	}

}
