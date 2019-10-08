package com.concurrent.test;

/**
 * @author 莫翌成 2018年06月05日
 */
public class VolatileTest extends Thread {

	// private volatile boolean stop = false;
	private boolean stop = false;

	public static void main(String[] args) throws InterruptedException {
		long a1 = System.currentTimeMillis();
		VolatileTest t = new VolatileTest();
		t.start();
		Thread.sleep(1000);
		t.stopMe();
		System.out.println("time is " + (System.currentTimeMillis() - a1));
		Thread.sleep(1000);
	}

	public void stopMe() {
		stop = true;

	}

	@Override
	public void run() {
		int i = 0;
		while (!stop) {
			i++;
			System.out.println(i);
		}
		System.out.println("stop thread");
	}
}
