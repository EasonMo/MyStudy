package com.concurrent.test;

/**
 * 两个线程交换数据，synchronized方式实现
 * 
 * @author 莫翌成 2018年3月28日
 *
 */
public class SynchronizedTest {

	static class CommunitationClient {
		boolean flag = false;

		synchronized public void answer(final String msg) {
			while (!flag) {
				try {
					wait();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(msg);
			flag = false;
			notifyAll();

		}

		synchronized public void question(final String msg) {

			while (flag) {
				try {
					wait();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(msg);
			flag = true;
			notifyAll();
		}
	}

	static class T1 implements Runnable {
		private final CommunitationClient m;

		public T1(final CommunitationClient m) {
			this.m = m;
			new Thread(this, "question").start();
		}

		@Override
		public void run() {
			final String[] msgList = new String[] { "1?", "2?", "3?" };

			for (final String msg : msgList) {
				m.question(msg);
			}
		}

	}

	static class T2 implements Runnable {
		private final CommunitationClient m;

		public T2(final CommunitationClient m) {
			this.m = m;
			new Thread(this, "answer").start();
		}

		@Override
		public void run() {
			final String[] msgList = new String[] { "1!", "2!", "3!" };

			for (final String msg : msgList) {
				m.answer(msg);
			}
		}
	}

	public static void main(final String[] args) {
		final CommunitationClient client = new CommunitationClient();

		new T1(client);
		new T2(client);

	}
}
