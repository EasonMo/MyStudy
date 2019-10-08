package com.dataStAndAl.ch11_hash;

// hash.java
// demonstrates hash table with linear probing
// to run this program: C:>java HashTableApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class DataItem { // (could have more data)
	private final int iData; // data item (key)
	// --------------------------------------------------------------

	public DataItem(final int ii) // constructor
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

class HashTable {
	private final DataItem2[] hashArray; // array holds hash table
	private final int arraySize;
	private final DataItem2 nonItem; // for deleted items
	// -------------------------------------------------------------

	public HashTable(final int size) // constructor
	{
		arraySize = size;
		hashArray = new DataItem2[arraySize];
		nonItem = new DataItem2(-1); // deleted item key is -1
	}

	// -------------------------------------------------------------
	public DataItem2 delete(final int key) // delete a DataItem
	{
		int hashVal = hashFunc(key); // hash the key

		while (hashArray[hashVal] != null) // until empty cell,
		{ // found the key?
			if (hashArray[hashVal].getKey() == key) {
				final DataItem2 temp = hashArray[hashVal]; // save item
				hashArray[hashVal] = nonItem; // delete item
				return temp; // return item
			}
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
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
	{
		int hashVal = hashFunc(key); // hash the key

		while (hashArray[hashVal] != null) // until empty cell,
		{ // found the key?
			if (hashArray[hashVal].getKey() == key) {
				return hashArray[hashVal]; // yes, return item
			}
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
		}
		return null; // can't find item
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public int hashFunc(final int key) {
		return key % arraySize; // hash function
	}

	// -------------------------------------------------------------
	public void insert(final DataItem2 item) // insert a DataItem
	// (assumes table not full)
	{
		final int key = item.getKey(); // extract key
		int hashVal = hashFunc(key); // hash the key
										// until empty cell or -1,
		while (hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
		}
		hashArray[hashVal] = item; // insert item
	} // end insert()
} // end class HashTable
	////////////////////////////////////////////////////////////////

class HashTableApp {
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
		DataItem2 aDataItem;
		int aKey, size, n, keysPerCell;
		// get sizes
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		keysPerCell = 10;
		// make table
		final HashTable theHashTable = new HashTable(size);

		for (int j = 0; j < n; j++) // insert data
		{
			aKey = (int) (java.lang.Math.random() * keysPerCell * size);
			aDataItem = new DataItem2(aKey);
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
				aDataItem = new DataItem2(aKey);
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
} // end class HashTableApp
	////////////////////////////////////////////////////////////////
