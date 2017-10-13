package com.shiy.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by DebugSy on 2017/10/13.
 */
@Entity
public class Proxy {

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String ip;

	@Column
	private String port;

	@Column
	private String address;

	@Column
	private boolean highHide;

	@Column
	private Date validateDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isHighHide() {
		return highHide;
	}

	public void setHighHide(boolean highHide) {
		this.highHide = highHide;
	}

	public Date getValidateDate() {
		return validateDate;
	}

	public void setValidateDate(Date validateDate) {
		this.validateDate = validateDate;
	}
}
