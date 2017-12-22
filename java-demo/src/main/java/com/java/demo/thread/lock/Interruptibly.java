package com.java.demo.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Interruptibly {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Interruptibly test = new Interruptibly();
        MyThread thread0 = new MyThread(test);
        MyThread thread1 = new MyThread(test);
        thread0.start();
        thread1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
        System.out.println("=====================");
    }

    public void insert(Thread thread) throws InterruptedException {
        lock.lockInterruptibly();
        try{
            System.out.println(thread.getName() + "得到了锁");
            long startTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();

            while ( (currentTime - startTime) <= Integer.MAX_VALUE){
                currentTime = System.currentTimeMillis();
            }
        }finally {
            System.out.println(thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }
    }

}

class MyThread extends Thread{
    private Interruptibly test = null;

    public MyThread(Interruptibly test) {
        this.test = test;
    }

    @Override
    public void run() {
        try{
            test.insert(Thread.currentThread());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"被中断");
//            e.printStackTrace();
        }
    }
}
