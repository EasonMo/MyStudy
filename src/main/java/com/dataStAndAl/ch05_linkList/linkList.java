package com.dataStAndAl.ch05_linkList;

// linkList.java
// demonstrates linked list
// to run this program: C>java LinkListApp
////////////////////////////////////////////////////////////////
class Linkl {
	public int iData; // data item
	public double dData; // data item
	public Linkl next; // next link in list
	// -------------------------------------------------------------

	public Linkl(final int id, final double dd) // constructor
	{
		iData = id; // initialize data
		dData = dd; // ('next' is automatically
	} // set to null)
		// -------------------------------------------------------------

	public void displayLink() // display ourself
	{
		System.out.print("{" + iData + ", " + dData + "} ");
	}
} // end class Link
	////////////////////////////////////////////////////////////////

class LinkListApp {
	public static void main(final String[] args) {
		final LinkListl theList = new LinkListl(); // make new list

		theList.insertFirst(22, 2.99); // insert four items
		theList.insertFirst(44, 4.99);
		theList.insertFirst(66, 6.99);
		theList.insertFirst(88, 8.99);

		theList.displayList(); // display list

		while (!theList.isEmpty()) // until it's empty,
		{
			final Linkl aLink = theList.deleteFirst(); // delete link
			System.out.print("Deleted "); // display it
			aLink.displayLink();
			System.out.println("");
		}
		theList.displayList(); // display list
	} // end main()
} // end class LinkListApp
	////////////////////////////////////////////////////////////////

class LinkListl {
	private Linkl first; // ref to first link on list

	// -------------------------------------------------------------
	public LinkListl() // constructor
	{
		first = null; // no links on list yet
	}

	// -------------------------------------------------------------
	public Linkl deleteFirst() // delete first item
	{ // (assumes list not empty)
		final Linkl temp = first; // save reference to link
		first = first.next; // delete it: first-->old next
		return temp; // return deleted link
	}

	// -------------------------------------------------------------
	public void displayList() {
		System.out.print("List (first-->last): ");
		Linkl current = first; // start at beginning of list
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	// insert at start of list
	public void insertFirst(final int id, final double dd) { // make new link
		final Linkl newLink = new Linkl(id, dd);
		newLink.next = first; // newLink --> old first
		first = newLink; // first --> newLink
	}

	// -------------------------------------------------------------
	public boolean isEmpty() // true if list is empty
	{
		return (first == null);
	}
} // end class LinkList
	////////////////////////////////////////////////////////////////
