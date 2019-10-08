package com.dataStAndAl.ch07_advancedSort;

class ArrayPar {
	private final long[] theArray; // ref to array theArray
	private int nElems; // number of data items
	// --------------------------------------------------------------

	public ArrayPar(final int max) // constructor
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
		// 这里减1和加1是因为下面是先加1和先减1的
		int leftPtr = left - 1; // right of first elem
		int rightPtr = right + 1; // left of pivot
		while (true) {
			while (leftPtr < right && // find bigger item
					theArray[++leftPtr] < pivot) {
				; // (nop)
			}

			while (rightPtr > left && // find smaller item
					theArray[--rightPtr] > pivot) {
				; // (nop)
			}
			if (leftPtr >= rightPtr) {
				break; // partition done
			} else {
				swap(leftPtr, rightPtr); // swap elements
			}
		} // end while(true)
		System.out.println("\nleftPtr: " + leftPtr + ", rightPtr: " + rightPtr);
		return leftPtr; // return partition

	} // end partitionIt()
		// --------------------------------------------------------------

	public int size() // return number of items
	{
		return nElems;
	}

	// --------------------------------------------------------------
	public void swap(final int dex1, final int dex2) // swap two elements
	{
		long temp;
		temp = theArray[dex1]; // A into temp
		theArray[dex1] = theArray[dex2]; // B into A
		theArray[dex2] = temp; // temp into B
	} // end swap()
		// --------------------------------------------------------------
} // end class ArrayPar

class PartitionApp {
	public static void main(final String[] args) {
		final int maxSize = 16; // array size
		ArrayPar arr; // reference to array
		arr = new ArrayPar(maxSize); // create the array

		for (int j = 0; j < maxSize; j++) // fill array with
		{ // random numbers
			final long n = (int) (java.lang.Math.random() * 199);
			arr.insert(n);
		}
		arr.display(); // display unsorted array

		final long pivot = 99; // pivot value
		System.out.print("Pivot is " + pivot);
		final int size = arr.size();
		// partition array
		final int partDex = arr.partitionIt(0, size - 1, pivot);

		System.out.println(", Partition is at index " + partDex);
		arr.display(); // display partitioned array
	} // end main()
} // end class PartitionApp
