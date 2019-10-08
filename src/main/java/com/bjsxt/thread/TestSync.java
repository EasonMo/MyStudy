package com.bjsxt.thread;

public class TestSync implements Runnable {
	public static void main(final String[] args) {
		final TestSync test = new TestSync();
		final Thread t1 = new Thread(test);
		final Thread t2 = new Thread(test);
		t1.setName("t1");
		t2.setName("t2");
		t1.start();
		t2.start();
	}

	Timer timer = new Timer();

	@Override
	public void run() {
		timer.add(Thread.currentThread().getName());
	}
}

class Timer {
	private static int num = 0;

	public synchronized void add(final String name) {
		// synchronized (this) {
		num++;
		try {
			Thread.sleep(1);
		} catch (final InterruptedException e) {
		}
		System.out.println(name + ", 你是第" + num + "个使用timer的线程");
		// }
	}
}