package com.java.interview;

public class TwoThreadWaitNotify {

    private static int start = 1;

    private static boolean ouNum = false;

    public static void main(String[] args) {
        Thread thread1 = new Thread(new OuNum());
        Thread thread2 = new Thread(new JiNum());

        thread1.start();
        thread2.start();
    }

    static class OuNum implements Runnable{

        @Override
        public void run() {
            while (start <= 100){
                synchronized (TwoThreadWaitNotify.class){
                    if (ouNum){
                        System.out.println(Thread.currentThread().getName() + "-> 偶数----" + start);
                        start++;
                        ouNum = false;
                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        }
    }

    static class JiNum implements Runnable{

        @Override
        public void run() {
            while (start <= 100){
                synchronized (TwoThreadWaitNotify.class){
                    if (!ouNum){
                        System.out.println(Thread.currentThread().getName() + "-> 奇数----" + start);
                        start++;
                        ouNum = true;
                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

}
