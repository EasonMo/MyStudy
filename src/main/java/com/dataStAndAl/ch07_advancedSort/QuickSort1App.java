package com.dataStAndAl.ch07_advancedSort;

// quickSort1.java
// demonstrates simple version of quick sort
// to run this program: C>java QuickSort1App
////////////////////////////////////////////////////////////////
class ArrayIns {
	private final long[] theArray; // ref to array theArray
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArrayIns(final int max) // constructor
	{
		theArray = new long[max]; // create the array
		nElems = 0; // no items yet
	}

	// --------------------------------------------------------------
	public void display() // displays array contents
	{
		System.out.print("A=");
		for (int j = 0; j < nElems; j++) {
			System.out.print(theArray[j] + " "); // display it
		}
		System.out.println("");
	}

	// --------------------------------------------------------------
	public void insert(final long value) // put element into array
	{
		theArray[nElems] = value; // insert it
		nElems++; // increment size
	}

	// --------------------------------------------------------------
	public int partitionIt(final int left, final int right, final long pivot) {
		int leftPtr = left - 1; // left (after ++)
		// 用了最右的数据作为枢纽，所以不用加1
		int rightPtr = right; // right-1 (after --)
		while (true) { // find bigger item
			while (theArray[++leftPtr] < pivot) {
				; // (nop)
			}
			// find smaller item
			while (rightPtr > 0 && theArray[--rightPtr] > pivot) {
				; // (nop)
			}

			if (leftPtr >= rightPtr) {
				// 这里决定了leftPtr=左边界+1
				// 所以下面交换leftPtr和right没问题
				// leftPtr就是枢纽的位置
				break; // partition done
			} else {
				swap(leftPtr, rightPtr); // swap elements
			}
		} // end while(true)
		swap(leftPtr, right); // restore pivot
		return leftPtr; // return pivot location
	} // end partitionIt()
		// --------------------------------------------------------------

	public void quickSort() {
		recQuickSort(0, nElems - 1);
	}

	// --------------------------------------------------------------
	public void recQuickSort(final int left, final int right) {
		if (right - left <= 0) {
			return; // already sorted
		} else // size is 2 or larger
		{
			// 用最右边的数据做枢纽
			final long pivot = theArray[right]; // rightmost item
			// partition range
			final int partition = partitionIt(left, right, pivot);
			recQuickSort(left, partition - 1); // sort left side
			recQuickSort(partition + 1, right); // sort right side
		}
	} // end recQuickSort()
		// --------------------------------------------------------------

	public void swap(final int dex1, final int dex2) // swap two elements
	{
		final long temp = theArray[dex1]; // A into temp
		theArray[dex1] = theArray[dex2]; // B into A
		theArray[dex2] = temp; // temp into B
	} // end swap(
		// --------------------------------------------------------------
} // end class ArrayIns
	////////////////////////////////////////////////////////////////

class QuickSort1App {
	public static void main(final String[] args) {
		final int maxSize = 16; // array size
		ArrayIns arr;
		arr = new ArrayIns(maxSize); // create array

		for (int j = 0; j < maxSize; j++) // fill array with
		{ // random numbers
			final long n = (int) (java.lang.Math.random() * 99);
			arr.insert(n);
		}
		arr.display(); // display items
		arr.quickSort(); // quicksort them
		arr.display(); // display them again
	} // end main()
} // end class QuickSort1App
	////////////////////////////////////////////////////////////////
