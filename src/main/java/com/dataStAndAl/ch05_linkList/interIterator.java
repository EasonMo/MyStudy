package com.dataStAndAl.ch05_linkList;

// interIterator.java
// demonstrates iterators on a linked listListIterator
// to run this program: C>java InterIterApp
// for I/O
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class InterIterApp {
	// -------------------------------------------------------------
	public static char getChar() throws IOException {
		final String s = getString();
		return s.charAt(0);
	}

	// -------------------------------------------------------------
	public static int getInt() throws IOException {
		final String s = getString();
		return Integer.parseInt(s);
	}

	// -------------------------------------------------------------
	// --------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	public static void main(final String[] args) throws IOException {
		final LinkList theList = new LinkList(); // new list
		final ListIterator iter1 = theList.getIterator(); // new iter
		long value;

		iter1.insertAfter(20); // insert items
		iter1.insertAfter(40);
		iter1.insertAfter(80);
		iter1.insertBefore(60);

		while (true) {
			System.out.print("Enter first letter of show, reset, ");
			System.out.print("next, get, before, after, delete: ");
			System.out.flush();
			final int choice = getChar(); // get user's option
			switch (choice) {
			case 's': // show list
				if (!theList.isEmpty()) {
					theList.displayList();
				} else {
					System.out.println("List is empty");
				}
				break;
			case 'r': // reset (to first)
				iter1.reset();
				break;
			case 'n': // advance to next item
				if (!theList.isEmpty() && !iter1.atEnd()) {
					iter1.nextLink();
				} else {
					System.out.println("Can't go to next link");
				}
				break;
			case 'g': // get current item
				if (!theList.isEmpty()) {
					value = iter1.getCurrent().dData;
					System.out.println("Returned " + value);
				} else {
					System.out.println("List is empty");
				}
				break;
			case 'b': // insert before current
				System.out.print("Enter value to insert: ");
				System.out.flush();
				value = getInt();
				iter1.insertBefore(value);
				break;
			case 'a': // insert after current
				System.out.print("Enter value to insert: ");
				System.out.flush();
				value = getInt();
				iter1.insertAfter(value);
				break;
			case 'd': // delete current item
				if (!theList.isEmpty()) {
					value = iter1.deleteCurrent();
					System.out.println("Deleted " + value);
				} else {
					System.out.println("Can't delete");
				}
				break;
			default:
				System.out.println("Invalid entry");
			} // end switch
		} // end while
	} // end main()
} // end class InterIterApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class Linki {
	public long dData; // data item
	public Linki next; // next link in list
	// -------------------------------------------------------------

	public Linki(final long dd) // constructor
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

class LinkList {
	private Linki first; // ref to first item on list

	// -------------------------------------------------------------
	public LinkList() // constructor
	{
		first = null;
	} // no items on list yet
		// -------------------------------------------------------------

	public void displayList() {
		Linki current = first; // start at beginning of list
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public Linki getFirst() // get value of first
	{
		return first;
	}

	// -------------------------------------------------------------
	public ListIterator getIterator() // return iterator
	{
		return new ListIterator(this); // initialized with
	} // this list
		// -------------------------------------------------------------

	public boolean isEmpty() // true if list is empty
	{
		return first == null;
	}

	// -------------------------------------------------------------
	public void setFirst(final Linki f) // set first to new link
	{
		first = f;
	}
} // end class LinkList
	////////////////////////////////////////////////////////////////

class ListIterator {
	private Linki current; // current link
	private Linki previous; // previous link
	private final LinkList ourList; // our linked list
	// --------------------------------------------------------------

	public ListIterator(final LinkList list) // constructor
	{
		ourList = list;
		reset();
	}

	// --------------------------------------------------------------
	public boolean atEnd() // true if last link
	{
		return (current.next == null);
	}

	// --------------------------------------------------------------
	public long deleteCurrent() // delete item at current
	{
		final long value = current.dData;
		if (previous == null) // beginning of list
		{
			ourList.setFirst(current.next);
			reset();
		} else // not beginning
		{
			previous.next = current.next;
			if (atEnd()) {
				reset();
			} else {
				current = current.next;
			}
		}
		return value;
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public Linki getCurrent() // get current link
	{
		return current;
	}

	// --------------------------------------------------------------
	public void insertAfter(final long dd) // insert after
	{ // current link
		final Linki newLink = new Linki(dd);

		if (ourList.isEmpty()) // empty list
		{
			ourList.setFirst(newLink);
			current = newLink;
		} else // not empty
		{
			newLink.next = current.next;
			current.next = newLink;
			nextLink(); // point to new link
		}
	}

	// --------------------------------------------------------------
	public void insertBefore(final long dd) // insert before
	{ // current link
		final Linki newLink = new Linki(dd);

		if (previous == null) // beginning of list
		{ // (or empty list)
			newLink.next = ourList.getFirst();
			ourList.setFirst(newLink);
			reset();
		} else // not beginning
		{
			newLink.next = previous.next;
			previous.next = newLink;
			current = newLink;
		}
	}

	// --------------------------------------------------------------
	public void nextLink() // go to next link
	{
		previous = current;
		current = current.next;
	}

	// --------------------------------------------------------------
	public void reset() // start at 'first'
	{
		current = ourList.getFirst();
		previous = null;
	}
} // end class ListIterator
