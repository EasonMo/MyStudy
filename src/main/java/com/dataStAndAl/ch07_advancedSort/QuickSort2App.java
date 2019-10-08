package com.dataStAndAl.ch07_advancedSort;

// quickSort2.java
// demonstrates quick sort with median-of-three partitioning
// to run this program: C>java QuickSort2App
////////////////////////////////////////////////////////////////
class ArrayIns2 {
	private final long[] theArray; // ref to array theArray
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArrayIns2(final int max) // constructor
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
	public void manualSort(final int left, final int right) {
		final int size = right - left + 1;
		if (size <= 1) {
			return; // no sort necessary
		}
		if (size == 2) { // 2-sort left and right
			if (theArray[left] > theArray[right]) {
				swap(left, right);
			}
			return;
		} else // size is 3
		{ // 3-sort left, center, & right
			if (theArray[left] > theArray[right - 1]) {
				swap(left, right - 1); // left, center
			}
			if (theArray[left] > theArray[right]) {
				swap(left, right); // left, right
			}
			if (theArray[right - 1] > theArray[right]) {
				swap(right - 1, right); // center, right
			}
		}
	} // end manualSort()
		// --------------------------------------------------------------
		// --------------------------------------------------------------

	public long medianOf3(final int left, final int right) {
		final int center = (left + right) / 2;
		// order left & center
		if (theArray[left] > theArray[center]) {
			swap(left, center);
		}
		// order left & right
		if (theArray[left] > theArray[right]) {
			swap(left, right);
		}
		// order center & right
		if (theArray[center] > theArray[right]) {
			swap(center, right);
		}
		// 枢纽找出来后，再放到最右边
		swap(center, right - 1); // put pivot on right
		return theArray[right - 1]; // return median value
	} // end medianOf3()
		// --------------------------------------------------------------

	public int partitionIt(final int left, final int right, final long pivot) {
		int leftPtr = left; // right of first elem
		int rightPtr = right - 1; // left of pivot

		while (true) {
			while (theArray[++leftPtr] < pivot) {
				; // (nop)
			}
			while (theArray[--rightPtr] > pivot) {
				; // (nop)
			}
			if (leftPtr >= rightPtr) {
				break; // partition done
			} else {
				swap(leftPtr, rightPtr); // swap elements
			}
		} // end while(true)
		swap(leftPtr, right - 1); // restore pivot
		return leftPtr; // return pivot location
	} // end partitionIt()
		// --------------------------------------------------------------

	public void quickSort() {
		recQuickSort(0, nElems - 1);
	}

	// --------------------------------------------------------------
	public void recQuickSort(final int left, final int right) {
		final int size = right - left + 1;
		if (size <= 3) {
			manualSort(left, right);
		} else // quicksort if large
		{
			final long median = medianOf3(left, right);
			final int partition = partitionIt(left, right, median);
			recQuickSort(left, partition - 1);
			recQuickSort(partition + 1, right);
		}
	} // end recQuickSort()
		// --------------------------------------------------------------

	public void swap(final int dex1, final int dex2) // swap two elements
	{
		final long temp = theArray[dex1]; // A into temp
		theArray[dex1] = theArray[dex2]; // B into A
		theArray[dex2] = temp; // temp into B
	} // end swap(
} // end class ArrayIns
	////////////////////////////////////////////////////////////////

class QuickSort2App {
	public static void main(final String[] args) {
		final int maxSize = 16; // array size
		ArrayIns2 arr; // reference to array
		arr = new ArrayIns2(maxSize); // create the array

		for (int j = 0; j < maxSize; j++) // fill array with
		{ // random numbers
			final long n = (int) (java.lang.Math.random() * 99);
			arr.insert(n);
		}
		arr.display(); // display items
		arr.quickSort(); // quicksort them
		arr.display(); // display them again
	} // end main()
} // end class QuickSort2App
	////////////////////////////////////////////////////////////////
