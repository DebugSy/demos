package com.shiy.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by DebugSy on 2017/10/12.
 */
public class ProxyUtil {

	public static void main(String[] args) throws UnsupportedEncodingException {
		//输入代理ip，端口，及所要爬取的url
		gethtml("121.31.101.121",8123,"http://club.autohome.com.cn/bbs/forum-c-2533-1.html?orderby=dateline&qaType=-1");

	}
	public static String gethtml(String ip,int port,String url) throws UnsupportedEncodingException{
		URL url1 = null;
		try {
			url1 = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		InetSocketAddress addr = null;
		//代理服务器的ip及端口
		addr = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
		InputStream in = null;
		try {
			URLConnection conn = url1.openConnection(proxy);
			conn.setConnectTimeout(100000);
			in = conn.getInputStream();
		} catch (Exception e) {
			System.out.println("ip " + " is not aviable");//异常IP
		}

		String s = convertStreamToString(in);
		System.out.println(s);
		return s;

	}
	public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
		if (is == null)
			return "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"gb2312"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

}
