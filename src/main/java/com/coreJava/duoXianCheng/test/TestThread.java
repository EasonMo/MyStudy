package com.coreJava.duoXianCheng.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThread {

	static class Task implements Runnable {
		@Override
		public void run() {
			try {
				final Long duration = (long) (Math.random() * 20);
				System.out.println("Running Task!");
				TimeUnit.SECONDS.sleep(duration);
				System.out.println("finish Task!");
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(final String[] arguments) throws InterruptedException {

		final ExecutorService executor = Executors.newSingleThreadExecutor();

		try {
			executor.submit(new Task());
			System.out.println("Shutdown executor");
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			System.err.println("tasks interrupted");
		} finally {
			if (!executor.isTerminated()) {
				System.err.println("cancel non-finished tasks");
			}
			executor.shutdownNow();
			System.out.println("shutdown finished");
		}
	}
}
