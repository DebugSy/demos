package com.netty.demo.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.Date;


/**
 * Created by DebugSy on 2017/12/13.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	private ByteBuf buf;

	/**
	 * channelActive()方法将会在连接被建立并且准备进行通信时被调用
	 * @param ctx
	 * @throws Exception
	 */
//	@Override
//	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//		ByteBuf time = ctx.alloc().buffer(4);
//		time.writeInt((int) (System.currentTimeMillis()/1000L + 2208988800L));
//		System.out.println(time);
//		final ChannelFuture channelFuture = ctx.writeAndFlush(time);
//		channelFuture.addListener(new ChannelFutureListener() {
//			public void operationComplete(ChannelFuture f) throws Exception {
//				assert channelFuture==f;
//				ctx.close();
//			}
//		});
//	}


	/**
	 * ChannelHandler有2个生命周期的监听方法：handlerAdded() handlerRemoved()
	 * 可以完成任意初始化任务只要他不会被阻塞很长的时间。
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		buf = ctx.alloc().buffer(4);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		buf.release();
		buf = null;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("server 读取数据……");
		//读取数据
		buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("接收客户端数据:" + body);
		//向客户端写数据
		System.out.println("server向client发送数据");
		String currentTime = new Date(System.currentTimeMillis()).toString();
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
//		ctx.flush();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server 读取数据完毕..");
		ctx.flush();//刷新后才将数据发出到SocketChannel
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
