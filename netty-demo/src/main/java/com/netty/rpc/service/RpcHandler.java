package com.netty.rpc.service;

import com.netty.rpc.common.RpcRequest;
import com.netty.rpc.common.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by DebugSy on 2017/12/14.
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

	private static final Logger LOG = LoggerFactory.getLogger(RpcHandler.class);

	private Map<String, Object> handlerMap;

	public RpcHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOG.error("server caught exception: {}", cause);
		ctx.close();
	}

	protected void messageReceived(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
		LOG.info("receive message : {}", rpcRequest.toString());
		RpcResponse rpcResponse = new RpcResponse();
		rpcRequest.setRequestId(rpcRequest.getRequestId());
		try{
			Object result = handle(rpcRequest);
			rpcResponse.setResult(result);
		} catch (Exception e){
			rpcResponse.setError(e);
		}

		channelHandlerContext.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
	}

	private Object handle(RpcRequest rpcRequest) throws Exception{
		String className = rpcRequest.getClassName();


		//拿到实现类对象
		Object serviceBean = handlerMap.get(className);

		//拿到调用方法的名称，参数类型，参数值
		String methodName = rpcRequest.getMethodName();
		Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
		Object[] parameters = rpcRequest.getParameters();

		//拿到接口类
		Class<?> forName = Class.forName(className);

		//调用实现类对象的指定方法并返回结果
		Method method = forName.getMethod(methodName, parameterTypes);
		Object value = method.invoke(serviceBean, parameters);
		return value;
	}

}
