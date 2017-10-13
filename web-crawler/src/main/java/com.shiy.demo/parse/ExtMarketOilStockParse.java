package com.shiy.demo.parse;

/**
 * Created by DebugSy on 2017/10/12.
 */

import com.shiy.demo.model.ExtMarketOilStockModel;
import com.shiy.demo.utils.HTTPUtils;
import com.shiy.demo.utils.TimeUtils;
import com.shiy.demo.utils.UumericalUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExtMarketOilStockParse {
	public static List<ExtMarketOilStockModel> parseurl(String url) throws Exception {
		List<ExtMarketOilStockModel> list = new ArrayList<ExtMarketOilStockModel>();
		String response = HTTPUtils.getRawHtml(url, 3000);
		String html = response.toString();
		String jsonarra = html.split("rank:")[1].split(",pages")[0];
		String stocks[] = jsonarra.split("\",");
		List<String> stocklist = new ArrayList<String>();
		for (int i = 0; i < stocks.length; i++) {
			stocklist.add(stocks[i].replace("[\"", "").replace("\"", "").replace("]", ""));
			System.out.println(stocks[i].replace("[\"", "").replace("\"", "").replace("]", ""));
		}
		for (int i = 0; i < stocklist.size(); i++) {
			String date = TimeUtils.GetNowDate("yyyy-MM-dd HH:mm:ss");
			String stock_id = stocklist.get(i).split(",")[1];
			String stock_name = stocklist.get(i).split(",")[2];
			float stock_price = 0;
			float stock_change = 0;
			float stock_range = 0;
			float stock_amplitude = 0;
			int stock_trading_number = 0;
			int stock_trading_value = 0;
			float stock_yesterdayfinish_price = 0;
			float stock_todaystart_price = 0;
			float stock_max_price = 0;
			float stock_min_price = 0;
			float stock_fiveminuate_change = 0;
			if (!stocklist.get(i).split(",")[3].equals("-")) {
				//价格
				stock_price = Float.parseFloat(stocklist.get(i).split(",")[3]);
				//涨跌额
				stock_change = Float.parseFloat(stocklist.get(i).split(",")[4]);
//				System.out.println(stock_change);
				//涨跌幅
				stock_range = UumericalUtil.FloatTO((float) (Float.parseFloat(stocklist.get(i).split(",")[5].replace("%", "")) * 0.01), 4);
				stock_amplitude = UumericalUtil.FloatTO((float) (Float.parseFloat(stocklist.get(i).split(",")[6].replace("%", "")) * 0.01), 4);

				stock_trading_number = Integer.parseInt(stocklist.get(i).split(",")[7].replace("%", ""));
				stock_trading_value = Integer.parseInt(stocklist.get(i).split(",")[8].replace("%", ""));
				stock_yesterdayfinish_price = Float.parseFloat(stocklist.get(i).split(",")[9]);
				stock_todaystart_price = Float.parseFloat(stocklist.get(i).split(",")[10]);
				stock_max_price = Float.parseFloat(stocklist.get(i).split(",")[11]);
				stock_min_price = Float.parseFloat(stocklist.get(i).split(",")[12]);
				stock_fiveminuate_change = UumericalUtil.FloatTO((float) (Float.parseFloat(stocklist.get(i).split(",")[21].replace("%", "")) * 0.01), 4);
//				System.out.println(stock_fiveminuate_change);
			}
			String craw_time = TimeUtils.GetNowDate("yyyy-MM-dd HH:mm:ss");
			ExtMarketOilStockModel model = new ExtMarketOilStockModel();
			model.setDate(date);
			model.setStock_id(stock_id);
			model.setStock_name(stock_name);
			model.setStock_price(stock_price);
			model.setStock_change(stock_change);
			model.setStock_range(stock_range);
			model.setStock_amplitude(stock_amplitude);
			model.setStock_trading_number(stock_trading_number);
			model.setStock_trading_value(stock_trading_value);
			model.setStock_yesterdayfinish_price(stock_yesterdayfinish_price);
			model.setStock_todaystart_price(stock_todaystart_price);
			model.setStock_max_price(stock_max_price);
			model.setStock_min_price(stock_min_price);
			model.setStock_fiveminuate_change(stock_fiveminuate_change);
			model.setCraw_time(craw_time);
			list.add(model);
		}
		return list;
	}
}
