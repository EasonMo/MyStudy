package com.dataStAndAl.ch05_linkList;

// sortedList.java
// demonstrates sorted list
// to run this program: C>java SortedListApp
////////////////////////////////////////////////////////////////
class Links {
	public long dData; // data item
	public Links next; // next link in list
	// -------------------------------------------------------------

	public Links(final long dd) // constructor
	{
		dData = dd;
	}

	// -------------------------------------------------------------
	public void displayLink() // display this link
	{
		System.out.print(dData + " ");
	}
} // end class Link
	////////////////////////////////////////////////////////////////

class SortedListApp {
	public static void main(final String[] args) { // create new list
		final SortedLists theSortedList = new SortedLists();
		theSortedList.insert(20); // insert 2 items
		theSortedList.insert(40);

		theSortedList.displayList(); // display list

		theSortedList.insert(10); // insert 3 more items
		theSortedList.insert(30);
		theSortedList.insert(50);

		theSortedList.displayList(); // display list

		theSortedList.remove(); // remove an item

		theSortedList.displayList(); // display list
	} // end main()
} // end class SortedListApp
	////////////////////////////////////////////////////////////////

class SortedLists {
	private Links first; // ref to first item
	// -------------------------------------------------------------

	public SortedLists() // constructor
	{
		first = null;
	}

	// -------------------------------------------------------------
	public void displayList() {
		System.out.print("List (first-->last): ");
		Links current = first; // start at beginning of list
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	public void insert(final long key) // insert, in order
	{
		final Links newLink = new Links(key); // make new link
		Links previous = null; // start at first
		Links current = first;
		// until end of list,
		while (current != null && key > current.dData) { // or key > current,
			previous = current;
			current = current.next; // go to next item
		}
		if (previous == null) {
			first = newLink; // first --> newLink
		} else {
			previous.next = newLink; // old prev --> newLink
		}
		newLink.next = current; // newLink --> old currnt
	} // end insert()
		// -------------------------------------------------------------

	public boolean isEmpty() // true if no links
	{
		return (first == null);
	}

	// -------------------------------------------------------------
	public Links remove() // return & delete first link
	{ // (assumes non-empty list)
		final Links temp = first; // save first
		first = first.next; // delete first
		return temp; // return value
	}
} // end class SortedList
	////////////////////////////////////////////////////////////////
