package com.dataStAndAl.ch06_recursion;

// stackTriangle.java
// evaluates triangular numbers, stack replaces recursion
// to run this program: C>java StackTriangleApp
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class Params // parameters to save on stack
{
	public int n;
	public int returnAddress;

	public Params(final int nn, final int ra) {
		n = nn;
		returnAddress = ra;
	}
} // end class Params
	////////////////////////////////////////////////////////////////

class StackTriangleApp {
	static int theNumber;
	static int theAnswer;
	static StackX theStack;
	static int codePart;
	static Params theseParams;

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

	// -------------------------------------------------------------
	public static void main(final String[] args) throws IOException {
		System.out.print("Enter a number: ");
		theNumber = getInt();
		recTriangle();
		System.out.println("Triangle=" + theAnswer);
	} // end main()
		// -------------------------------------------------------------

	public static void recTriangle() {
		theStack = new StackX(10000);
		codePart = 1;
		while (step() == false) {
			; // null statement
		}
	}

	// -------------------------------------------------------------
	public static boolean step() {
		switch (codePart) {
		case 1: // initial call
			System.out.println("case 1");
			theseParams = new Params(theNumber, 6);
			theStack.push(theseParams);
			codePart = 2;
			break;
		case 2: // method entry
			System.out.println("case 2");
			theseParams = theStack.peek();
			if (theseParams.n == 1) // test
			{
				theAnswer = 1;
				codePart = 5; // exit
			} else {
				codePart = 3; // recursive call
			}
			break;
		case 3: // method call
			System.out.println("case 3");
			final Params newParams = new Params(theseParams.n - 1, 4);
			theStack.push(newParams);
			codePart = 2; // go enter method
			break;
		case 4: // calculation
			System.out.println("case 4");
			theseParams = theStack.peek();
			theAnswer = theAnswer + theseParams.n;
			codePart = 5;
			break;
		case 5: // method exit
			System.out.println("case 5");
			theseParams = theStack.peek();
			codePart = theseParams.returnAddress; // (4 or 6)
			theStack.pop();
			break;
		case 6: // return point
			System.out.println("case 6");
			return true;
		} // end switch
		return false;
	} // end triangle
} // end class StackTriangleApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class StackX {
	private final int maxSize; // size of StackX array
	private final Params[] stackArray;
	private int top; // top of stack
	// --------------------------------------------------------------

	public StackX(final int s) // constructor
	{
		maxSize = s; // set array size
		stackArray = new Params[maxSize]; // create array
		top = -1; // no items yet
	}

	// --------------------------------------------------------------
	public Params peek() // peek at top of stack
	{
		return stackArray[top];
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public Params pop() // take item from top of stack
	{
		return stackArray[top--]; // access item, decrement top
	}

	// --------------------------------------------------------------
	public void push(final Params p) // put item on top of stack
	{
		stackArray[++top] = p; // increment top, insert item
	}
} // end class StackX
