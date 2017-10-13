package com.shiy.demo.utils;

import com.shiy.demo.model.ExtMarketOilStockModel;
import com.shiy.demo.parse.ExtMarketOilStockParse;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;

/**
 * Created by DebugSy on 2017/10/13.
 */
public class ProxySource {
	
	public Proxy getProxyFromWeb(String url) throws IOException, InterruptedException {
		String response = HTTPUtils.getRawHtml(url, 5000);
		return null;
	}
	
}
