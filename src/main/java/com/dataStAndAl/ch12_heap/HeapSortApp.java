package com.dataStAndAl.ch12_heap;

// heapSort.java
// demonstrates heap sort
// to run this program: C>java HeapSortApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class Heap2 {
	private final Node2[] heapArray;
	private final int maxSize; // size of array
	private int currentSize; // number of items in array
	// -------------------------------------------------------------

	public Heap2(final int mx) // constructor
	{
		maxSize = mx;
		currentSize = 0;
		heapArray = new Node2[maxSize];
	}

	// -------------------------------------------------------------
	public void displayArray() {
		for (int j = 0; j < maxSize; j++) {
			System.out.print(heapArray[j].getKey() + " ");
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	public void displayHeap() {
		int nBlanks = 32;
		int itemsPerRow = 1;
		int column = 0;
		int j = 0; // current item
		final String dots = "...............................";
		System.out.println(dots + dots); // dotted top line

		while (currentSize > 0) // for each heap item
		{
			if (column == 0) {
				for (int k = 0; k < nBlanks; k++) {
					System.out.print(' ');
				}
			}
			// display item
			System.out.print(heapArray[j].getKey());

			if (++j == currentSize) {
				break;
			}

			if (++column == itemsPerRow) // end of row?
			{
				nBlanks /= 2; // half the blanks
				itemsPerRow *= 2; // twice the items
				column = 0; // start over on
				System.out.println(); // new row
			} else {
				for (int k = 0; k < nBlanks * 2 - 2; k++) {
					System.out.print(' '); // interim blanks
				}
			}
		} // end for
		System.out.println("\n" + dots + dots); // dotted bottom line
	} // end displayHeap()
	// -------------------------------------------------------------

	public void incrementSize() {
		currentSize++;
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public void insertAt(final int index, final Node2 newNode) {
		heapArray[index] = newNode;
	}

	// -------------------------------------------------------------
	public Node2 remove() // delete item with max key
	{ // (assumes non-empty list)
		final Node2 root = heapArray[0];
		heapArray[0] = heapArray[--currentSize];
		trickleDown(0);
		return root;
	} // end remove()
	// -------------------------------------------------------------

	public void trickleDown(int index) {
		int largerChild;
		final Node2 top = heapArray[index]; // save root
		while (index < currentSize / 2) // not on bottom row
		{
			final int leftChild = 2 * index + 1;
			final int rightChild = leftChild + 1;
			// find larger child
			if (rightChild < currentSize && // right ch exists?
					heapArray[leftChild].getKey() < heapArray[rightChild].getKey()) {
				largerChild = rightChild;
			} else {
				largerChild = leftChild;
			}
			// top >= largerChild?
			if (top.getKey() >= heapArray[largerChild].getKey()) {
				break;
			}
			// shift child up
			heapArray[index] = heapArray[largerChild];
			index = largerChild; // go down
		} // end while
		heapArray[index] = top; // root to index
	} // end trickleDown()
} // end class Heap
////////////////////////////////////////////////////////////////

class HeapSortApp {
	// -------------------------------------------------------------
	public static int getInt() throws IOException {
		final String s = getString();
		return Integer.parseInt(s);
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	public static void main(final String[] args) throws IOException {
		int size, j;

		System.out.print("Enter number of items: ");
		size = getInt();
		final Heap2 theHeap = new Heap2(size);

		for (j = 0; j < size; j++) // fill array with
		{ // random nodes
			final int random = (int) (java.lang.Math.random() * 100);
			final Node2 newNode = new Node2(random);
			theHeap.insertAt(j, newNode);
			theHeap.incrementSize();
		}

		System.out.print("Random: ");
		theHeap.displayArray(); // display random array

		for (j = size / 2 - 1; j >= 0; j--) {
			theHeap.trickleDown(j);
		}

		System.out.print("Heap:   ");
		theHeap.displayArray(); // dislay heap array
		theHeap.displayHeap(); // display heap

		for (j = size - 1; j >= 0; j--) // remove from heap and
		{ // store at array end
			final Node2 biggestNode = theHeap.remove();
			theHeap.insertAt(j, biggestNode);
		}
		System.out.print("Sorted: ");
		theHeap.displayArray(); // display sorted array
	} // end main()
} // end class HeapSortApp
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class Node2 {
	private final int iData; // data item (key)
	// -------------------------------------------------------------

	public Node2(final int key) // constructor
	{
		iData = key;
	}

	// -------------------------------------------------------------
	public int getKey() {
		return iData;
	}
	// -------------------------------------------------------------
} // end class Node
