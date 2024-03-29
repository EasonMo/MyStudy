package com.coreJava.jiCheng;

import java.lang.reflect.Method;

/**
 * This program shows how to invoke methods through reflection.
 * 
 * @version 1.1 2004-02-21
 * @author Cay Horstmann
 */
public class MethodPointerTest {
	public static void main(String[] args) throws Exception {
		// get method pointers to the square and sqrt methods
		final Method square = MethodPointerTest.class.getMethod("square",
				double.class);
		final Method sqrt = Math.class.getMethod("sqrt", double.class);

		// print tables of x- and y-values

		printTable(1, 10, 10, square);
		printTable(1, 10, 10, sqrt);
	}

	/**
	 * Prints a table with x- and y-values for a method
	 * 
	 * @param from
	 *            the lower bound for the x-values
	 * @param to
	 *            the upper bound for the x-values
	 * @param n
	 *            the number of rows in the table
	 * @param f
	 *            a method with a double parameter and double return value
	 */
	public static void printTable(double from, double to, int n, Method f) {
		// print out the method as table header
		System.out.println(f);

		final double dx = (to - from) / (n - 1);

		for (double x = from; x <= to; x += dx) {
			try {
				final double y = (Double) f.invoke(null, x);
				System.out.printf("%10.4f | %10.4f%n", x, y);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the square of a number
	 * 
	 * @param x
	 *            a number
	 * @return x squared
	 */
	public static double square(double x) {
		return x * x;
	}
}
