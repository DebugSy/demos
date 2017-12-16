package com.netty.rpc.app.client;

import com.netty.rpc.app.server.HelloService;
import com.netty.rpc.client.RpcProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by DebugSy on 2017/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = config.class)
public class HelloServiceTestProxy {

	@Autowired
	private RpcProxy rpcProxy;

	@Test
	public void helloTest1() {
		// 调用代理的create方法，代理HelloService接口
		HelloService helloService = rpcProxy.create(HelloService.class);

		// 调用代理的方法，执行invoke
		String result = helloService.hello("World aaa");
		System.out.println("服务端返回结果：");
		System.out.println(result);
	}

}
