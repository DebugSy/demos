package com.flink.demo.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by DebugSy on 2018/5/9.
 */
public class FileSourceTest {

	private final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

	private String path = this.getClass().getResource("/datastream/").getPath();

	private FileSource fileSource = new FileSource(env, path);

	@Test
	public void readTextFile() throws Exception {
		String path = this.getClass().getResource("/datastream/Integer").getPath();
		DataStreamSource streamSource = fileSource.read();
		streamSource.print();

		env.execute();
	}

	/**
	 * 测试增量读取文件及其子目录
	 * Junit不会执行增量读取，得在main函数中写
	 * @throws Exception
	 */
	@Test
	public void readTextFileContinuously() throws Exception {
		String path = this.getClass().getResource("/datastream").getPath();
		DataStreamSource streamSource = fileSource.read(FileProcessingMode.PROCESS_CONTINUOUSLY);
		streamSource.print();

		env.execute("read file continuously");
	}

	@Test
	public void testSplit(){
		String msg = "a 1527753444";
		String[] split = msg.split("\\s");
		for (String str : split){
			System.out.println(str + "&");
		}

	}

}
