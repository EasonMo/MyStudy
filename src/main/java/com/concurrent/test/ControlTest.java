package com.concurrent.test;

/**
 * 线程状态控制
 *
 * @author 莫翌成 2018年3月28日
 *
 */
public class ControlTest {
	static class RunnableDemo implements Runnable {
		private final String name;
		private Thread t;
		boolean suspended = false;

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
					Thread.sleep(500);
					synchronized (this) {
						while (suspended) {
							wait();
						}
					}
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

		synchronized void resume() {
			suspended = false;
			notify();// 只能在同步方法或同步块中
		}

		void suspend() {
			suspended = true;
		}

	}

	public static void main(final String args[]) {

		final RunnableDemo R1 = new RunnableDemo("Thread-1");
		R1.start();

		final RunnableDemo R2 = new RunnableDemo("Thread-2");
		R2.start();

		try {
			Thread.sleep(1000);
			R1.suspend();
			System.out.println("Suspending First Thread");
			Thread.sleep(1000);
			R1.resume();
			System.out.println("Resuming First Thread");

			R2.suspend();
			System.out.println("Suspending thread Two");
			Thread.sleep(1000);
			R2.resume();
			System.out.println("Resuming thread Two");
		} catch (final InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}
		try {
			System.out.println("Waiting for threads to finish.");
			R1.t.join();
			R2.t.join();
		} catch (final InterruptedException e) {
			System.out.println("Main thread Interrupted");
		}
		System.out.println("Main thread exiting.");
	}
}
