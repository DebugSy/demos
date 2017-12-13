package com.netty.demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Created by DebugSy on 2017/12/13.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	//客户端连接服务器后被调用
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端连接服务器，开始发送数据……");
		byte[] req = "QUERY TIME ORDER".getBytes();//消息
		ByteBuf firstMessage = Unpooled.buffer(req.length);//发送类
		firstMessage.writeBytes(req);//发送
		ctx.writeAndFlush(firstMessage);//flush
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		try{
			long currentTimeMillis = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
			System.out.print("time: ");
			System.out.println(new Date(currentTimeMillis));
			ctx.close();
		}finally {
			byteBuf.release();
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
