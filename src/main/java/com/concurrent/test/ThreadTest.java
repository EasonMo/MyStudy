package com.concurrent.test;

/**
 * 实现Runnable接口创建一个线程
 * 
 * @author 莫翌成 2018年3月22日
 *
 */
public class ThreadTest {
	static class RunnableDemo implements Runnable {
		private final String name;

		private Thread t;

		public RunnableDemo(final String name) {
			this.name = name;
			System.out.println("Creating " + name);
		}

		@Override
		public void run() {
			System.out.println("Running " + name);
			try {
				for (int i = 0; i < 5; i++) {
					System.out.println("Thread: " + name + ", " + i);
					Thread.sleep(50);
				}
			} catch (final InterruptedException e) {
				System.out.println("Thread " + name + "interupted.");
			}
			System.out.println("Thread " + name + "exiting.");
		}

		public void start() {
			System.out.println("starting " + name);
			if (t == null) {
				t = new Thread(this, name);
				t.start();
			}
		}

	}

	public static void main(final String[] args) throws InterruptedException {
		final RunnableDemo r1 = new RunnableDemo("Thread-1");
		r1.start();

		final RunnableDemo r2 = new RunnableDemo("Thread-2");
		r2.start();
		// Thread.sleep(3000);
		System.out.println("main exit.");
	}
}
