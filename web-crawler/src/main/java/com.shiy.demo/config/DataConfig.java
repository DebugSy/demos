package com.shiy.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by DebugSy on 2017/10/12.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.shiy.demo.repository")
public class DataConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/datacollection?useUnicode=true&characterEncoding=utf8");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter adapter(){
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabase(Database.MYSQL);
		jpaAdapter.setShowSql(true);
		jpaAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		return jpaAdapter;
	}

	@Bean(name = "entityManagerFactorya")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryaaa(DataSource dataSource, JpaVendorAdapter adapter) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(adapter);
		emfb.setPackagesToScan("com.shiy.demo.model");
//		emfb.setPersistenceUnitName("stock");
		return emfb;
	}

//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
//		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//		emf.setDataSource(dataSource);
////		emf.setPersistenceUnitName("spittr");
//		emf.setJpaVendorAdapter(jpaVendorAdapter);
//		emf.setPackagesToScan("com.shiy.demo.model");
//		return emf;
//	}

//	@Bean
//	public PersistenceAnnotationBeanPostProcessor paPostProcessor(){
//		return new PersistenceAnnotationBeanPostProcessor();
//	}

	@Configuration
	@EnableTransactionManagement
	public static class TransactionConfig {

		@Autowired
		private EntityManagerFactory emf;

		@Bean
		public PlatformTransactionManager transactionManager() {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(emf);
			return transactionManager;
		}
	}

}


