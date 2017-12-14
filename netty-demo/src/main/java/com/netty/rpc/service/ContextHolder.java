package com.netty.rpc.service;

import com.netty.rpc.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DebugSy on 2017/12/14.
 */
@Component
public class ContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private static Map<String, Object> handlerMap = new HashMap<String, Object>();

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.applicationContext = ctx;

		Map<String, Object> beansMap = ctx.getBeansWithAnnotation(RpcService.class);
		if ( beansMap != null && !beansMap.isEmpty()){
			for (Object bean : beansMap.values()){
				String interfaceName = bean.getClass().getAnnotation(RpcService.class).value().getName();
				handlerMap.put(interfaceName, bean);
			}
		}
	}

	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

	public static Object getHandlerBean(String beanName){
		return handlerMap.get(beanName);
	}

	public static Map<String, Object> getHandlerMap(){
		return handlerMap;
	}
}
