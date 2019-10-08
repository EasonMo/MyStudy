package com.dataStAndAl.ch08_tree;

// tree.java
// demonstrates binary tree
// to run this program: C>java TreeApp
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// for Stack class
import java.util.Stack;

////////////////////////////////////////////////////////////////
class Node {

	public int iData; // data item (key)
	public double dData; // data item
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child

	public void displayNode() // display ourself
	{
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(dData);
		System.out.print("} ");
	}
} // end class Node
	////////////////////////////////////////////////////////////////

class Tree {
	private Node root; // first node of tree

	// -------------------------------------------------------------
	public Tree() // constructor
	{
		root = null;
	} // no nodes in tree yet
		// -------------------------------------------------------------

	public boolean delete(final int key) // delete node with given key
	{ // (assumes non-empty list)
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;// current节点的属性

		while (current.iData != key) // search for node
		{
			parent = current;
			if (key < current.iData) // go left?
			{
				isLeftChild = true;
				current = current.leftChild;
			} else // or go right?
			{
				isLeftChild = false;
				current = current.rightChild;
			}
			if (current == null) {
				return false; // didn't find it
			}
		} // end while
			// found node to delete

		// 没有子节点
		// if no children, simply delete it
		if (current.leftChild == null && current.rightChild == null) {
			if (current == root) {
				root = null; // tree is empty
			} else if (isLeftChild) {
				parent.leftChild = null; // disconnect
			} else {
				parent.rightChild = null;
			}
		}

		// 有一个子节点
		// if no right child, replace with left subtree
		else if (current.rightChild == null) {
			if (current == root) {
				root = current.leftChild;
			} else if (isLeftChild) {
				parent.leftChild = current.leftChild;
			} else {
				parent.rightChild = current.leftChild;
			}
		} else if (current.leftChild == null) {
			if (current == root) {
				root = current.rightChild;
			} else if (isLeftChild) {
				parent.leftChild = current.rightChild;
			} else {
				parent.rightChild = current.rightChild;
			}
		} else // two children, so replace with inorder successor
		// 有两个子节点
		{
			// get successor of node to delete (current)
			final Node successor = getSuccessor(current);

			// connect parent of current to successor instead
			if (current == root) {
				root = successor;
			} else if (isLeftChild) {
				parent.leftChild = successor;
			} else {
				parent.rightChild = successor;
			}

			// connect successor to current's left child
			successor.leftChild = current.leftChild;
		} // end else two children
			// (successor cannot have a left child)
		return true; // success
	} // end delete()
		// -------------------------------------------------------------

