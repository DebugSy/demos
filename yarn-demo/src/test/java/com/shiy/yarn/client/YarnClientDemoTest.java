package com.shiy.yarn.client;

import com.shiy.yarn.client.YarnClientDemo;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by DebugSy on 2017/11/6.
 */
public class YarnClientDemoTest {

	@Test
	public void testGetQueueInfo(){
		YarnClientDemo yarnClientDemo = new YarnClientDemo();
		try {
			yarnClientDemo.getQueueInfo();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (YarnException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTotalInfo(){
		YarnClientDemo yarnClientDemo = new YarnClientDemo();
		yarnClientDemo.getTotalInfo();
	}


}
