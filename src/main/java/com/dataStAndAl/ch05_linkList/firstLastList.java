package com.dataStAndAl.ch05_linkList;

////////////////////////////////////////////////////////////////
class FirstLastApp {
	public static void main(final String[] args) { // make a new list
		final FirstLastList theList = new FirstLastList();

		theList.insertFirst(22); // insert at front
		theList.insertFirst(44);
		theList.insertFirst(66);

		theList.insertLast(11); // insert at rear
		theList.insertLast(33);
		theList.insertLast(55);

		theList.displayList(); // display the list

		theList.deleteFirst(); // delete first two items
		theList.deleteFirst();

		theList.displayList(); // display again
	} // end main()
} // end class FirstLastApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class FirstLastList {
	private Linkf first; // ref to first link
	private Linkf last; // ref to last link
	// -------------------------------------------------------------

	public FirstLastList() // constructor
	{
		first = null; // no links on list yet
		last = null;
	}

	// -------------------------------------------------------------
	public long deleteFirst() // delete first link
	{ // (assumes non-empty list)
		final long temp = first.dData;
		if (first.next == null) {
			last = null; // null <-- last
		}
		first = first.next; // first --> old next
		return temp;
	}

	// -------------------------------------------------------------
	public void displayList() {
		System.out.print("List (first-->last): ");
		Linkf current = first; // start at beginning
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public void insertFirst(final long dd) // insert at front of list
	{
		final Linkf newLink = new Linkf(dd); // make new link

		if (isEmpty()) {
			last = newLink; // newLink <-- last
		}
		newLink.next = first; // newLink --> old first
		first = newLink; // first --> newLink
	}

	// -------------------------------------------------------------
	public void insertLast(final long dd) // insert at end of list
	{
		final Linkf newLink = new Linkf(dd); // make new link
		if (isEmpty()) {
			first = newLink; // first --> newLink
		} else {
			last.next = newLink; // old last --> newLink
		}
		last = newLink; // newLink <-- last
	}

	// -------------------------------------------------------------
	public boolean isEmpty() // true if no links
	{
		return first == null;
	}
} // end class FirstLastList
	// firstLastList.java
	// demonstrates list with first and last references
	// to run this program: C>java FirstLastApp
	////////////////////////////////////////////////////////////////

class Linkf {
	public long dData; // data item
	public Linkf next; // next link in list
	// -------------------------------------------------------------

	public Linkf(final long d) // constructor
	{
		dData = d;
	}

	// -------------------------------------------------------------
	public void displayLink() // display this link
	{
		System.out.print(dData + " ");
	}
	// -------------------------------------------------------------
} // end class Link
