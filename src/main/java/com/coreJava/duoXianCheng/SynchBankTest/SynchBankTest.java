package com.coreJava.duoXianCheng.SynchBankTest;

/**
 * This program shows how multiple threads can safely access a data structure.
 * 
 * @version 1.30 2004-08-01
 * @author Cay Horstmann
 */
public class SynchBankTest {
	public static final int NACCOUNTS = 100;

	public static final double INITIAL_BALANCE = 1000;

	public static void main(final String[] args) {
		final Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
		int i;
		for (i = 0; i < NACCOUNTS; i++) {
			final TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
			final Thread t = new Thread(r);
			t.start();
		}
	}
}
