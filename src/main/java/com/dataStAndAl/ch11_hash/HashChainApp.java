package com.dataStAndAl.ch11_hash;

// hashChain.java
// demonstrates hash table with separate chaining
// to run this program: C:>java HashChainApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class HashChainApp {
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

	// --------------------------------------------------------------
	// --------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	public static void main(final String[] args) throws IOException {
		int aKey;
		Link aDataItem;
		int size, n;
		final int keysPerCell = 100;
		// get sizes
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		// make table
		final HashTable3 theHashTable = new HashTable3(size);

		for (int j = 0; j < n; j++) // insert data
		{
			aKey = (int) (java.lang.Math.random() * keysPerCell * size);
			aDataItem = new Link(aKey);
			theHashTable.insert(aDataItem);
		}
		while (true) // interact with user
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, delete, or find: ");
			final char choice = getChar();
			switch (choice) {
			case 's':
				theHashTable.displayTable();
				break;
			case 'i':
				System.out.print("Enter key value to insert: ");
				aKey = getInt();
				aDataItem = new Link(aKey);
				theHashTable.insert(aDataItem);
				break;
			case 'd':
				System.out.print("Enter key value to delete: ");
				aKey = getInt();
				theHashTable.delete(aKey);
				break;
			case 'f':
				System.out.print("Enter key value to find: ");
				aKey = getInt();
				aDataItem = theHashTable.find(aKey);
				if (aDataItem != null) {
					System.out.println("Found " + aKey);
				} else {
					System.out.println("Could not find " + aKey);
				}
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
		} // end while
	} // end main()
} // end class HashChainApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class HashTable3 {
	private final SortedList[] hashArray; // array of lists
	private final int arraySize;

	// -------------------------------------------------------------
	public HashTable3(final int size) // constructor
	{
		arraySize = size;
		hashArray = new SortedList[arraySize]; // create array
		for (int j = 0; j < arraySize; j++) {
			hashArray[j] = new SortedList(); // with lists
		}
	}

	// -------------------------------------------------------------
	public void delete(final int key) // delete a link
	{
		final int hashVal = hashFunc(key); // hash the key
		hashArray[hashVal].delete(key); // delete link
	} // end delete()
		// -------------------------------------------------------------

	public void displayTable() {
		for (int j = 0; j < arraySize; j++) // for each cell,
		{
			System.out.print(j + ". "); // display cell number
			hashArray[j].displayList(); // display list
		}
	}

	// -------------------------------------------------------------
	public Link find(final int key) // find link
	{
		final int hashVal = hashFunc(key); // hash the key
		final Link theLink = hashArray[hashVal].find(key); // get link
		return theLink; // return link
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public int hashFunc(final int key) // hash function
	{
		return key % arraySize;
	}

	// -------------------------------------------------------------
	public void insert(final Link theLink) // insert a link
	{
		final int key = theLink.getKey();
		final int hashVal = hashFunc(key); // hash the key
		hashArray[hashVal].insert(theLink); // insert at hashVal
	} // end insert()
} // end class HashTable
	////////////////////////////////////////////////////////////////

class Link { // (could be other items)
	private final int iData; // data item
	public Link next; // next link in list
	// -------------------------------------------------------------

	public Link(final int it) // constructor
	{
		iData = it;
	}

	// -------------------------------------------------------------
	public void displayLink() // display this link
	{
		System.out.print(iData + " ");
	}

	// -------------------------------------------------------------
	public int getKey() {
		return iData;
	}
} // end class Link
	////////////////////////////////////////////////////////////////

class SortedList {
	private Link first; // ref to first list item
	// -------------------------------------------------------------

	public void delete(final int key) // delete link
	{ // (assumes non-empty list)
		Link previous = null; // start at first
		Link current = first;
		// until end of list,
		while (current != null && key != current.getKey()) { // or key ==
																// current,
			previous = current;
			current = current.next; // go to next link
		}
		// disconnect link
		if (previous == null) {
			first = first.next; // delete first link
		} else {
			previous.next = current.next; // delete current link
		}
	} // end delete()
		// -------------------------------------------------------------

	public void displayList() {
		System.out.print("List (first-->last): ");
		Link current = first; // start at beginning of list
		while (current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	public Link find(final int key) // find link
	{
		Link current = first; // start at first
								// until end of list,
		while (current != null && current.getKey() <= key) { // or key too
																// small,
			if (current.getKey() == key) {
				return current; // found it, return link
			}
			current = current.next; // go to next item
		}
		return null; // didn't find it
	} // end find()
		// -------------------------------------------------------------

	public void insert(final Link theLink) // insert link, in order
	{
		final int key = theLink.getKey();
		Link previous = null; // start at first
		Link current = first;
		// until end of list,
		while (current != null && key > current.getKey()) { // or current > key,
			previous = current;
			current = current.next; // go to next item
		}
		if (previous == null) {
			first = theLink; // first --> new link
		} else {
			previous.next = theLink; // prev --> new link
		}
		theLink.next = current; // new link --> current
	} // end insert()
		// -------------------------------------------------------------

	public void SortedList() // constructor
	{
		first = null;
	}
} // end class SortedList
