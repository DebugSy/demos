package com.shiy.solr.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by DebugSy on 2017/11/24.
 */
public class TestEntity {

	@Field
	private int id;

	@Field
	private String name;

	@Field
	private String descendent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescendent() {
		return descendent;
	}

	public void setDescendent(String descendent) {
		this.descendent = descendent;
	}
}
