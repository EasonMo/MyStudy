package com.dataStAndAl.ch02_array;

// orderedArray.java
// demonstrates ordered array class
// to run this program: C>java OrderedApp
////////////////////////////////////////////////////////////////
class OrdArray {
	private final long[] a; // ref to array a
	private int nElems; // number of data items
	// -----------------------------------------------------------

	public OrdArray(final int max) // constructor
	{
		a = new long[max]; // create array
		nElems = 0;
	}

	// -----------------------------------------------------------
	public boolean delete(final long value) {
		final int j = find(value);
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
	public int find(final long searchKey) {
		int lowerBound = 0;
		int upperBound = nElems - 1;
		int curIn;

		while (true) {
			curIn = (lowerBound + upperBound) / 2;
			if (a[curIn] == searchKey) {
				return curIn; // found it
			} else if (lowerBound > upperBound) {
				return nElems; // can't find it
			} else // divide range
			{
				if (a[curIn] < searchKey) {
					lowerBound = curIn + 1; // it's in upper half
				} else {
					upperBound = curIn - 1; // it's in lower half
				}
			} // end else divide range
		} // end while
	} // end find()
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
} // end class OrdArray
////////////////////////////////////////////////////////////////

class OrderedApp {
	public static void main(final String[] args) {
		final int maxSize = 100; // array size
		OrdArray arr; // reference to array
		arr = new OrdArray(maxSize); // create the array

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

		final int searchKey = 55; // search for item
		if (arr.find(searchKey) != arr.size()) {
			System.out.println("Found " + searchKey);
		} else {
			System.out.println("Can't find " + searchKey);
		}

		arr.display(); // display items

		arr.delete(00); // delete 3 items
		arr.delete(55);
		arr.delete(99);

		arr.display(); // display items again
	} // end main()
} // end class OrderedApp
