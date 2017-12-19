package com.java.demo.RMI.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * Created by DebugSy on 2017/8/16.
 */
@Configuration
@ComponentScan("com.java.demo.RMI.spring")
public class RmiConfig {

	@Bean
	public RmiServiceExporter rmiServiceExporter(SpitterService spitterService){
		RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
		rmiServiceExporter.setService(spitterService);
		rmiServiceExporter.setServiceName("SpitterService");
		rmiServiceExporter.setRegistryHost("localhost");
		rmiServiceExporter.setRegistryPort(9999);
		return rmiServiceExporter;
	}

//	@Bean
//	public RmiProxyFactoryBean spitterService(){
//		RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
//		rmiProxy.setServiceUrl("rmi://localhost/SpitterService");
//		rmiProxy.setServiceInterface(SpitterService.class);
//		return rmiProxy;
//	}

}
