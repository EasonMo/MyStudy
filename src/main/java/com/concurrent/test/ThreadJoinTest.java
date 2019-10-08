package com.concurrent.test;

/**
 * 线程并发同步
 *
 * @author 莫翌成 2018年04月02日
 *
 */
public class ThreadJoinTest {
	static private class PrintComponent {
		public void printCount(final PrintThread thread) {
			for (int i = 0; i < 50; i++) {
				System.out.println(thread + ", count: -- " + i);
			}
		}
	}

	static private class PrintThread implements Runnable {
		private Thread t;
		private final String name;
		private final PrintComponent client;

		private PrintThread(final String name, final PrintComponent client) {
			this.name = name;
			this.client = client;
		}

		public Thread getThread() {
			return t;
		}

		@Override
		public void run() {
			synchronized (client) {
				client.printCount(this);
			}
			System.out.println(name + " end");
		}

		public void start() {
			System.out.println("start : " + name);
			if (t == null) {
				t = new Thread(this, name);
				t.start();
			}
		}

		@Override
		public String toString() {
			return "PrintThread [name=" + name + "]";
		}

	}

	public static void main(final String[] args) throws InterruptedException {
		final PrintComponent c1 = new PrintComponent();

		final PrintThread t1 = new PrintThread("Thread 1", c1);
		t1.start();
		final PrintThread t2 = new PrintThread("Thread 2", c1);
		t2.start();

		t1.getThread().join();
		t2.getThread().join();
		System.out.println("main end");
	}
}
