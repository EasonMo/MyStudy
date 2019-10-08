package com.concurrent.test;

/**
 * 讨论线程中断状态 线程中断被置位时，调用sleep()
 * 
 * @author 莫翌成 2018年3月28日
 *
 */
public class InterruptTest {
	static private class MyThread implements Runnable {

		@Override
		public void run() {
			System.out.println("thread is running");
			Thread.currentThread().interrupt();
			System.out.println("interrupt flag 1: " + Thread.currentThread().isInterrupted());
			System.out.println("interrupt flag 2: " + Thread.interrupted());
			System.out.println("interrupt flag 3: " + Thread.currentThread().isInterrupted());
			// Thread.currentThread().interrupt();
			try {
				Thread.sleep(5000);
				System.out.println("sleep");
			} catch (final InterruptedException e) {
				System.out.println("enter sleep exception");
				e.printStackTrace();
			}
			// for (int i = 0; i < 1000000000; i++) {
			// new String("");
			// }

			System.out.println("interrupt flag 4: " + Thread.currentThread().isInterrupted());
			System.out.println("thread is over");
		}

	}

	public static void main(final String[] args) throws InterruptedException {
		final MyThread myThread = new MyThread();
		final Thread thread = new Thread(myThread);
		thread.start();
		// System.out.println("interrupt flag: " + thread.isInterrupted());
		Thread.sleep(50);
		thread.interrupt();
		System.out.println("main interrupt");
	}
}
