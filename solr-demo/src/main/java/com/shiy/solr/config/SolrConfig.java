package com.shiy.solr.config;


import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.HttpSolrClientFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by DebugSy on 2017/11/27.
 */
@Configuration
@EnableSolrRepositories(basePackages = "com.shiy.solr")
@EnableTransactionManagement
public class SolrConfig {

	@Value("${spring.data.solr.host}")
	private String url;

	@Bean
	public HttpSolrClientFactoryBean solrServerFactoryBean() {
		HttpSolrClientFactoryBean httpSolrClientFactoryBean = new HttpSolrClientFactoryBean();
		httpSolrClientFactoryBean.setUrl("http://localhost:8984/solr/person");
		httpSolrClientFactoryBean.setMaxConnections(30);
		httpSolrClientFactoryBean.setTimeout(5 * 1000);
		return httpSolrClientFactoryBean;
	}

	@Bean
	public SolrClient solrClient() {
		try {
			return solrServerFactoryBean().getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public SolrOperations solrTemplate() {
		return new SolrTemplate(solrClient());
	}

}
