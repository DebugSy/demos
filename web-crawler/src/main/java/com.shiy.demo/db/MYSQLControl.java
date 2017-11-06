package com.shiy.demo.db;

/**
 * Created by DebugSy on 2017/10/12.
 */

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import com.shiy.demo.model.ExtMarketOilStockModel;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MYSQLControl {
	static final Log logger = LogFactory.getLog(MYSQLControl.class);
	static DataSource ds = MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/datacollection?useUnicode=true&characterEncoding=utf8");
	static QueryRunner qr = new QueryRunner(ds);

	//第一类方法
	public static void executeUpdate(String sql) {
		try {
			qr.update(sql);
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	//按照SQL查询单个结果
	public static Object getScalaBySQL(String sql) {

		ResultSetHandler<Object> h = new ScalarHandler<Object>(1);
		Object obj = null;
		try {
			obj = qr.query(sql, h);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;

	}

	//按照SQL查询多个结果
	public static <T> List<T> getListInfoBySQL(String sql, Class<T> type) {
		List<T> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<T>(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//查询一列
	public static List<Object> getListOneBySQL(String sql, String id) {
		List<Object> list = null;

		try {
			list = (List<Object>) qr.query(sql, new ColumnListHandler(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//此种数据库操作方法需要优化
	public static int insertoilStocks(List<ExtMarketOilStockModel> oilstocks) {

		Object[][] params = new Object[oilstocks.size()][17];
		int c = 0;  //success number of update
		int[] sum;
		for (int i = 0; i < oilstocks.size(); i++) {
			params[i][0] = oilstocks.get(i).getDate();
			params[i][1] = oilstocks.get(i).getStock_id();
			params[i][2] = oilstocks.get(i).getStock_name();
			params[i][3] = oilstocks.get(i).getStock_price();
			params[i][4] = oilstocks.get(i).getStock_change();
			params[i][5] = oilstocks.get(i).getStock_range();
			params[i][6] = oilstocks.get(i).getStock_amplitude();
			params[i][7] = oilstocks.get(i).getStock_trading_number();
			params[i][8] = oilstocks.get(i).getStock_trading_value();
			params[i][9] = oilstocks.get(i).getStock_yesterdayfinish_price();
			params[i][10] = oilstocks.get(i).getStock_todaystart_price();
			params[i][11] = oilstocks.get(i).getStock_max_price();
			params[i][12] = oilstocks.get(i).getStock_min_price();
			params[i][13] = oilstocks.get(i).getStock_fiveminuate_change();
			params[i][14] = oilstocks.get(i).getCraw_time();
			params[i][15] = null;
			params[i][16] = null;
		}

		QueryRunner qr = new QueryRunner(ds);
		try {
			sum = qr.batch("INSERT INTO `datacollection`.`ext_market_oil_stock` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println("石油数据入库完毕");

		return c;

	}

	//此种数据库操作方法需要优化
	public static int insertcarStocks(List<ExtMarketOilStockModel> carstocks) {

		int c = 0;  //success number of update
		int[] sum;
		Object[][] params1 = new Object[carstocks.size()][17];
		int c1 = 0; //success number of update
		for (int i = 0; i < carstocks.size(); i++) {
			params1[i][0] = carstocks.get(i).getDate();
			params1[i][1] = carstocks.get(i).getStock_id();
			params1[i][2] = carstocks.get(i).getStock_name();
			params1[i][3] = carstocks.get(i).getStock_price();
			params1[i][4] = carstocks.get(i).getStock_change();
			params1[i][5] = carstocks.get(i).getStock_range();
			params1[i][6] = carstocks.get(i).getStock_amplitude();
			params1[i][7] = carstocks.get(i).getStock_trading_number();
			params1[i][8] = carstocks.get(i).getStock_trading_value();
			params1[i][9] = carstocks.get(i).getStock_yesterdayfinish_price();
			params1[i][10] = carstocks.get(i).getStock_todaystart_price();
			params1[i][11] = carstocks.get(i).getStock_max_price();
			params1[i][12] = carstocks.get(i).getStock_min_price();
			params1[i][13] = carstocks.get(i).getStock_fiveminuate_change();
			params1[i][14] = carstocks.get(i).getCraw_time();
			params1[i][15] = null;
			params1[i][16] = null;
		}
		QueryRunner qr = new QueryRunner(ds);
		try {
			//插入的数据表及数据
			sum = qr.batch("INSERT INTO `datacollection`.`ext_market_car_stock` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params1);

		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println("汽车数据入库完毕");

		return c;

	}

}
