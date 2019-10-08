package com.dataStAndAl.ch03_simpleSort;

// bubbleSort.java
// demonstrates bubble sort
// to run this program: C>java BubbleSortApp
////////////////////////////////////////////////////////////////
class ArrayBub {
	private final long[] a; // ref to array a
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArrayBub(final int max) // constructor
	{
		a = new long[max]; // create the array
		nElems = 0; // no items yet
	}

	// --------------------------------------------------------------
	public void bubbleSort() {
		int out, in;

		for (out = nElems - 1; out > 1; out--) {
			for (in = 0; in < out; in++) {
				if (a[in] > a[in + 1]) {
					swap(in, in + 1); // swap them
				}
			}
		}
	} // end bubbleSort()
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
	private void swap(final int one, final int two) {
		final long temp = a[one];
		a[one] = a[two];
		a[two] = temp;
	}
	// --------------------------------------------------------------
} // end class ArrayBub
////////////////////////////////////////////////////////////////

class BubbleSortApp {
	public static void main(final String[] args) {
		final int maxSize = 100; // array size
		ArrayBub arr; // reference to array
		arr = new ArrayBub(maxSize); // create the array

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

		arr.bubbleSort(); // bubble sort them

		arr.display(); // display them again
	} // end main()
} // end class BubbleSortApp
////////////////////////////////////////////////////////////////
