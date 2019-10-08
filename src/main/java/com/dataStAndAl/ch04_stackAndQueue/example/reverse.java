package com.dataStAndAl.ch04_stackAndQueue.example;

// reverse.java
// stack used to reverse a string
// to run this program: C>java ReverseApp
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class ReverseApp {
	// --------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	// --------------------------------------------------------------
	public static void main(final String[] args) throws IOException {
		String input, output;
		while (true) {
			System.out.print("Enter a string: ");
			System.out.flush();
			input = getString(); // read a string from kbd
			if (input.equals("")) {
				break;
			}
			// make a Reverser
			final Reverser theReverser = new Reverser(input);
			output = theReverser.doRev(); // use it
			System.out.println("Reversed: " + output);
		} // end while
	} // end main()
} // end class ReverseApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class Reverser {
	private final String input; // input string
	private String output; // output string
	// --------------------------------------------------------------

	public Reverser(final String in) // constructor
	{
		input = in;
	}

	// --------------------------------------------------------------
	public String doRev() // reverse the string
	{
		final int stackSize = input.length(); // get max stack size
		final StackXr theStack = new StackXr(stackSize); // make stack

		for (int j = 0; j < input.length(); j++) {
			final char ch = input.charAt(j); // get a char from input
			theStack.push(ch); // push it
		}
		output = "";
		while (!theStack.isEmpty()) {
			final char ch = theStack.pop(); // pop a char,
			output = output + ch; // append to output
		}
		return output;
	} // end doRev()
		// --------------------------------------------------------------
} // end class Reverser
	////////////////////////////////////////////////////////////////

class StackXr {
	private final int maxSize;
	private final char[] stackArray;
	private int top;

	// --------------------------------------------------------------
	public StackXr(final int max) // constructor
	{
		maxSize = max;
		stackArray = new char[maxSize];
		top = -1;
	}

	// --------------------------------------------------------------
	public boolean isEmpty() // true if stack is empty
	{
		return (top == -1);
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public char peek() // peek at top of stack
	{
		return stackArray[top];
	}

	// --------------------------------------------------------------
	public char pop() // take item from top of stack
	{
		return stackArray[top--];
	}

	// --------------------------------------------------------------
	public void push(final char j) // put item on top of stack
	{
		stackArray[++top] = j;
	}
} // end class StackX
