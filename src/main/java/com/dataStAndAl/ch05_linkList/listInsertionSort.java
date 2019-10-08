package com.dataStAndAl.ch05_linkList;

// listInsertionSort.java
// demonstrates sorted list used for sorting
// to run this program: C>java ListInsertionSortApp
////////////////////////////////////////////////////////////////
class Linkli {
	public long dData; // data item
	public Linkli next; // next link in list
	// -------------------------------------------------------------

	public Linkli(final long dd) // constructor
	{
		dData = dd;
	}
	// -------------------------------------------------------------
} // end class Link
////////////////////////////////////////////////////////////////

class ListInsertionSortApp {
	public static void main(final String[] args) {
		final int size = 10;
		// create array of links
		final Linkli[] linkArray = new Linkli[size];

		for (int j = 0; j < size; j++) // fill array with links
		{ // random number
			final int n = (int) (java.lang.Math.random() * 99);
			final Linkli newLink = new Linkli(n); // make link
			linkArray[j] = newLink; // put in array
		}
		// display array contents
		System.out.print("Unsorted array: ");
		for (int j = 0; j < size; j++) {
			System.out.print(linkArray[j].dData + " ");
		}
		System.out.println("");

		// create new list
		final SortedList theSortedList = new SortedList(linkArray);

		for (int j = 0; j < size; j++) {
			linkArray[j] = theSortedList.remove();
		}

		// display array contents
		System.out.print("Sorted Array:   ");
		for (int j = 0; j < size; j++) {
			System.out.print(linkArray[j].dData + " ");
		}
		System.out.println("");
	} // end main()
} // end class ListInsertionSortApp
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class SortedList {
	private Linkli first; // ref to first item on list
	// -------------------------------------------------------------

	public SortedList() // constructor (no args)
	{
		first = null;
	} // initialize list
	// -------------------------------------------------------------

	public SortedList(final Linkli[] linkArr) // constructor (array
	{ // as argument)
		first = null; // initialize list
		for (int j = 0; j < linkArr.length; j++) {
			insert(linkArr[j]); // to list
		}
	}

	// -------------------------------------------------------------
	public void insert(final Linkli k) // insert (in order)
	{
		Linkli previous = null; // start at first
		Linkli current = first;
		// until end of list,
		while (current != null && k.dData > current.dData) { // or key >
																// current,
			previous = current;
			current = current.next; // go to next item
		}
		if (previous == null) {
			first = k; // first --> k
		} else {
			previous.next = k; // old prev --> k
		}
		k.next = current; // k --> old currnt
	} // end insert()
	// -------------------------------------------------------------

	public Linkli remove() // return & delete first link
	{ // (assumes non-empty list)
		final Linkli temp = first; // save first
		first = first.next; // delete first
		return temp; // return value
	}
	// -------------------------------------------------------------
} // end class SortedList
