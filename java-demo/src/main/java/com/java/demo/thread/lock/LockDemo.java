package com.java.demo.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  lock和synchronized的区别
 *　1）Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问；
 *　2）Lock和synchronized有一点非常大的不同，采用synchronized不需要用户去手动释放锁，当synchronized方法或者synchronized代码块执行完之后，
 *    系统会自动让线程释放对锁的占用；而Lock则必须要用户去手动释放锁，如果没有主动释放锁，就有可能导致出现死锁现象。
 */
public class LockDemo {

    private static ArrayList arrayList = new ArrayList();

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                Thread thread = Thread.currentThread();

                lock.lock();
                try {
                    System.out.println(thread.getName() + "得到了锁");
                    for (int i = 0; i < 5; i++) {
                        arrayList.add(i);
                    }
                    thread.sleep(3000);
                }catch (Exception e){

                } finally {
                    System.out.println(thread.getName() + "释放了锁");
                    lock.unlock();
                }
            }
        }.start();

        new Thread() {
            public void run() {
                Thread thread = Thread.currentThread();
                lock.lock();
                try {
                    System.out.println(thread.getName() + "得到了锁");
                    for (int i = 0; i < 5; i++) {
                        arrayList.add(i);
                    }
                    thread.sleep(3000);
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    System.out.println(thread.getName() + "释放了锁");
                    lock.unlock();
                }

            };
        }.start();
    }

}
