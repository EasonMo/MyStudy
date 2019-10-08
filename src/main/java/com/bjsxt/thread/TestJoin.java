package com.bjsxt.thread;

public class TestJoin {
	public static void main(final String[] args) throws InterruptedException {
		final MyThread1 t1 = new MyThread1("abcde");
		t1.start();
		// try {
		t1.join();
		// } catch (final InterruptedException e) {
		// }

		for (int i = 1; i <= 10; i++) {
			Thread.sleep(500);
			System.out.println("i am main thread");
		}
	}
}

class MyThread1 extends Thread {
	MyThread1(final String s) {
		super(s);
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			System.out.println("i am " + getName());
			try {
				sleep(1000);
			} catch (final InterruptedException e) {
				return;
			}
		}
	}
}
