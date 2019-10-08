package com.bjsxt.thread;

public class ProducerConsumer {
	public static void main(final String[] args) {
		final SyncStack ss = new SyncStack();
		final Producer p = new Producer(ss);
		final Consumer c = new Consumer(ss);
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(c).start();

	}
}

class Consumer implements Runnable {
	SyncStack ss = null;

	Consumer(final SyncStack ss) {
		this.ss = ss;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			final WoTou wt = ss.pop();
			System.out.println("消费了: " + wt + "，计数：" + (i + 1));
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Producer implements Runnable {
	SyncStack ss = null;

	Producer(final SyncStack ss) {
		this.ss = ss;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			final WoTou wt = new WoTou(i);
			ss.push(wt);
			System.out.println("生产了：" + wt);
			try {
				Thread.sleep((int) (Math.random() * 200));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class SyncStack {
	int index = 0;
	WoTou[] arrWT = new WoTou[6];

	public synchronized WoTou pop() {
		while (index == 0) {
			try {
				this.wait();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notifyAll();
		index--;
		return arrWT[index];
	}

	public synchronized void push(final WoTou wt) {
		while (index == arrWT.length) {
			try {
				this.wait();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notifyAll();
		arrWT[index] = wt;
		index++;
	}
}

class WoTou {
	int id;

	WoTou(final int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WoTou : " + id;
	}
}