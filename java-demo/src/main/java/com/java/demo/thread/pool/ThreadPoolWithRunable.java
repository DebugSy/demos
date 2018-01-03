package com.java.demo.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolWithRunable {

    /**
     *
     *  Executors.newSingleThreadExecutor() 只有一个线程的线程池，因此所有提交的任务是顺序执行
     *  Executors.newCachedThreadPool() 线程池里有很多线程需要同时执行，老的可用线程将被新的任务触发重新执行，如果线程超过60秒内没执行，那么将被终止并从池中删除
     *  Executors.newFixedThreadPool(4) 拥有固定线程数的线程池，如果没有任务执行，那么线程会一直等待
     *  Executors.newScheduledThreadPool() 用来调度即将执行的任务的线程池，可能是不是直接执行, 每隔多久执行一次... 策略型的
     *  Executors.newSingleThreadScheduledExecutor() 只有一个线程，用来调度任务在指定时间执行
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable job = new Runnable() {
            @Override
            public void run() {
                System.out.println("thread name: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 5; i++) {
            executorService.execute(job);
        }
        executorService.shutdown();
    }

}
