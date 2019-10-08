package com.jvm.classloader;

public class StaticBlockClinitTest {
	// 静态语句在前则先执行
	static {
		i = 0;
		System.out.println("static block");
	}
	static int i = 1;

	public static void main(final String[] args) {
		System.out.println(StaticBlockClinitTest.i);
		final StaticBlockClinitTest test = new StaticBlockClinitTest();
		System.out.println(test.getClass().getClassLoader().getClass().getName());

	}

}
