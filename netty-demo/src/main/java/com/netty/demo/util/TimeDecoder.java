package com.netty.demo.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by DebugSy on 2017/12/13.
 */
public class TimeDecoder extends ByteToMessageDecoder {
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readableBytes() <= 4) {
			return;
		}
		list.add(byteBuf.readBytes(4));
	}
}
