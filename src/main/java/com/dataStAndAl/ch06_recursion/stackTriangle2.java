package com.dataStAndAl.ch06_recursion;

// stackTriangle2.java
// evaluates triangular numbers, stack replaces recursion
// to run this program: C>java StackTriangle2App
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class StackTriangle2App {
	static int theNumber;
	static int theAnswer;
	static StackXs theStack;

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
		System.out.flush();
		theNumber = getInt();
		stackTriangle();
		System.out.println("Triangle=" + theAnswer);
	} // end main()
		// -------------------------------------------------------------

	public static void stackTriangle() {
		theStack = new StackXs(10000); // make a stack

		theAnswer = 0; // initialize answer

		while (theNumber > 0) // until n is 1,
		{
			theStack.push(theNumber); // push value
			--theNumber; // decrement value
		}
		while (!theStack.isEmpty()) // until stack empty,
		{
			final int newN = theStack.pop(); // pop value,
			theAnswer += newN; // add to answer
		}
	}
} // end class StackTriangle2App
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class StackXs {
	private final int maxSize; // size of stack array
	private final int[] stackArray;
	private int top; // top of stack
	// --------------------------------------------------------------

	public StackXs(final int s) // constructor
	{
		maxSize = s;
		stackArray = new int[maxSize];
		top = -1;
	}

	// --------------------------------------------------------------
	public boolean isEmpty() // true if stack is empty
	{
		return (top == -1);
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public int peek() // peek at top of stack
	{
		return stackArray[top];
	}

	// --------------------------------------------------------------
	public int pop() // take item from top of stack
	{
		return stackArray[top--];
	}

	// --------------------------------------------------------------
	public void push(final int p) // put item on top of stack
	{
		stackArray[++top] = p;
	}
} // end class StackX
