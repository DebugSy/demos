package com.shiy.demo.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DebugSy on 2017/10/12.
 */
public class TimeUtils {

	public static void main(String[] args) throws ParseException {

		String time = getMonth("2002-1-08 14:50:38");
		System.out.println(time);
		System.out.println(getDay("2002-1-08 14:50:38"));
		System.out.println(TimeUtils.parseTime("2016-05-19 19:17", "yyyy-MM-dd HH:mm"));

	}

	//get current time
	public static String GetNowDate(String formate) {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public static String getMonth(String time) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {

			date = sdf.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(date);

	}

	public static String getDay(String time) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {

			date = sdf.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(date);

	}

	public static Date parseTime(String inputTime) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(inputTime);

		return date;

	}

	public static String dateToString(Date date, String type) {
		DateFormat df = new SimpleDateFormat(type);
		return df.format(date);
	}

	public static Date parseTime(String inputTime, String timeFormat) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		Date date = sdf.parse(inputTime);

		return date;

	}

	public static Calendar parseTimeToCal(String inputTime, String timeFormat) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		Date date = sdf.parse(inputTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;

	}

	public static int getDaysBetweenCals(Calendar cal1, Calendar cal2) throws ParseException {

		return (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 24 * 3600));

	}

	public static Date parseTime(long inputTime) {

		//  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(inputTime);
		return date;

	}

	public static String parseTimeString(long inputTime) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(inputTime);
		return sdf.format(date);

	}

	public static String parseStringTime(String inputTime) {

		String date = null;
		try {
			Date date1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(inputTime);
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	public static List<String> YearMonth(int year) {
		List<String> yearmouthlist = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			DecimalFormat dfInt = new DecimalFormat("00");
			String sInt = dfInt.format(i);
			yearmouthlist.add(year + sInt);
		}

		return yearmouthlist;
	}

	public static List<String> YearMonth(int startyear, int finistyear) {
		List<String> yearmouthlist = new ArrayList<String>();
		for (int i = startyear; i < finistyear + 1; i++) {
			for (int j = 1; j < 13; j++) {
				DecimalFormat dfInt = new DecimalFormat("00");
				String sInt = dfInt.format(j);
				yearmouthlist.add(i + "-" + sInt);
			}
		}
		return yearmouthlist;
	}

	public static List<String> TOAllDay(int year) {
		List<String> daylist = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int m = 1;//月份计数
		while (m < 13) {
			int month = m;
			Calendar cal = Calendar.getInstance();//获得当前日期对象
			cal.clear();//清除信息
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month - 1);//1月从0开始
			cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天

			System.out.println("##########___" + sdf.format(cal.getTime()));

			int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			System.out.println("$$$$$$$$$$________" + count);

			for (int j = 0; j <= (count - 2); ) {
				cal.add(Calendar.DAY_OF_MONTH, +1);
				j++;
				daylist.add(sdf.format(cal.getTime()));
			}
			m++;
		}
		return daylist;
	}

	//获取昨天的日期
	public static String getyesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}
}
