package com.dataStAndAl.ch03_simpleSort;

// selectSort.java
// demonstrates selection sort
// to run this program: C>java SelectSortApp
////////////////////////////////////////////////////////////////
class ArraySel {
	private final long[] a; // ref to array a
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArraySel(final int max) // constructor
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
	public void selectionSort() {
		int out, in, min;

		// 最后一个不用比较，因为在swap里已经把最后一个较大的给交换了
		for (out = 0; out < nElems - 1; out++) // outer loop
		{
			min = out; // minimum
			for (in = out + 1; in < nElems; in++) {
				if (a[in] < a[min]) {
					min = in; // we have a new min
				}
			}
			swap(out, min); // swap them
		} // end for(out)
	} // end selectionSort()
		// --------------------------------------------------------------

	private void swap(final int one, final int two) {
		final long temp = a[one];
		a[one] = a[two];
		a[two] = temp;
	}
	// --------------------------------------------------------------
} // end class ArraySel
	////////////////////////////////////////////////////////////////

class SelectSortApp {
	public static void main(final String[] args) {
		final int maxSize = 100; // array size
		ArraySel arr; // reference to array
		arr = new ArraySel(maxSize); // create the array

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

		arr.selectionSort(); // selection-sort them

		arr.display(); // display them again
	} // end main()
} // end class SelectSortApp
	////////////////////////////////////////////////////////////////
