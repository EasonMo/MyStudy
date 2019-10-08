package com.jvm.engine;

public class LocalVariableTableTest {
	public static void main(final String[] args) {
		// 加花括号，a的作用域被限定在括号内
		{
			final byte[] a = new byte[64 * 1024 * 1024];
		}

		final int b = 0;
		System.gc();

	}
}
