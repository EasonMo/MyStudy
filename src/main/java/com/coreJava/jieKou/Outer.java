package com.coreJava.jieKou;

public class Outer {
	public class Inner {
		public void a() {
			// System.out.println(str + num);
		}
	}

	private String str;
	private int num;

	public void test() {
		new Inner().a();
	}
}
