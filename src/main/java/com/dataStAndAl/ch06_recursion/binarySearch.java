package com.dataStAndAl.ch06_recursion;

////////////////////////////////////////////////////////////////
class BinarySearchApp {
	public static void main(final String[] args) {
		final int maxSize = 100; // array size
		ordArray arr; // reference to array
		arr = new ordArray(maxSize); // create the array

		arr.insert(72); // insert items
		arr.insert(90);
		arr.insert(45);
		arr.insert(126);
		arr.insert(54);
		arr.insert(99);
		arr.insert(144);
		arr.insert(27);
		arr.insert(135);
		arr.insert(81);
		arr.insert(18);
		arr.insert(108);
		arr.insert(9);
		arr.insert(117);
		arr.insert(63);
		arr.insert(36);

		arr.display(); // display array

		final int searchKey = 27; // search for item
		if (arr.find(searchKey) != arr.size()) {
			System.out.println("Found " + searchKey);
		} else {
			System.out.println("Can't find " + searchKey);
		}
	} // end main()
} // end class BinarySearchApp
////////////////////////////////////////////////////////////////
// binarySearch.java
// demonstrates recursive binary search
// to run this program: C>java BinarySearchApp
////////////////////////////////////////////////////////////////

class ordArray {
	private final long[] a; // ref to array a
	private int nElems; // number of data items
	// -----------------------------------------------------------

	public ordArray(final int max) // constructor
	{
		a = new long[max]; // create array
		nElems = 0;
	}

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
	public int find(final long searchKey) {
		return recFind(searchKey, 0, nElems - 1);
	}

	// -----------------------------------------------------------
	public void insert(final long value) // put element into array
	{
		int j;
		for (j = 0; j < nElems; j++) {
			if (a[j] > value) {
				break;
			}
		}
		for (int k = nElems; k > j; k--) {
			a[k] = a[k - 1];
		}
		a[j] = value; // insert it
		nElems++; // increment size
	} // end insert()
	// -----------------------------------------------------------

	public int size() {
		return nElems;
	}

	// -----------------------------------------------------------
	private int recFind(final long searchKey, final int lowerBound, final int upperBound) {
		int curIn;

		curIn = (lowerBound + upperBound) / 2;
		if (a[curIn] == searchKey) {
			return curIn; // found it
		} else if (lowerBound > upperBound) {
			return nElems; // can't find it
		} else // divide range
		{
			if (a[curIn] < searchKey) {
				return recFind(searchKey, curIn + 1, upperBound);
			} else {
				return recFind(searchKey, lowerBound, curIn - 1);
			}
		} // end else divide range
	} // end recFind()
} // end class ordArray
