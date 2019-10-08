package com.dataStAndAl.ch04_stackAndQueue.example;

////////////////////////////////////////////////////////////////
class StackApp {
	public static void main(final String[] args) {
		final StackXs theStack = new StackXs(10); // make new stack
		theStack.push(20); // push items onto stack
		theStack.push(40);
		theStack.push(60);
		theStack.push(80);

		while (!theStack.isEmpty()) // until it's empty,
		{ // delete item from stack
			final long value = theStack.pop();
			System.out.print(value); // display it
			System.out.print(" ");
		} // end while
		System.out.println("");
	} // end main()
} // end class StackApp
	////////////////////////////////////////////////////////////////
	// stack.java
	// demonstrates stacks
	// to run this program: C>java StackApp
	////////////////////////////////////////////////////////////////

class StackXs {
	private final int maxSize; // size of stack array
	private final long[] stackArray;
	private int top; // top of stack
	// --------------------------------------------------------------

	public StackXs(final int s) // constructor
	{
		maxSize = s; // set array size
		stackArray = new long[maxSize]; // create array
		top = -1; // no items yet
	}

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
	// --------------------------------------------------------------
	public long peek() // peek at top of stack
	{
		return stackArray[top];
	}

	// --------------------------------------------------------------
	public long pop() // take item from top of stack
	{
		return stackArray[top--]; // access item, decrement top
	}

	// --------------------------------------------------------------
	public void push(final long j) // put item on top of stack
	{
		stackArray[++top] = j; // increment top, insert item
	}
} // end class StackX
