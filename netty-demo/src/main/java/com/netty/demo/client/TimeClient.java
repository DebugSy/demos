package com.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by DebugSy on 2017/12/13.
 */
public class TimeClient {

	public static void main(String[] args) throws InterruptedException {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		EventLoopGroup worker = new NioEventLoopGroup();// EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接、接受数据、发送数据
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(worker)//多线程处理
					.channel(NioSocketChannel.class)//指定通道类型为NioServerSocketChannel，一种异步模式，OIO阻塞模式为OioServerSocketChannel
//					.remoteAddress(new InetSocketAddress(host, port))//地址， 与下面的connect(host, port)一样
					.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {//业务处理类
				protected void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new TimeClientHandler());//注册handler
				}
			});

			//start the client.
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();// 链接服务器

			//wait until the connection is closed.
			channelFuture.channel().closeFuture().sync();

		}finally {
			worker.shutdownGracefully();
		}
	}

}
