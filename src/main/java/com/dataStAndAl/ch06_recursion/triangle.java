package com.dataStAndAl.ch06_recursion;

// triangle.java
// evaluates triangular numbers
// to run this program: C>java TriangleApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class TriangleApp {
	static int theNumber;

	// -------------------------------------------------------------
	public static int getInt() throws IOException {
		final String s = getString();
		return Integer.parseInt(s);
	}

	// --------------------------------------------------------------
	// -------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	public static void main(final String[] args) throws IOException {
		System.out.print("Enter a number: ");
		theNumber = getInt();
		final int theAnswer = triangle(theNumber);
		System.out.println("Triangle=" + theAnswer);
	} // end main()
	// -------------------------------------------------------------

	public static int triangle(final int n) {
		if (n == 1) {
			return 1;
		} else {
			return (n + triangle(n - 1));
		}
	}
} // end class TriangleApp
////////////////////////////////////////////////////////////////
