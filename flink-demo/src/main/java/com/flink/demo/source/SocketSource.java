package com.flink.demo.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by DebugSy on 2018/5/9.
 */
public class SocketSource implements Source {

	private StreamExecutionEnvironment env;

	private String host;

	private int port;

	public SocketSource(StreamExecutionEnvironment env, String host, int port) {
		this.env = env;
		this.host = host;
		this.port = port;
	}

	public DataStreamSource read() {
		DataStreamSource<String> streamSource = env.socketTextStream(host, port);
		return streamSource;
	}

}
