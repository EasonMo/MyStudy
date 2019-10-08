package com.dataStAndAl.ch04_stackAndQueue.example;

// postfix.java
// parses postfix arithmetic expressions
// to run this program: C>java PostfixApp
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class ParsePost {
	private StackXp theStack;
	private final String input;

	// --------------------------------------------------------------
	public ParsePost(final String s) {
		input = s;
	}

	// --------------------------------------------------------------
	public int doParse() {
		theStack = new StackXp(20); // make new stack
		char ch;
		int j;
		int num1, num2, interAns;

		for (j = 0; j < input.length(); j++) // for each char,
		{
			ch = input.charAt(j); // read from input
			theStack.displayStack("" + ch + " "); // *diagnostic*
			if (ch >= '0' && ch <= '9') {
				theStack.push(ch - '0'); // push it
			} else // it's an operator
			{
				num2 = theStack.pop(); // pop operands
				num1 = theStack.pop();
				switch (ch) // do arithmetic
				{
				case '+':
					interAns = num1 + num2;
					break;
				case '-':
					interAns = num1 - num2;
					break;
				case '*':
					interAns = num1 * num2;
					break;
				case '/':
					interAns = num1 / num2;
					break;
				default:
					interAns = 0;
				} // end switch
				theStack.push(interAns); // push result
			} // end else
		} // end for
		interAns = theStack.pop(); // get answer
		return interAns;
	} // end doParse()
} // end class ParsePost
////////////////////////////////////////////////////////////////

class PostfixApp {
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
		int output;

		while (true) {
			System.out.print("Enter postfix: ");
			System.out.flush();
			input = getString(); // read a string from kbd
			if (input.equals("")) {
				break;
			}
			// make a parser
			final ParsePost aParser = new ParsePost(input);
			output = aParser.doParse(); // do the evaluation
			System.out.println("Evaluates to " + output);
		} // end while
	} // end main()
} // end class PostfixApp
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class StackXp {
	private final int maxSize;
	private final int[] stackArray;
	private int top;

	// --------------------------------------------------------------
	public StackXp(final int size) // constructor
	{
		maxSize = size;
		stackArray = new int[maxSize];
		top = -1;
	}

	// --------------------------------------------------------------
	public void displayStack(final String s) {
		System.out.print(s);
		System.out.print("Stack (bottom-->top): ");
		for (int j = 0; j < size(); j++) {
			System.out.print(peekN(j));
			System.out.print(' ');
		}
		System.out.println("");
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public boolean isEmpty() // true if stack is empty
	{
		return (top == -1);
	}

	// --------------------------------------------------------------
	public boolean isFull() // true if stack is full
	{
		return (top == maxSize - 1);
	}

	// --------------------------------------------------------------
	public int peek() // peek at top of stack
	{
		return stackArray[top];
	}

	// --------------------------------------------------------------
	public int peekN(final int n) // peek at index n
	{
		return stackArray[n];
	}

	// --------------------------------------------------------------
	public int pop() // take item from top of stack
	{
		return stackArray[top--];
	}

	// --------------------------------------------------------------
	public void push(final int j) // put item on top of stack
	{
		stackArray[++top] = j;
	}

	// --------------------------------------------------------------
	public int size() // return size
	{
		return top + 1;
	}
} // end class StackX
