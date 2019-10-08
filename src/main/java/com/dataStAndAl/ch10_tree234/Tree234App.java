package com.dataStAndAl.ch10_tree234;

// tree234.java
// demonstrates 234 tree
// to run this program: C>java Tree234App
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class DataItem

{
	public long dData; // one data item
	// --------------------------------------------------------------

	public DataItem(final long dd) // constructor
	{
		dData = dd;
	}

	// --------------------------------------------------------------
	public void displayItem() // display item, format "/27"
	{
		System.out.print("/" + dData);
	}
	// --------------------------------------------------------------
} // end class DataItem
	////////////////////////////////////////////////////////////////

class Node {
	private static final int ORDER = 4;
	private int numItems;
	private Node parent;
	private final Node childArray[] = new Node[ORDER];
	private final DataItem itemArray[] = new DataItem[ORDER - 1];

	// -------------------------------------------------------------
	// connect child to this node
	public void connectChild(final int childNum, final Node child) {
		childArray[childNum] = child;
		if (child != null) {
			child.parent = this;
		}
	}

	// -------------------------------------------------------------
	// disconnect child from this node, return it
	public Node disconnectChild(final int childNum) {
		final Node tempNode = childArray[childNum];
		childArray[childNum] = null;
		return tempNode;
	}

	// -------------------------------------------------------------
	public void displayNode() // format "/24/56/74/"
	{
		for (int j = 0; j < numItems; j++) {
			itemArray[j].displayItem(); // "/56"
		}
		System.out.println("/"); // final "/"
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public int findItem(final long key) // return index of
	{ // item (within node)
		for (int j = 0; j < ORDER - 1; j++) // if found,
		{ // otherwise,
			if (itemArray[j] == null) {
				break;
			} else if (itemArray[j].dData == key) {
				return j;
			}
		}
		return -1;
	} // end findItem
		// -------------------------------------------------------------

	public Node getChild(final int childNum) {
		return childArray[childNum];
	}

	// -------------------------------------------------------------
	public DataItem getItem(final int index) // get DataItem at index
	{
		return itemArray[index];
	}

	// -------------------------------------------------------------
	public int getNumItems() {
		return numItems;
	}

	// -------------------------------------------------------------
	public Node getParent() {
		return parent;
	}

	// -------------------------------------------------------------
	public int insertItem(final DataItem newItem) {
		// assumes node is not full
		numItems++; // will add new item
		final long newKey = newItem.dData; // key of new item

		for (int j = ORDER - 2; j >= 0; j--) // start on right,
		{ // examine items
			if (itemArray[j] == null) {
				continue; // go left one cell
			} else // not null,
			{ // get its key
				final long itsKey = itemArray[j].dData;
				if (newKey < itsKey) {
					itemArray[j + 1] = itemArray[j]; // shift it right
				} else {
					itemArray[j + 1] = newItem; // insert new item
					return j + 1; // return index to
				} // new item
			} // end else (not null)
		} // end for // shifted all items,
		itemArray[0] = newItem; // insert new item
		return 0;
	} // end insertItem()
		// -------------------------------------------------------------

	public boolean isFull() {
		return (numItems == ORDER - 1) ? true : false;
	}

	// -------------------------------------------------------------
	public boolean isLeaf() {
		return (childArray[0] == null) ? true : false;
	}

	// -------------------------------------------------------------
	public DataItem removeItem() // remove largest item
	{
		// assumes node not empty
		final DataItem temp = itemArray[numItems - 1]; // save item
		itemArray[numItems - 1] = null; // disconnect it
		numItems--; // one less item
		return temp; // return item
	}
} // end class Node
	////////////////////////////////////////////////////////////////

class Tree234 {
	private Node root = new Node(); // make root node
	// -------------------------------------------------------------

	public void displayTree() {
		recDisplayTree(root, 0, 0);
	}

	// -------------------------------------------------------------
	public int find(final long key) {
		Node curNode = root;
		int childNumber;
		while (true) {
			if ((childNumber = curNode.findItem(key)) != -1) {
				return childNumber; // found it
			} else if (curNode.isLeaf()) {
				return -1; // can't find it
			} else {
				curNode = getNextChild(curNode, key);
			}
		} // end while
	}

