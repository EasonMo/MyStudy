package com.atGuiGu.reflection;

public class ArithmeticCalcultorImpl implements ArithmeticCalculator {

	@Override
	public int add(int i, int j) {
		// System.out.println("The method add begins with " + i + "," + j);
		final int result = i + j;
		// System.out.println("The method add ends with " + result);
		return result;
	}

	@Override
	public void div(int i, int j) {
		final int result = i / j;
		System.out.println(result);
	}

	@Override
	public void mul(int i, int j) {
		final int result = i * j;
		System.out.println(result);
	}

	@Override
	public int sub(int i, int j) {
		final int result = i - j;
		return result;
	}

}
