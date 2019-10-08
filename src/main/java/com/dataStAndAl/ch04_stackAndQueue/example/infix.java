package com.dataStAndAl.ch04_stackAndQueue.example;

// infix.java
// converts infix arithmetic expressions to postfix
// to run this program: C>java InfixApp
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class InfixApp {
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
			System.out.print("Enter infix: ");
			System.out.flush();
			input = getString(); // read a string from kbd
			if (input.equals("")) {
				break;
			}
			// make a translator
			final InToPost theTrans = new InToPost(input);
			output = theTrans.doTrans(); // do the translation
			System.out.println("Postfix is " + output + '\n');
		} // end while
	} // end main()
} // end class InfixApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class InToPost // infix to postfix conversion
{
	private final StackXi theStack;
	private final String input;
	private String output = "";

	// --------------------------------------------------------------
	public InToPost(final String in) // constructor
	{
		input = in;
		final int stackSize = input.length();
		theStack = new StackXi(stackSize);
	}

	// --------------------------------------------------------------
	public String doTrans() // do translation to postfix
	{
		for (int j = 0; j < input.length(); j++) // for each char
		{
			final char ch = input.charAt(j); // get it
			theStack.displayStack("For " + ch + " "); // *diagnostic*
			switch (ch) {
			case '+': // it's + or -
			case '-':
				gotOper(ch, 1); // go pop operators
				break; // (precedence 1)
			case '*': // it's * or /
			case '/':
				gotOper(ch, 2); // go pop operators
				break; // (precedence 2)
			case '(': // it's a left paren
				theStack.push(ch); // push it
				break;
			case ')': // it's a right paren
				gotParen(ch); // go pop operators
				break;
			default: // must be an operand
				output = output + ch; // write it to output
				break;
			} // end switch
		} // end for
		while (!theStack.isEmpty()) // pop remaining opers
		{
			theStack.displayStack("While "); // *diagnostic*
			output = output + theStack.pop(); // write to output
		}
		theStack.displayStack("End   "); // *diagnostic*
		return output; // return postfix
	} // end doTrans()
		// --------------------------------------------------------------

	public void gotOper(final char opThis, final int prec1) { // got operator
																// from input
		while (!theStack.isEmpty()) {
			final char opTop = theStack.pop();
			if (opTop == '(') // if it's a '('
			{
				theStack.push(opTop); // restore '('
				break;
			} else // it's an operator
			{
				int prec2; // precedence of new op

				if (opTop == '+' || opTop == '-') {
					prec2 = 1;
				} else {
					prec2 = 2;
				}
				if (prec2 < prec1) // if prec of new op less
				{ // than prec of old
					theStack.push(opTop); // save newly-popped op
					break;
				} else {
					output = output + opTop; // than prec of old
				}
			} // end else (it's an operator)
		} // end while
		theStack.push(opThis); // push new operator
	} // end gotOp()
		// --------------------------------------------------------------

	public void gotParen(final char ch) { // got right paren from input
		while (!theStack.isEmpty()) {
			final char chx = theStack.pop();
			if (chx == '(') {
				break; // we're done
			} else {
				output = output + chx; // output it
			}
		} // end while
	} // end popOps()
		// --------------------------------------------------------------
} // end class InToPost
	////////////////////////////////////////////////////////////////

class StackXi {
	private final int maxSize;
	private final char[] stackArray;
	private int top;

	// --------------------------------------------------------------
	public StackXi(final int s) // constructor
	{
		maxSize = s;
		stackArray = new char[maxSize];
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
	public char peek() // peek at top of stack
	{
		return stackArray[top];
	}

	// --------------------------------------------------------------
	public char peekN(final int n) // return item at index n
	{
		return stackArray[n];
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

	// -------------------------------------------------------------
	public int size() // return size
	{
		return top + 1;
	}
} // end class StackX
