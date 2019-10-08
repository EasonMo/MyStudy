package com.dataStAndAl.ch05_linkList;

// linkList2.java
// demonstrates linked list
// to run this program: C>java LinkList2App
////////////////////////////////////////////////////////////////
class Linkl2 {
	public int iData; // data item (key)
	public double dData; // data item
	public Linkl2 next; // next link in list
	// -------------------------------------------------------------

	public Linkl2(final int id, final double dd) // constructor
	{
		iData = id;
		dData = dd;
	}

	// -------------------------------------------------------------
	public void displayLink() // display ourself
	{
		System.out.print("{" + iData + ", " + dData + "} ");
	}
} // end class Link
	////////////////////////////////////////////////////////////////

class LinkList2App {
	public static void main(final String[] args) {
		final LinkListl2 theList = new LinkListl2(); // make list

		theList.insertFirst(22, 2.99); // insert 4 items
		theList.insertFirst(44, 4.99);
		theList.insertFirst(66, 6.99);
		theList.insertFirst(88, 8.99);

		theList.displayList(); // display list

		final Linkl2 f = theList.find(44); // find item
		if (f != null) {
			System.out.println("Found link with key " + f.iData);
		} else {
			System.out.println("Can't find link");
		}

		final Linkl2 d = theList.delete(66); // delete item
		if (d != null) {
			System.out.println("Deleted link with key " + d.iData);
		} else {
			System.out.println("Can't delete link");
		}

		theList.displayList(); // display list
	} // end main()
} // end class LinkList2App
	////////////////////////////////////////////////////////////////

class LinkListl2 {
	private Linkl2 first; // ref to first link on list

	// -------------------------------------------------------------
	public LinkListl2() // constructor
	{
		first = null; // no links on list yet
	}

	// -------------------------------------------------------------
	public Linkl2 delete(final int key) // delete link with given key
	{ // (assumes non-empty list)
		Linkl2 current = first; // search for link
		Linkl2 previous = first;
		while (current.iData != key) {
			if (current.next == null) {
				return null; // didn't find it
			} else {
				previous = current; // go to next link
				current = current.next;
			}
		} // found it
		if (current == first) {
			first = first.next; // change first
		} else {
			previous.next = current.next; // bypass it
		}
		return current;
	}

	// -------------------------------------------------------------
	public void displayList() // display the list
	{
		System.out.print("List (first-->last): ");
		Linkl2 current = first; // start at beginning of list
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public Linkl2 find(final int key) // find link with given key
	{ // (assumes non-empty list)
		Linkl2 current = first; // start at 'first'
		while (current.iData != key) // while no match,
		{
			if (current.next == null) {
				return null; // didn't find it
			} else {
				current = current.next; // go to next link
			}
		}
		return current; // found it
	}

	// -------------------------------------------------------------
	public void insertFirst(final int id, final double dd) { // make new link
		final Linkl2 newLink = new Linkl2(id, dd);
		newLink.next = first; // it points to old first link
		first = newLink; // now first points to this
	}
} // end class LinkList
	////////////////////////////////////////////////////////////////
