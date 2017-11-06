package com.shiy.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by DebugSy on 2017/10/12.
 */
@Entity
public class ExtMarketOilStockModel {
	@Column(name="date")
	private String date;

	@Id
	private String stock_id;

	@Column(name="stock_name")
	private String stock_name;

	@Column(name="stock_price")
	private float stock_price;

	@Column(name="stock_change")
	private float stock_change;

	@Column(name="stock_range")
	private float stock_range;

	@Column(name="stock_amplitude")
	private float stock_amplitude;

	@Column(name="stock_trading_number")
	private int stock_trading_number;

	@Column(name="stock_trading_value")
	private int stock_trading_value;

	@Column(name="stock_yesterdayfinish_price")
	private float stock_yesterdayfinish_price;

	@Column(name="stock_todaystart_price")
	private float stock_todaystart_price;

	@Column(name="stock_max_price")
	private float stock_max_price;

	@Column(name="stock_min_price")
	private float stock_min_price;

	@Column(name="stock_fiveminuate_change")
	private float stock_fiveminuate_change;

	@Column(name="craw_time")
	private String craw_time;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStock_id() {
		return stock_id;
	}

	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public float getStock_price() {
		return stock_price;
	}

	public void setStock_price(float stock_price) {
		this.stock_price = stock_price;
	}

	public float getStock_change() {
		return stock_change;
	}

	public void setStock_change(float stock_change) {
		this.stock_change = stock_change;
	}

	public float getStock_range() {
		return stock_range;
	}

	public void setStock_range(float stock_range) {
		this.stock_range = stock_range;
	}

	public float getStock_amplitude() {
		return stock_amplitude;
	}

	public void setStock_amplitude(float stock_amplitude) {
		this.stock_amplitude = stock_amplitude;
	}

	public int getStock_trading_number() {
		return stock_trading_number;
	}

	public void setStock_trading_number(int stock_trading_number) {
		this.stock_trading_number = stock_trading_number;
	}

	public int getStock_trading_value() {
		return stock_trading_value;
	}

	public void setStock_trading_value(int stock_trading_value) {
		this.stock_trading_value = stock_trading_value;
	}

	public float getStock_yesterdayfinish_price() {
		return stock_yesterdayfinish_price;
	}

	public void setStock_yesterdayfinish_price(float stock_yesterdayfinish_price) {
		this.stock_yesterdayfinish_price = stock_yesterdayfinish_price;
	}

	public float getStock_todaystart_price() {
		return stock_todaystart_price;
	}

	public void setStock_todaystart_price(float stock_todaystart_price) {
		this.stock_todaystart_price = stock_todaystart_price;
	}

	public float getStock_max_price() {
		return stock_max_price;
	}

	public void setStock_max_price(float stock_max_price) {
		this.stock_max_price = stock_max_price;
	}

	public float getStock_min_price() {
		return stock_min_price;
	}

	public void setStock_min_price(float stock_min_price) {
		this.stock_min_price = stock_min_price;
	}

	public float getStock_fiveminuate_change() {
		return stock_fiveminuate_change;
	}

	public void setStock_fiveminuate_change(float stock_fiveminuate_change) {
		this.stock_fiveminuate_change = stock_fiveminuate_change;
	}

	public String getCraw_time() {
		return craw_time;
	}

	public void setCraw_time(String craw_time) {
		this.craw_time = craw_time;
	}
}
