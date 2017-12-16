package com.netty.rpc.app.client;

import com.netty.rpc.app.server.HelloService;
import com.netty.rpc.client.RpcClient;
import com.netty.rpc.common.RpcRequest;
import com.netty.rpc.common.RpcResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by DebugSy on 2017/12/14.
 *
 * 未使用代理
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = config.class)
public class HelloServiceTest {

	@Test
	public void helloTest() throws Throwable {

		Method method = HelloService.class.getMethod("hello", String.class);


		//创建RpcRequest，封装被代理类的属性
		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		//拿到声明这个方法的业务接口名称
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParameters(new String[]{"World! xxx "});


		//创建Netty实现的RpcClient，链接服务端
		RpcClient client = new RpcClient("localhost", 8888);
		//通过netty向服务端发送请求
		RpcResponse response = client.send(request);
		//返回信息
		if (response.isError()) {
			throw response.getError();
		} else {
			System.out.println(response.getResult());
		}
	}


}
