package com.concurrent.test;

public class DeadLockTest {
	private static class ThreadDemo1 extends Thread {
		@Override
		public void run() {
			synchronized (Lock1) {
				System.out.println("Thread 1: Holding lock 1...");

				try {
					Thread.sleep(10);
				} catch (final InterruptedException e) {
				}
				System.out.println("Thread 1: Waiting for lock 2...");

				synchronized (Lock2) {
					System.out.println("Thread 1: Holding lock 1 & 2...");
				}
			}
		}
	}

	private static class ThreadDemo2 extends Thread {
		@Override
		public void run() {
			synchronized (Lock2) {
				System.out.println("Thread 2: Holding lock 2...");

				try {
					Thread.sleep(10);
				} catch (final InterruptedException e) {
				}
				System.out.println("Thread 2: Waiting for lock 1...");

				synchronized (Lock1) {
					System.out.println("Thread 2: Holding lock 1 & 2...");
				}
			}
		}
	}

	public static Object Lock1 = new Object();

	public static Object Lock2 = new Object();

	public static void main(final String args[]) {
		final ThreadDemo1 T1 = new ThreadDemo1();
		final ThreadDemo2 T2 = new ThreadDemo2();
		T1.start();
		T2.start();
	}
}
