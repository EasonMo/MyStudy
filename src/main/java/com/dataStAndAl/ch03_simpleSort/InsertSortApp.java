package com.dataStAndAl.ch03_simpleSort;

// insertSort.java
// demonstrates insertion sort
// to run this program: C>java InsertSortApp
//--------------------------------------------------------------
class ArrayIns {
	private final long[] a; // ref to array a
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArrayIns(final int max) // constructor
	{
		a = new long[max]; // create the array
		nElems = 0; // no items yet
	}

	// --------------------------------------------------------------
	public void display() // displays array contents
	{
		for (int j = 0; j < nElems; j++) {
			System.out.print(a[j] + " "); // display it
		}
		System.out.println("");
	}

	// --------------------------------------------------------------
	public void insert(final long value) // put element into array
	{
		a[nElems] = value; // insert it
		nElems++; // increment size
	}

	// --------------------------------------------------------------
	public void insertionSort() {
		int in, out;

		for (out = 1; out < nElems; out++) // out is dividing line
		{
			final long temp = a[out]; // remove marked item
			in = out; // start shifts at out
			while (in > 0 && a[in - 1] >= temp) // until one is smaller,
			{
				a[in] = a[in - 1]; // shift item to right
				--in; // go left one position
			}
			a[in] = temp; // insert marked item
		} // end for
	} // end insertionSort()
	// --------------------------------------------------------------
} // end class ArrayIns
////////////////////////////////////////////////////////////////

class InsertSortApp {
	public static void main(final String[] args) {
		final int maxSize = 100; // array size
		ArrayIns arr; // reference to array
		arr = new ArrayIns(maxSize); // create the array

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

		arr.insertionSort(); // insertion-sort them

		arr.display(); // display them again
	} // end main()
} // end class InsertSortApp
