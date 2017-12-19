package com.java.demo.thread;

/**
 * Created by DebugSy on 2017/11/20.
 *
 * synchronized 测试
 *
 */
public class SynchronizedTest {

	private int number = 0;

	public void numAdd(int a){
		number+=a;
	}

	public void numAdd2(int a){
		number+=1;
	}

	public synchronized void forEachAdd(int forEachNum){
		for (int i = 0; i < forEachNum; i++) {
			numAdd(i);
			System.out.println(Thread.currentThread().getName() + " " + number);
		}
		System.out.println(Thread.currentThread().getName() + " : result ===> " + number);
	}

	public synchronized void forEachAdd2(int forEachNum){
		for (int i = 0; i < forEachNum; i++) {
			numAdd2(i);
			System.out.println(Thread.currentThread().getName() + " " + number);
		}
		System.out.println(Thread.currentThread().getName() + " : result ===> " + number);
	}

}

class ThreadTest extends Thread{

	private SynchronizedTest synchronizedTest;

	private int type;

	public ThreadTest(SynchronizedTest synchronizedTest, int type) {
		this.synchronizedTest = synchronizedTest;
		this.type = type;
	}

	@Override
	public void run() {
		if (type == 1){
			synchronizedTest.forEachAdd(100);
		} else {
			synchronizedTest.forEachAdd2(100);
		}

	}

	public static void main(String[] args) {
		SynchronizedTest so = new SynchronizedTest();

		ThreadTest threadTest01 = new ThreadTest(so, 1);
		threadTest01.setName("threadTest01");
		threadTest01.start();

		ThreadTest threadTest02 = new ThreadTest(so, 2);
		threadTest02.setName("threadTest02");
		threadTest02.start();

	}
}


