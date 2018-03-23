package com.flink.demo.olympic;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by DebugSy on 2018/3/23.
 */
public class GamesDemo {

	public static void main(String[] args) {
		ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();

		DataSource<Record> csvInput = env.readCsvFile("flink-demo/src/main/resources/dataset/games.csv").pojoType(Record.class,
				"playerName", "country", "year", "game", "gold", "silver", "bronze", "total");

		AggregateOperator<Tuple2<String, Integer>> groupedByCountry = csvInput.flatMap(new FlatMapFunction<Record, Tuple2<String, Integer>>() {
			public void flatMap(Record record, Collector<Tuple2<String, Integer>> collector) throws Exception {
				collector.collect(new Tuple2<String, Integer>(record.getCountry(), 1));
			}
		}).groupBy(0).sum(1);

		try {
			groupedByCountry.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
