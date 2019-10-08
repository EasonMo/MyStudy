package com.dataStAndAl.ch13_graph;

//dfs.java
//demonstrates depth-first search
//to run this program: C>java DFSApp

class DFSApp {
	public static void main(final String[] args) {
		final Graph theGraph = new Graph();
		theGraph.addVertex('A'); // 0 (start for dfs)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4

		theGraph.addEdge(0, 1); // AB
		theGraph.addEdge(1, 2); // BC
		theGraph.addEdge(0, 3); // AD
		theGraph.addEdge(3, 4); // DE

		System.out.print("Visits: ");
		theGraph.dfs(); // depth-first search
		System.out.println();
	} // end main()
} // end class DFSApp

class Graph {
	private final int MAX_VERTS = 20;
	private final Vertex vertexList[]; // list of vertices
	private final int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private final StackX theStack;

	// ------------------------------------------------------------
	public Graph() // constructor
	{
		vertexList = new Vertex[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int y = 0; y < MAX_VERTS; y++) {
			for (int x = 0; x < MAX_VERTS; x++) {
				adjMat[x][y] = 0;
			}
		}
		theStack = new StackX();
	} // end constructor
		// ------------------------------------------------------------

	public void addEdge(final int start, final int end) {
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}

	// ------------------------------------------------------------
	public void addVertex(final char lab) {
		vertexList[nVerts++] = new Vertex(lab);
	}

	// ------------------------------------------------------------
	public void dfs() // depth-first search
	{ // begin at vertex 0
		vertexList[0].wasVisited = true; // mark it
		displayVertex(0); // display it
		theStack.push(0); // push it

		while (!theStack.isEmpty()) // until stack empty,
		{
			// get an unvisited vertex adjacent to stack top
			final int v = getAdjUnvisitedVertex(theStack.peek());
			if (v == -1) {
				theStack.pop();
			} else // if it exists,
			{
				vertexList[v].wasVisited = true; // mark it
				displayVertex(v); // display it
				theStack.push(v); // push it
			}
		} // end while

		// stack is empty, so we're done
		for (int j = 0; j < nVerts; j++) {
			vertexList[j].wasVisited = false;
		}
	} // end dfs
		// ------------------------------------------------------------

	public void displayVertex(final int v) {
		System.out.print(vertexList[v].label);
	}

	// ------------------------------------------------------------
	// returns an unvisited vertex adj to v
	public int getAdjUnvisitedVertex(final int v) {
		for (int j = 0; j < nVerts; j++) {
			if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false) {
				return j;
			}
		}
		return -1;
	} // end getAdjUnvisitedVertex()
		// ------------------------------------------------------------
} // end class Graph

class StackX {
	private final int SIZE = 20;
	private final int[] st;
	private int top;

	// ------------------------------------------------------------
	public StackX() // constructor
	{
		st = new int[SIZE]; // make array
		top = -1;
	}

	// ------------------------------------------------------------
	public boolean isEmpty() // true if nothing on stack
	{
		return (top == -1);
	}

	// ------------------------------------------------------------
	// ------------------------------------------------------------
	public int peek() // peek at top of stack
	{
		return st[top];
	}

	// ------------------------------------------------------------
	public int pop() // take item off stack
	{
		return st[top--];
	}

	// ------------------------------------------------------------
	public void push(final int j) // put item on stack
	{
		st[++top] = j;
	}
} // end class StackX

class Vertex {
	public char label; // label (e.g. 'A')
	public boolean wasVisited;

	// ------------------------------------------------------------
	public Vertex(final char lab) // constructor
	{
		label = lab;
		wasVisited = false;
	}
	// ------------------------------------------------------------
} // end class Vertex
