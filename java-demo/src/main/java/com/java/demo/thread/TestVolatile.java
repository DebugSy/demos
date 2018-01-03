package com.java.demo.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile不是线程安全的
 */
public class TestVolatile {

	public static volatile AtomicInteger numb = new AtomicInteger(0);

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						numb.addAndGet(1);
					}
				}
			}).start();

		}
		
		Thread.sleep(2000);
		System.out.println(numb);
	}

}
