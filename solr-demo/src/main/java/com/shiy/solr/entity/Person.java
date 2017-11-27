package com.shiy.solr.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;



/**
 * Created by DebugSy on 2017/11/27.
 */
@SolrDocument(solrCoreName = "person")
public class Person {

	@Id
	private long id;

	@Field
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
