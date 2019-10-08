package com.dataStAndAl.ch06_recursion;

// anagram.java
// creates anagrams
// to run this program: C>java AnagramApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class AnagramApp {
	static int size;
	static int count;
	static char[] arrChar = new char[100];

	// -----------------------------------------------------------
	public static void displayWord() {
		if (count < 99) {
			System.out.print(" ");
		}
		if (count < 9) {
			System.out.print(" ");
		}
		System.out.print(++count + " ");
		for (int j = 0; j < size; j++) {
			System.out.print(arrChar[j]);
		}
		System.out.print("   ");
		System.out.flush();
		if (count % 6 == 0) {
			System.out.println("");
		}
	}

	// -----------------------------------------------------------
	public static void doAnagram(final int newSize) {
		if (newSize == 1) {
			return; // go no further
		}

		for (int j = 0; j < newSize; j++) // for each position,
		{
			doAnagram(newSize - 1); // anagram remaining
			if (newSize == 2) {
				displayWord(); // display it
			}
			rotate(newSize); // rotate word
		}
	}

	// -----------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	public static void main(final String[] args) throws IOException {
		System.out.print("Enter a word: "); // get word
		final String input = getString();
		size = input.length(); // find its size
		count = 0;
		for (int j = 0; j < size; j++) {
			arrChar[j] = input.charAt(j);
		}
		doAnagram(size); // anagram it
	} // end main()
		// -----------------------------------------------------------
		// rotate left all chars from position to end

	public static void rotate(final int newSize) {
		int j;
		final int position = size - newSize;
		final char temp = arrChar[position]; // save first letter
		for (j = position + 1; j < size; j++) {
			arrChar[j - 1] = arrChar[j];
		}
		arrChar[j - 1] = temp; // put first on right
	}
} // end class AnagramApp
	////////////////////////////////////////////////////////////////