	// -------------------------------------------------------------
	// gets appropriate child of node during search for value
	public Node getNextChild(final Node theNode, final long theValue) {
		int j;
		// assumes node is not empty, not full, not a leaf
		final int numItems = theNode.getNumItems();
		for (j = 0; j < numItems; j++) // for each item in node
		{ // are we less?
			if (theValue < theNode.getItem(j).dData) {
				return theNode.getChild(j); // return left child
			}
		} // end for // we're greater, so
		return theNode.getChild(j); // return right child
	}

	// -------------------------------------------------------------
	// insert a DataItem
	public void insert(final long dValue) {
		Node curNode = root;
		final DataItem tempItem = new DataItem(dValue);

		while (true) {
			if (curNode.isFull()) // if node full,
			{
				split(curNode); // split it
				curNode = curNode.getParent(); // back up
												// search once
				curNode = getNextChild(curNode, dValue);
			} // end if(node is full)

			else if (curNode.isLeaf()) {
				break; // go insert
				// node is not full, not a leaf; so go to lower level
			} else {
				curNode = getNextChild(curNode, dValue);
			}
		} // end while

		curNode.insertItem(tempItem); // insert new DataItem
	} // end insert()
		// -------------------------------------------------------------

	public void split(final Node thisNode) // split the node
	{
		// assumes node is full
		DataItem itemB, itemC;
		Node parent, child2, child3;
		int itemIndex;

		itemC = thisNode.removeItem(); // remove items from
		itemB = thisNode.removeItem(); // this node
		child2 = thisNode.disconnectChild(2); // remove children
		child3 = thisNode.disconnectChild(3); // from this node

		final Node newRight = new Node(); // make new node

		if (thisNode == root) // if this is the root,
		{
			root = new Node(); // make new root
			parent = root; // root is our parent
			root.connectChild(0, thisNode); // connect to parent
		} else {
			parent = thisNode.getParent(); // get parent
		}

		// deal with parent
		itemIndex = parent.insertItem(itemB); // item B to parent
		final int n = parent.getNumItems(); // total items?

		// 这里用来纠正上移数据项后的父节点的子节点的顺序
		for (int j = n - 1; j > itemIndex; j--) // move parent's
		{ // connections
			final Node temp = parent.disconnectChild(j); // one child
			parent.connectChild(j + 1, temp); // to the right
		}
		// connect newRight to parent
		parent.connectChild(itemIndex + 1, newRight);

		// deal with newRight
		newRight.insertItem(itemC); // item C to newRight
		newRight.connectChild(0, child2); // connect to 0 and 1
		newRight.connectChild(1, child3); // on newRight
	} // end split()
		// -------------------------------------------------------------

	private void recDisplayTree(final Node thisNode, final int level, final int childNumber) {
		System.out.print("level=" + level + " child=" + childNumber + " ");
		thisNode.displayNode(); // display this node

		// call ourselves for each child of this node
		final int numItems = thisNode.getNumItems();
		for (int j = 0; j < numItems + 1; j++) {
			final Node nextNode = thisNode.getChild(j);
			if (nextNode != null) {
				recDisplayTree(nextNode, level + 1, j);
			} else {
				return;
			}
		}
	} // end recDisplayTree()
		// -------------------------------------------------------------\
} // end class Tree234
	////////////////////////////////////////////////////////////////

class Tree234App {
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

	// -------------------------------------------------------------
	// --------------------------------------------------------------
	public static String getString() throws IOException {
		final InputStreamReader isr = new InputStreamReader(System.in);
		final BufferedReader br = new BufferedReader(isr);
		final String s = br.readLine();
		return s;
	}

	public static void main(final String[] args) throws IOException {
		long value;
		final Tree234 theTree = new Tree234();

		theTree.insert(50);
		theTree.insert(40);
		theTree.insert(60);
		theTree.insert(30);
		theTree.insert(70);

		while (true) {
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, or find: ");
			final char choice = getChar();
			switch (choice) {
			case 's':
				theTree.displayTree();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				theTree.insert(value);
				break;
			case 'f':
				System.out.print("Enter value to find: ");
				value = getInt();
				final int found = theTree.find(value);
				if (found != -1) {
					System.out.println("Found " + value);
				} else {
					System.out.println("Could not find " + value);
				}
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
		} // end while
	} // end main()
} // end class Tree234App
	////////////////////////////////////////////////////////////////
