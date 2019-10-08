package com.dataStAndAl.ch04_stackAndQueue.example;

// priorityQ.java
// demonstrates priority queue
// to run this program: C>java PriorityQApp
////////////////////////////////////////////////////////////////
class PriorityQ {
	// array in sorted order, from max at 0 to min at size-1
	private final int maxSize;
	private final long[] queArray;
	private int nItems;

	// -------------------------------------------------------------
	public PriorityQ(final int s) // constructor
	{
		maxSize = s;
		queArray = new long[maxSize];
		nItems = 0;
	}

	// -------------------------------------------------------------
	public void insert(final long item) // insert item
	{
		int j;

		if (nItems == 0) {
			queArray[nItems++] = item; // insert at 0
		} else // if items,
		{
			for (j = nItems - 1; j >= 0; j--) // start at end,
			{
				if (item > queArray[j]) {
					queArray[j + 1] = queArray[j]; // shift upward
				} else {
					break; // done shifting
				}
			} // end for
			queArray[j + 1] = item; // insert it
			// 因为j--多减了1，所以要加上
			nItems++;
		} // end else (nItems > 0)
	} // end insert()
		// -------------------------------------------------------------

	public boolean isEmpty() // true if queue is empty
	{
		return (nItems == 0);
	}

	// -------------------------------------------------------------
	public boolean isFull() // true if queue is full
	{
		return (nItems == maxSize);
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public long peekMin() // peek at minimum item
	{
		return queArray[nItems - 1];
	}

	// -------------------------------------------------------------
	public long remove() // remove minimum item
	{
		return queArray[--nItems];
	}
} // end class PriorityQ
	////////////////////////////////////////////////////////////////

class PriorityQApp {
	public static void main(final String[] args) {
		final PriorityQ thePQ = new PriorityQ(5);
		thePQ.insert(30);
		thePQ.insert(50);
		thePQ.insert(10);
		thePQ.insert(40);
		thePQ.insert(20);

		while (!thePQ.isEmpty()) {
			final long item = thePQ.remove();
			System.out.print(item + " "); // 10, 20, 30, 40, 50
		} // end while
		System.out.println("");
	} // end main()
		// -------------------------------------------------------------
} // end class PriorityQApp
	////////////////////////////////////////////////////////////////
