package com.concurrent.test;

public class ProducerConsumerTest {

	// 生产者消费者的简单实现
	private final String[] products = new String[5];
	int index = 0;

	synchronized String pop() throws Exception {
		while (index == 0) {
			wait();
		}
		final String p = new String(products[index]);
		index--;
		notifyAll();
		return p;
	}

	synchronized void push(final String str) throws Exception {
		while (index == products.length) {
			wait();
		}
		products[index] = str;
		index++;
		notifyAll();
	}

}
