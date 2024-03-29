package com.coreJava.duoXianCheng.SynchBankTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A bank with a number of bank accounts that uses locks for serializing access.
 * 
 * @version 1.30 2004-08-01
 * @author Cay Horstmann
 */
public class Bank {
	private final double[] accounts;

	private final Lock bankLock;

	private final Condition sufficientFunds;

	/**
	 * Constructs the bank.
	 * 
	 * @param n
	 *            the number of accounts
	 * @param initialBalance
	 *            the initial balance for each account
	 */
	public Bank(final int n, final double initialBalance) {
		accounts = new double[n];
		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = initialBalance;
		}
		bankLock = new ReentrantLock();
		sufficientFunds = bankLock.newCondition();
	}

	/**
	 * Gets the sum of all account balances.
	 * 
	 * @return the total balance
	 */
	public double getTotalBalance() {
		bankLock.lock();
		try {
			double sum = 0;

			for (final double a : accounts) {
				sum += a;
			}

			return sum;
		} finally {
			bankLock.unlock();
		}
	}

	/**
	 * Gets the number of accounts in the bank.
	 * 
	 * @return the number of accounts
	 */
	public int size() {
		return accounts.length;
	}

	/**
	 * Transfers money from one account to another.
	 * 
	 * @param from
	 *            the account to transfer from
	 * @param to
	 *            the account to transfer to
	 * @param amount
	 *            the amount to transfer
	 */
	public void transfer(final int from, final int to, final double amount) throws InterruptedException {
		bankLock.lock();
		try {
			while (accounts[from] < amount) {
				sufficientFunds.await();
			}
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf(" %10.2f from %d to %d", amount, from, to);
			accounts[to] += amount;
			System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
			sufficientFunds.signalAll();
		} finally {
			bankLock.unlock();
		}
	}
}
