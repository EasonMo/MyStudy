package com.dataStAndAl.ch05_linkList;

////////////////////////////////////////////////////////////////
class LinkListls {
	private Linkls first; // ref to first item on list
	// -------------------------------------------------------------

	public LinkListls() // constructor
	{
		first = null;
	} // no items on list yet
		// -------------------------------------------------------------

	public long deleteFirst() // delete first item
	{ // (assumes list not empty)
		final Linkls temp = first; // save reference to link
		first = first.next; // delete it: first-->old next
		return temp.dData; // return deleted link
	}

	// -------------------------------------------------------------
	public void displayList() {
		Linkls current = first; // start at beginning of list
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public void insertFirst(final long dd) // insert at start of list
	{ // make new link
		final Linkls newLink = new Linkls(dd);
		newLink.next = first; // newLink --> old first
		first = newLink; // first --> newLink
	}

	// -------------------------------------------------------------
	public boolean isEmpty() // true if list is empty
	{
		return (first == null);
	}
} // end class LinkList
	// linkStack.java
	// demonstrates a stack implemented as a list
	// to run this program: C>java LinkStackApp
	////////////////////////////////////////////////////////////////

class Linkls {
	public long dData; // data item
	public Linkls next; // next link in list
	// -------------------------------------------------------------

	public Linkls(final long dd) // constructor
	{
		dData = dd;
	}

	// -------------------------------------------------------------
	public void displayLink() // display ourself
	{
		System.out.print(dData + " ");
	}
} // end class Link
	////////////////////////////////////////////////////////////////

class LinkStack {
	private final LinkListls theList;

	// --------------------------------------------------------------
	public LinkStack() // constructor
	{
		theList = new LinkListls();
	}

	// --------------------------------------------------------------
	public void displayStack() {
		System.out.print("Stack (top-->bottom): ");
		theList.displayList();
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public boolean isEmpty() // true if stack is empty
	{
		return (theList.isEmpty());
	}

	// --------------------------------------------------------------
	public long pop() // take item from top of stack
	{
		return theList.deleteFirst();
	}

	// --------------------------------------------------------------
	public void push(final long j) // put item on top of stack
	{
		theList.insertFirst(j);
	}
} // end class LinkStack
	////////////////////////////////////////////////////////////////

class LinkStackApp {
	public static void main(final String[] args) {
		final LinkStack theStack = new LinkStack(); // make stack

		theStack.push(20); // push items
		theStack.push(40);

		theStack.displayStack(); // display stack

		theStack.push(60); // push items
		theStack.push(80);

		theStack.displayStack(); // display stack

		theStack.pop(); // pop items
		theStack.pop();

		theStack.displayStack(); // display stack
	} // end main()
} // end class LinkStackApp
	////////////////////////////////////////////////////////////////
