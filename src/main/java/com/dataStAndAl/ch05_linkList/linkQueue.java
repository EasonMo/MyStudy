package com.dataStAndAl.ch05_linkList;

////////////////////////////////////////////////////////////////
class FirstLastListlq {
	private Linklq first; // ref to first item
	private Linklq last; // ref to last item
	// -------------------------------------------------------------

	public FirstLastListlq() // constructor
	{
		first = null; // no items on list yet
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
		Linklq current = first; // start at beginning
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public void insertLast(final long dd) // insert at end of list
	{
		final Linklq newLink = new Linklq(dd); // make new link
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
	// linkQueue.java
	// demonstrates queue implemented as double-ended list
	// to run this program: C>java LinkQueueApp
	////////////////////////////////////////////////////////////////

class Linklq {
	public long dData; // data item
	public Linklq next; // next link in list
	// -------------------------------------------------------------

	public Linklq(final long d) // constructor
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
	////////////////////////////////////////////////////////////////

class LinkQueue {
	private final FirstLastListlq theList;

	// --------------------------------------------------------------
	public LinkQueue() // constructor
	{
		theList = new FirstLastListlq();
	} // make a 2-ended list
		// --------------------------------------------------------------

	public void displayQueue() {
		System.out.print("Queue (front-->rear): ");
		theList.displayList();
	}

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public void insert(final long j) // insert, rear of queue
	{
		theList.insertLast(j);
	}

	// --------------------------------------------------------------
	public boolean isEmpty() // true if queue is empty
	{
		return theList.isEmpty();
	}

	// --------------------------------------------------------------
	public long remove() // remove, front of queue
	{
		return theList.deleteFirst();
	}
} // end class LinkQueue
	////////////////////////////////////////////////////////////////

class LinkQueueApp {
	public static void main(final String[] args) {
		final LinkQueue theQueue = new LinkQueue();
		theQueue.insert(20); // insert items
		theQueue.insert(40);

		theQueue.displayQueue(); // display queue

		theQueue.insert(60); // insert items
		theQueue.insert(80);

		theQueue.displayQueue(); // display queue

		theQueue.remove(); // remove items
		theQueue.remove();

		theQueue.displayQueue(); // display queue
	} // end main()
} // end class LinkQueueApp
	////////////////////////////////////////////////////////////////
