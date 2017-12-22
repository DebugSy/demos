package com.java.demo.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {

    private static ArrayList arrayList = new ArrayList();

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                Thread thread = Thread.currentThread();

                boolean tryLock = lock.tryLock();
                System.out.println("是否得到锁：" + tryLock);
                if (tryLock){
                    try {
                        System.out.println(thread.getName() + "得到了锁");
                        for (int i = 0; i < 5; i++) {
                            arrayList.add(i);
                        }
                        thread.sleep(3000);
                    }catch (Exception e){

                    } finally {
                        System.out.println(thread.getName() + "释放了锁");
                        TryLockDemo.lock.unlock();
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                Thread thread = Thread.currentThread();
                boolean tryLock = lock.tryLock();
                System.out.println("是否得到锁：" + tryLock);
                if (tryLock) {
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
                }

            }
        }.start();
    }

}
