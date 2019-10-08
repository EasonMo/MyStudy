package com.dataStAndAl.ch07_advancedSort;

// quickSort3.java
// demonstrates quick sort; uses insertion sort for cleanup
// to run this program: C>java QuickSort3App
////////////////////////////////////////////////////////////////
class ArrayIns3 {
	private final long[] theArray; // ref to array theArray
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArrayIns3(final int max) // constructor
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
	// insertion sort
	public void insertionSort(final int left, final int right) {
		int in, out;
		// sorted on left of out
		for (out = left + 1; out <= right; out++) {
			final long temp = theArray[out]; // remove marked item
			in = out; // start shifts at out
						// until one is smaller,
			while (in > left && theArray[in - 1] >= temp) {
				theArray[in] = theArray[in - 1]; // shift item to right
				--in; // go left one position
			}
			theArray[in] = temp; // insert marked item
		} // end for
	} // end insertionSort()
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
		// 枢纽找出来后，再放到最右边-1，因为最右的比它大
		swap(center, right - 1); // put pivot on right
		return theArray[right - 1]; // return median value
	} // end medianOf3()
		// --------------------------------------------------------------

	public int partitionIt(final int left, final int right, final long pivot) {
		int leftPtr = left; // right of first elem
		// 划分数组的右边位置为right-2，即三项的最右项和枢纽占了2位
		int rightPtr = right - 1; // left of pivot
		while (true) {
			// 此处的==，--很精粹
			while (theArray[++leftPtr] < pivot) {
				; // (nop)
			}
			while (theArray[--rightPtr] > pivot) {
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
		swap(leftPtr, right - 1); // restore pivot
		return leftPtr; // return pivot location
	} // end partitionIt()
		// --------------------------------------------------------------

	public void quickSort() {
		recQuickSort(0, nElems - 1);
		// insertionSort(0, nElems-1); // the other option
	}

	// --------------------------------------------------------------
	public void recQuickSort(final int left, final int right) {
		final int size = right - left + 1;
		if (size < 10) {
			insertionSort(left, right);
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

class QuickSort3App {
	public static void main(final String[] args) {
		final int maxSize = 16; // array size
		ArrayIns3 arr; // reference to array
		arr = new ArrayIns3(maxSize); // create the array

		for (int j = 0; j < maxSize; j++) // fill array with
		{ // random numbers
			final long n = (int) (java.lang.Math.random() * 99);
			arr.insert(n);
		}
		arr.display(); // display items
		arr.quickSort(); // quicksort them
		arr.display(); // display them again
	} // end main()
} // end class QuickSort3App
	////////////////////////////////////////////////////////////////
