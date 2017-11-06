package com.shiy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by DebugSy on 2017/10/12
 */

@ComponentScan(basePackages = "com.shiy.demo")
@EnableAutoConfiguration
public class WebCrawlerApp {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApp.class, args);
	}

}
