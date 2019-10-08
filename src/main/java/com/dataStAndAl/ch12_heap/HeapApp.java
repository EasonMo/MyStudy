package com.dataStAndAl.ch12_heap;

// heap.java
// demonstrates heaps
// to run this program: C>java HeapApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class Heap {
	private final Node[] heapArray;
	private final int maxSize; // size of array
	private int currentSize; // number of nodes in array
	// -------------------------------------------------------------

	public Heap(final int mx) // constructor
	{
		maxSize = mx;
		currentSize = 0;
		heapArray = new Node[maxSize]; // create array
	}

	// -------------------------------------------------------------
	public boolean change(final int index, final int newValue) {
		if (index < 0 || index >= currentSize) {
			return false;
		}
		final int oldValue = heapArray[index].getKey(); // remember old
		heapArray[index].setKey(newValue); // change to new

		if (oldValue < newValue) {
			trickleUp(index); // trickle it up
		} else {
			trickleDown(index); // trickle it down
		}
		return true;
	} // end change()
	// -------------------------------------------------------------

	public void displayHeap() {
		System.out.print("heapArray: "); // array format
		for (int m = 0; m < currentSize; m++) {
			if (heapArray[m] != null) {
				System.out.print(heapArray[m].getKey() + " ");
			} else {
				System.out.print("-- ");
			}
		}
		System.out.println();
		// heap format
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
	// -------------------------------------------------------------

	public boolean insert(final int key) {
		if (currentSize == maxSize) {
			return false;
		}
		final Node newNode = new Node(key);
		heapArray[currentSize] = newNode;
		trickleUp(currentSize++);
		return true;
	} // end insert()
	// -------------------------------------------------------------

	public boolean isEmpty() {
		return currentSize == 0;
	}

	// -------------------------------------------------------------
	public Node remove() // delete item with max key
	{ // (assumes non-empty list)
		final Node root = heapArray[0];
		heapArray[0] = heapArray[--currentSize];
		trickleDown(0);
		return root;
	} // end remove()
	// -------------------------------------------------------------

	public void trickleDown(int index) {
		int largerChild;
		final Node top = heapArray[index]; // save root
		while (index < currentSize / 2) // while node has at
		{ // least one child,
			final int leftChild = 2 * index + 1;
			final int rightChild = leftChild + 1;
			// find larger child
			if (rightChild < currentSize && // (rightChild exists?)
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
	// -------------------------------------------------------------

	public void trickleUp(int index) {
		int parent = (index - 1) / 2;
		final Node bottom = heapArray[index];

		while (index > 0 && heapArray[parent].getKey() < bottom.getKey()) {
			heapArray[index] = heapArray[parent]; // move it down
			index = parent;
			parent = (parent - 1) / 2;
		} // end while
		heapArray[index] = bottom;
	} // end trickleUp()
} // end class Heap
////////////////////////////////////////////////////////////////

class HeapApp {
	// -------------------------------------------------------------
	public static char getChar() throws IOException {
		final String s = getString();
		return s.charAt(0);
	}

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
		int value, value2;
		final Heap theHeap = new Heap(31); // make a Heap; max size 31
		boolean success;

		theHeap.insert(70); // insert 10 items
		theHeap.insert(40);
		theHeap.insert(50);
		theHeap.insert(20);
		theHeap.insert(60);
		theHeap.insert(100);
		theHeap.insert(80);
		theHeap.insert(30);
		theHeap.insert(10);
		theHeap.insert(90);

		while (true) // until [Ctrl]-[C]
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, remove, change: ");
			final int choice = getChar();
			switch (choice) {
			case 's': // show
				theHeap.displayHeap();
				break;
			case 'i': // insert
				System.out.print("Enter value to insert: ");
				value = getInt();
				success = theHeap.insert(value);
				if (!success) {
					System.out.println("Can't insert; heap full");
				}
				break;
			case 'r': // remove
				if (!theHeap.isEmpty()) {
					theHeap.remove();
				} else {
					System.out.println("Can't remove; heap empty");
				}
				break;
			case 'c': // change
				System.out.print("Enter current index of item: ");
				value = getInt();
				System.out.print("Enter new key: ");
				value2 = getInt();
				success = theHeap.change(value, value2);
				if (!success) {
					System.out.println("Invalid index");
				}
				break;
			default:
				System.out.println("Invalid entry\n");
			} // end switch
		} // end while
	} // end main()
} // end class HeapApp
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class Node {
	private int iData; // data item (key)
	// -------------------------------------------------------------

	public Node(final int key) // constructor
	{
		iData = key;
	}

	// -------------------------------------------------------------
	public int getKey() {
		return iData;
	}

	// -------------------------------------------------------------
	public void setKey(final int id) {
		iData = id;
	}
	// -------------------------------------------------------------
} // end class Node
