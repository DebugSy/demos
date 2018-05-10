package com.flink.demo.official;

import org.junit.Test;

import java.net.URL;

/**
 * Created by DebugSy on 2018/5/9.
 */
public class FlinkDemoTest {

	private FlinkDemo flinkDemo = new FlinkDemo();

	@Test
	public void readFile() throws Exception {
		String path = this.getClass().getResource("/datastream/Integer").getPath();
		System.out.println(path);
		flinkDemo.readFile(path);
	}

}
