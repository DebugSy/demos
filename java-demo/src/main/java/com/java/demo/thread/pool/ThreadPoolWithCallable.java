package com.java.demo.thread.pool;

import java.util.concurrent.*;

public class ThreadPoolWithCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Callable job = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("thread name: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "b--" + Thread.currentThread().getName();
            }
        };
        for (int i = 0; i < 10; i++) {
            Future<String> future = executorService.submit(job);
            //从Future中get结果，这个方法是会被阻塞的，一直要等到线程任务返回结果
            System.out.println(future.get());
        }
        executorService.shutdown();
    }

}