	public void displayTree() {
		final Stack<Node> globalStack = new Stack<Node>();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println("......................................................");
		while (isRowEmpty == false) {
			final Stack<Node> localStack = new Stack<Node>();
			isRowEmpty = true;

			for (int j = 0; j < nBlanks; j++) {
				System.out.print(' ');
			}

			while (globalStack.isEmpty() == false) {
				final Node temp = globalStack.pop();
				if (temp != null) {
					System.out.print(temp.iData);
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);

					if (temp.leftChild != null || temp.rightChild != null) {
						isRowEmpty = false;
					}
				} else {
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for (int j = 0; j < nBlanks * 2 - 2; j++) {
					System.out.print(' ');
				}
			} // end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while (localStack.isEmpty() == false) {
				globalStack.push(localStack.pop());
			}
		} // end while isRowEmpty is false
		System.out.println("......................................................");
	} // end displayTree()
		// -------------------------------------------------------------
		// -------------------------------------------------------------

	public Node find(final int key) // find node with given key
	{ // (assumes non-empty tree)
		Node current = root; // start at root
		while (current.iData != key) // while no match,
		{
			if (key < current.iData) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
			if (current == null) {
				return null; // didn't find it
			}
		}
		return current; // found it
	} // end find()
		// -------------------------------------------------------------

	public void insert(final int id, final double dd) {
		final Node newNode = new Node(); // make new node
		newNode.iData = id; // insert data
		newNode.dData = dd;
		if (root == null) {
			root = newNode;
		} else // root occupied
		{
			Node current = root; // start at root
			Node parent;
			while (true) // (exits internally)
			{
				parent = current;
				if (id < current.iData) // go left?
				{
					current = current.leftChild;
					if (current == null) // if end of the line,
					{ // insert on left
						parent.leftChild = newNode;
						return;
					}
				} // end if go left
				else // or go right?
				{
					current = current.rightChild;
					if (current == null) // if end of the line
					{ // insert on right
						parent.rightChild = newNode;
						return;
					}
				} // end else go right
			} // end while
		} // end else not root
	} // end insert()
		// -------------------------------------------------------------

	public void traverse(final int traverseType) {
		switch (traverseType) {
		case 1:
			System.out.print("\nPreorder traversal: ");
			preOrder(root);
			break;
		case 2:
			System.out.print("\nInorder traversal:  ");
			inOrder(root);
			break;
		case 3:
			System.out.print("\nPostorder traversal: ");
			postOrder(root);
			break;
		}
		System.out.println();
	}

	// -------------------------------------------------------------
	// returns node with next-highest value after delNode
	// goes to right child, then right child's left descendents
	private Node getSuccessor(final Node delNode) {
		Node successorParent = delNode;
		Node successor = delNode;
		Node current = delNode.rightChild; // go to right child
		// 找最后一个左子节点
		while (current != null) // until no more
		{ // left children,
			successorParent = successor;
			successor = current;
			current = current.leftChild; // go to left child
		}
		// 判断是不是被删除的节点的右子节点
		// if successor not
		if (successor != delNode.rightChild) // right child,
		{ // make connections
			successorParent.leftChild = successor.rightChild;// 完成后继节点的子节点的连接（包含了后继节点是左子节点的情况）
			successor.rightChild = delNode.rightChild;
		}
		return successor;// 返回后继节点替换删除节点后调整完的子树
	}

	// -------------------------------------------------------------
	private void inOrder(final Node localRoot) {
		if (localRoot != null) {
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.iData + " ");
			inOrder(localRoot.rightChild);
		}
	}

	// -------------------------------------------------------------
	private void postOrder(final Node localRoot) {
		if (localRoot != null) {
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.print(localRoot.iData + " ");
		}
	}

	// -------------------------------------------------------------
	private void preOrder(final Node localRoot) {
		if (localRoot != null) {
			System.out.print(localRoot.iData + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}
} // end class Tree
	////////////////////////////////////////////////////////////////

class TreeApp {
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
		int value;
		final Tree theTree = new Tree();

		theTree.insert(50, 1.5);
		theTree.insert(25, 1.2);
		theTree.insert(75, 1.7);
		theTree.insert(12, 1.5);
		theTree.insert(37, 1.2);
		theTree.insert(43, 1.7);
		theTree.insert(30, 1.5);
		theTree.insert(33, 1.2);
		theTree.insert(87, 1.7);
		theTree.insert(93, 1.5);
		theTree.insert(97, 1.5);

		while (true) {
			System.out.print("Enter first letter of show, ");
			System.out.print("insert, find, delete, or traverse: ");
			final int choice = getChar();
			switch (choice) {
			case 's':
				theTree.displayTree();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				theTree.insert(value, value + 0.9);
				break;
			case 'f':
				System.out.print("Enter value to find: ");
				value = getInt();
				final Node found = theTree.find(value);
				if (found != null) {
					System.out.print("Found: ");
					found.displayNode();
					System.out.print("\n");
				} else {
					System.out.print("Could not find ");
				}
				System.out.print(value + '\n');
				break;
			case 'd':
				System.out.print("Enter value to delete: ");
				value = getInt();
				final boolean didDelete = theTree.delete(value);
				if (didDelete) {
					System.out.print("Deleted " + value + '\n');
				} else {
					System.out.print("Could not delete ");
				}
				System.out.print(value + '\n');
				break;
			case 't':
				System.out.print("Enter type 1, 2 or 3: ");
				value = getInt();
				theTree.traverse(value);
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
		} // end while
	} // end main()
} // end class TreeApp
	////////////////////////////////////////////////////////////////
