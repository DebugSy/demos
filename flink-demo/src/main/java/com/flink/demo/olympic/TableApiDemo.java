package com.flink.demo.olympic;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * Created by DebugSy on 2018/3/26.
 */
public class TableApiDemo {

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();
		BatchTableEnvironment tableEnv = BatchTableEnvironment.getTableEnvironment(env);
		DataSource<Record> csvInput = env.readCsvFile("flink-demo/src/main/resources/dataset/games.csv")
				.pojoType(Record.class, "playerName", "country", "year", "game", "gold", "silver", "bronze", "total");

		Table atheltes = tableEnv.fromDataSet(csvInput);
		tableEnv.registerTable("atheltes", atheltes);
		Table groupedByCountry = tableEnv.sql("select country, sum(total) as frequency from atheltes group by country");

		DataSet<Result> result = tableEnv.toDataSet(groupedByCountry, Result.class);
		result.print();


		Table groupedByGame = atheltes.groupBy("game").select("game, total.sum as frequency");
		DataSet<GameResult> gameResult = tableEnv.toDataSet(groupedByGame, GameResult.class);
		gameResult.print();
	}

	public static class Result {
		public String country;
		public Integer frequency;

		public Result() {
			super();
		}

		public Result(String country, Integer total) {
			this.country = country;
			this.frequency = total;
		}

		@Override
		public String toString() {
			return "Result " + country + " " + frequency;
		}
	}

	public static class GameResult {
		public String game;
		public Integer frequency;

		public GameResult(String game, Integer frequency) {
			super();
			this.game = game;
			this.frequency = frequency;
		}

		public GameResult() {
			super();
		}

		@Override
		public String toString() {
			return "GameResult [game=" + game + ", frequency=" + frequency + "]";
		}

	}

}


