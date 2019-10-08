package com.dataStAndAl.ch04_stackAndQueue.example;

// brackets.java
// stacks used to check matching brackets
// to run this program: C>java bracketsApp
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class BracketChecker {
	private final String input; // input string
	// --------------------------------------------------------------

	public BracketChecker(final String in) // constructor
	{
		input = in;
	}

	// --------------------------------------------------------------
	public void check() {
		final int stackSize = input.length(); // get max stack size
		final StackX theStack = new StackX(stackSize); // make stack

		for (int j = 0; j < input.length(); j++) // get chars in turn
		{
			final char ch = input.charAt(j); // get char
			switch (ch) {
			case '{': // opening symbols
			case '[':
			case '(':
				theStack.push(ch); // push them
				break;

			case '}': // closing symbols
			case ']':
			case ')':
				if (!theStack.isEmpty()) // if stack not empty,
				{
					final char chx = theStack.pop(); // pop and check
					if ((ch == '}' && chx != '{') || (ch == ']' && chx != '[') || (ch == ')' && chx != '(')) {
						System.out.println("Error: " + ch + " at " + j);
					}
				} else {
					System.out.println("Error: " + ch + " at " + j);
				}
				break;
			default: // no action on other characters
				break;
			} // end switch
		} // end for
		// at this point, all characters have been processed
		if (!theStack.isEmpty()) {
			System.out.println("Error: missing right delimiter");
		}
	} // end check()
	// --------------------------------------------------------------
} // end class BracketChecker
////////////////////////////////////////////////////////////////

class BracketsApp {
	// --------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	// --------------------------------------------------------------
	public static void main(final String[] args) throws IOException {
		String input;
		while (true) {
			System.out.print("Enter string containing delimiters: ");
			System.out.flush();
			input = getString(); // read a string from kbd
			if (input.equals("")) {
				break;
			}
			// make a BracketChecker
			final BracketChecker theChecker = new BracketChecker(input);
			theChecker.check(); // check brackets
		} // end while
	} // end main()
} // end class BracketsApp
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class StackX {
	private final int maxSize;
	private final char[] stackArray;
	private int top;

	// --------------------------------------------------------------
	public StackX(final int s) // constructor
	{
		maxSize = s;
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
