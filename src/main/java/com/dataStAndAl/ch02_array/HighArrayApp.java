package com.dataStAndAl.ch02_array;

// highArray.java
// demonstrates array class with high-level interface
// to run this program: C>java HighArrayApp
////////////////////////////////////////////////////////////////
class HighArray {
	private final long[] a; // ref to array a
	private int nElems; // number of data items
	// -----------------------------------------------------------

	public HighArray(final int max) // constructor
	{
		a = new long[max]; // create the array
		nElems = 0; // no items yet
	}

	// -----------------------------------------------------------
	public boolean delete(final long value) {
		int j;
		for (j = 0; j < nElems; j++) {
			if (value == a[j]) {
				break;
			}
		}
		if (j == nElems) {
			return false;
		} else // found it
		{
			for (int k = j; k < nElems; k++) {
				a[k] = a[k + 1];
			}
			nElems--; // decrement size
			return true;
		}
	} // end delete()
	// -----------------------------------------------------------

	public void display() // displays array contents
	{
		for (int j = 0; j < nElems; j++) {
			System.out.print(a[j] + " "); // display it
		}
		System.out.println("");
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	public boolean find(final long searchKey) { // find specified value
		int j;
		for (j = 0; j < nElems; j++) {
			if (a[j] == searchKey) {
				break; // exit loop before end
			}
		}
		if (j == nElems) {
			return false; // yes, can't find it
		} else {
			return true; // no, found it
		}
	} // end find()
	// -----------------------------------------------------------

	public void insert(final long value) // put element into array
	{
		a[nElems] = value; // insert it
		nElems++; // increment size
	}
} // end class HighArray
////////////////////////////////////////////////////////////////

class HighArrayApp {
	public static void main(final String[] args) {
		final int maxSize = 100; // array size
		HighArray arr; // reference to array
		arr = new HighArray(maxSize); // create the array

		arr.insert(77); // insert 10 items
		arr.insert(99);
		arr.insert(44);
		arr.insert(55);
		arr.insert(22);
		arr.insert(88);
		arr.insert(11);
		arr.insert(00);
		arr.insert(66);
		arr.insert(33);

		arr.display(); // display items

		final int searchKey = 35; // search for item
		if (arr.find(searchKey)) {
			System.out.println("Found " + searchKey);
		} else {
			System.out.println("Can't find " + searchKey);
		}

		arr.delete(00); // delete 3 items
		arr.delete(55);
		arr.delete(99);

		arr.display(); // display items again
	} // end main()
} // end class HighArrayApp
