package com.concurrent.test;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class SemaphreTest {
	static private class Printer implements Runnable {
		private final Semaphore gate;
		private int i = 0;
		private Thread t;

		private Printer() {
			this.gate = new Semaphore(1);
		}

		public void goon() {
			gate.release();
		}

		public void print() {
			while (i < 10) {
				System.out.println("step : " + i);
				i++;
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					gate.acquire();
					print();

				}
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

		}

		public void start() {
			t = new Thread(this);
			t.start();
		}

	}

	public static void main(final String[] args) {
		final Printer printer = new Printer();
		printer.start();

		final Scanner in = new Scanner(System.in);
		while (true) {
			if (in.nextLine().equals("1")) {
				printer.goon();
			}
		}

	}
}
