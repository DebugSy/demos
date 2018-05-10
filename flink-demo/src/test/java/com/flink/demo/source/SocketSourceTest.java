package com.flink.demo.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

/**
 * Created by DebugSy on 2018/5/10.
 */
public class SocketSourceTest {

	private StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

	private String host = "localhost";

	private int port = 9001;

	private SocketSource socketSource = new SocketSource(env, host, port);

	@Test
	public void socketSource() throws Exception {
		DataStreamSource streamSource = socketSource.read();
		streamSource.print();

		env.execute("socket source");
	}

}
