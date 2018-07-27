package com.java.demo.thread.pool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DebugSy on 2018/7/17.
 */
public class CountdownLatchDemo {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 10; i++) {
			executorService.execute(() -> {
				System.out.println("run... " + Thread.currentThread().getName());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
				System.out.println("countDownLatch's size : " + countDownLatch.getCount());
			});
		}
		countDownLatch.await();
		System.out.println("end");
		executorService.shutdown();
	}

}
