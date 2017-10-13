package com.shiy.demo;

import com.shiy.demo.utils.ProxySource;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by DebugSy on 2017/10/13.
 */
public class ProxySourceTest {

	@Test
	public void getProxyFromWeb(){
		ProxySource proxySource = new ProxySource();
		try {
			proxySource.getProxyFromWeb("http://www.xicidaili.com/nn/1");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
