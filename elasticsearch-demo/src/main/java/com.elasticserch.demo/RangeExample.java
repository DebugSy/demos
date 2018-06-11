package com.elasticserch.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Created by DebugSy on 2018/5/29.
 */
public class RangeExample {

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
						new HttpHost("localhost", 9300, "http")));

//		client.

		client.close();

	}

}
