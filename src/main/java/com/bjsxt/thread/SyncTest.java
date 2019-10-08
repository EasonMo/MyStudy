package com.bjsxt.thread;

/*	范例名称：生产者--消费者问题
 * 	源文件名称：SyncTest.java
 *	要  点：
 *		1. 共享数据的不一致性/临界资源的保护
 *		2. Java对象锁的概念
 *		3. synchronized关键字/wait()及notify()方法
 */

public class SyncTest {
	public static void main(final String args[]) {
		final SyncStack1 stack = new SyncStack1();
		final Runnable p = new Producer1(stack);
		final Runnable c = new Consumer1(stack);
		final Thread t1 = new Thread(p);
		final Thread t2 = new Thread(c);
		t1.start();
		t2.start();
	}
}

class Consumer1 implements Runnable {
	SyncStack1 stack;

	public Consumer1(final SyncStack1 s) {
		stack = s;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			final char c = stack.pop();
			System.out.println("消费：" + c);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (final InterruptedException e) {
			}
		}
	}
}

class Producer1 implements Runnable {
	SyncStack1 stack;

	public Producer1(final SyncStack1 s) {
		stack = s;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			final char c = (char) (Math.random() * 26 + 'A');
			stack.push(c);
			System.out.println("produced：" + c);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (final InterruptedException e) {
			}
		}
	}
}

class SyncStack1 { // 支持多线程同步操作的堆栈的实现
	private int index = 0;
	private final char[] data = new char[6];

	public synchronized char pop() {
		if (index == 0) {
			try {
				this.wait();
			} catch (final InterruptedException e) {
			}
		}
		this.notify();
		index--;
		return data[index];
	}

	public synchronized void push(final char c) {
		if (index == data.length) {
			try {
				this.wait();
			} catch (final InterruptedException e) {
			}
		}
		this.notify();
		data[index] = c;
		index++;
	}
}
