package com.jvm.memory;

/**
 * @author 莫翌成 2018年06月05日
 */
public class OnStackTest {

	public static void alloc() {
		byte[] b = new byte[2];
		b[0] = 1;
	}

	public static void main(String[] args) {
		long b = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			alloc();
		}

		System.out.println(System.currentTimeMillis() - b);
	}
}
