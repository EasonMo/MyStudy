package com.dataStAndAl.ch11_hash;

// hashDouble.java
// demonstrates hash table with double hashing
// to run this program: C:>java HashDoubleApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class DataItem2 { // (could have more items)
	private final int iData; // data item (key)
	// --------------------------------------------------------------

	public DataItem2(final int ii) // constructor
	{
		iData = ii;
	}

	// --------------------------------------------------------------
	public int getKey() {
		return iData;
	}
	// --------------------------------------------------------------
} // end class DataItem
	////////////////////////////////////////////////////////////////

class HashDoubleApp {
	// --------------------------------------------------------------
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
		DataItem2 aDataItem;
		int size, n;
		// get sizes
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		// make table
		final HashTable2 theHashTable = new HashTable2(size);

		for (int j = 0; j < n; j++) // insert data
		{
			aKey = (int) (java.lang.Math.random() * 2 * size);
			aDataItem = new DataItem2(aKey);
			theHashTable.insert(aKey, aDataItem);
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
				aDataItem = new DataItem2(aKey);
				theHashTable.insert(aKey, aDataItem);
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
} // end class HashDoubleApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class HashTable2 {
	private final DataItem2[] hashArray; // array is the hash table
	private final int arraySize;
	private final DataItem2 nonItem; // for deleted items
	// -------------------------------------------------------------

	HashTable2(final int size) // constructor
	{
		arraySize = size;
		hashArray = new DataItem2[arraySize];
		nonItem = new DataItem2(-1);
	}

	// -------------------------------------------------------------
	public DataItem2 delete(final int key) // delete a DataItem
	{
		int hashVal = hashFunc1(key); // hash the key
		final int stepSize = hashFunc2(key); // get step size

		while (hashArray[hashVal] != null) // until empty cell,
		{ // is correct hashVal?
			if (hashArray[hashVal].getKey() == key) {
				final DataItem2 temp = hashArray[hashVal]; // save item
				hashArray[hashVal] = nonItem; // delete item
				return temp; // return item
			}
			hashVal += stepSize; // add the step
			hashVal %= arraySize; // for wraparound
		}
		return null; // can't find item
	} // end delete()
		// -------------------------------------------------------------

	public void displayTable() {
		System.out.print("Table: ");
		for (int j = 0; j < arraySize; j++) {
			if (hashArray[j] != null) {
				System.out.print(hashArray[j].getKey() + " ");
			} else {
				System.out.print("** ");
			}
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	public DataItem2 find(final int key) // find item with key
	// (assumes table not full)
	{
		int hashVal = hashFunc1(key); // hash the key
		final int stepSize = hashFunc2(key); // get step size

		while (hashArray[hashVal] != null) // until empty cell,
		{ // is correct hashVal?
			if (hashArray[hashVal].getKey() == key) {
				return hashArray[hashVal]; // yes, return item
			}
			hashVal += stepSize; // add the step
			hashVal %= arraySize; // for wraparound
		}
		return null; // can't find item
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public int hashFunc1(final int key) {
		return key % arraySize;
	}

	// -------------------------------------------------------------
	public int hashFunc2(final int key) {
		// non-zero, less than array size, different from hF1
		// array size must be relatively prime to 5, 4, 3, and 2
		return 5 - key % 5;
	}

	// -------------------------------------------------------------
	// insert a DataItem
	public void insert(final int key, final DataItem2 item)
	// (assumes table not full)
	{
		int hashVal = hashFunc1(key); // hash the key
		final int stepSize = hashFunc2(key); // get step size
		// until empty cell or -1
		while (hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
			hashVal += stepSize; // add the step
			hashVal %= arraySize; // for wraparound
		}
		hashArray[hashVal] = item; // insert item
	} // end insert()
} // end class HashTable
