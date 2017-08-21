package com.shiy.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * Created by DebugSy on 2017/8/17.
 */
@Configuration
//@ComponentScan("com.shiy.demo.jdbcTemplate.data")//jpa配置
//spring-data，会自动查找拓展自Repository的接口，并为其自动实现（在应用启动的时候）
@EnableJpaRepositories(basePackages = "com.shiy.demo")
public class DataConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/shiy?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}

//	@Bean
//	public JdbcTemplate jdbcTemplate(DataSource dataSource){
//		return new JdbcTemplate(dataSource);
//	}

	@Bean
	public JpaVendorAdapter adapter(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("com.shiy.demo.entity");//这里会去扫描带有@Entity注解的类
		return emfb;
	}

	/**
	 * 注册@PersistenceContext，因为PersistenceContext不是spring的注解
	 */
	@Bean
	public PersistenceAnnotationBeanPostProcessor paPostProcessor(){
		return new PersistenceAnnotationBeanPostProcessor();
	}


}
