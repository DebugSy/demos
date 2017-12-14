package com.netty.rpc.service;

import com.netty.rpc.common.RpcDecoder;
import com.netty.rpc.common.RpcEncoder;
import com.netty.rpc.common.RpcRequest;
import com.netty.rpc.common.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by DebugSy on 2017/12/14.
 */
@EnableAutoConfiguration
@ComponentScan({
		"com.netty.rpc.service",
		"com.netty.rpc.annotion",
		"com.netty.rpc.common"
})
@Component
public class RpcServer {

	private static final Logger LOG = LoggerFactory.getLogger(RpcServer.class);

	@Value("${rpc.server.ip:localhost}")
	private String ip;

	@Value("${rpc.server.port:8888}")
	private String port;

	@PostConstruct
	public void init(){
		LOG.info("Rpc Service Starting......");
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel
									.pipeline()
									.addLast(new RpcDecoder(RpcRequest.class))
									.addLast(new RpcEncoder(RpcResponse.class))
									.addLast(new RpcHandler(ContextHolder.getHandlerMap()));
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture channelFuture = bootstrap.bind(ip, Integer.parseInt(port)).sync();
			channelFuture.channel().closeFuture().sync();
			LOG.info("Rpc Service Started!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).profiles()
				.sources(RpcServer.class).web(true).run(args);
	}

}
