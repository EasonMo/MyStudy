package com.dataStAndAl.ch13_graph;

// mst.java
// demonstrates minimum spanning tree
// to run this program: C>java MSTApp

class Graph3 {
	private final int MAX_VERTS = 20;
	private final Vertex3 vertexList[]; // list of vertices
	private final int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private final StackX3 theStack;

	// -------------------------------------------------------------
	public Graph3() // constructor
	{
		vertexList = new Vertex3[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int j = 0; j < MAX_VERTS; j++) {
			for (int k = 0; k < MAX_VERTS; k++) {
				adjMat[j][k] = 0;
			}
		}
		theStack = new StackX3();
	} // end constructor
		// -------------------------------------------------------------

	public void addEdge(final int start, final int end) {
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}

	// -------------------------------------------------------------
	public void addVertex(final char lab) {
		vertexList[nVerts++] = new Vertex3(lab);
	}

	// -------------------------------------------------------------
	public void displayVertex(final int v) {
		System.out.print(vertexList[v].label);
	}

	// -------------------------------------------------------------
	// returns an unvisited vertex adj to v
	public int getAdjUnvisitedVertex(final int v) {
		for (int j = 0; j < nVerts; j++) {
			if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false) {
				return j;
			}
		}
		return -1;
	} // end getAdjUnvisitedVert()
		// -------------------------------------------------------------
		// -------------------------------------------------------------

	public void mst() // minimum spanning tree (depth first)
	{ // start at 0
		vertexList[0].wasVisited = true; // mark it
		theStack.push(0); // push it

		while (!theStack.isEmpty()) // until stack empty
		{ // get stack top
			final int currentVertex = theStack.peek();
			// get next unvisited neighbor
			final int v = getAdjUnvisitedVertex(currentVertex);
			if (v == -1) {
				theStack.pop(); // pop it away
			} else // got a neighbor
			{
				vertexList[v].wasVisited = true; // mark it
				theStack.push(v); // push it
				// display edge
				displayVertex(currentVertex); // from currentV
				displayVertex(v); // to v
				System.out.print(" ");
			}
		} // end while(stack not empty)

		// stack is empty, so we're done
		for (int j = 0; j < nVerts; j++) {
			vertexList[j].wasVisited = false;
		}
	} // end mst()
} // end class Graph
	////////////////////////////////////////////////////////////////

class MSTApp {
	public static void main(final String[] args) {
		final Graph3 theGraph = new Graph3();
		theGraph.addVertex('A'); // 0 (start for mst)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4

		theGraph.addEdge(0, 1); // AB
		theGraph.addEdge(0, 2); // AC
		theGraph.addEdge(0, 3); // AD
		theGraph.addEdge(0, 4); // AE
		theGraph.addEdge(1, 2); // BC
		theGraph.addEdge(1, 3); // BD
		theGraph.addEdge(1, 4); // BE
		theGraph.addEdge(2, 3); // CD
		theGraph.addEdge(2, 4); // CE
		theGraph.addEdge(3, 4); // DE

		System.out.print("Minimum spanning tree: ");
		theGraph.mst(); // minimum spanning tree
		System.out.println();
	} // end main()
} // end class MSTApp

class StackX3 {
	private final int SIZE = 20;
	private final int[] st;
	private int top;

	// -------------------------------------------------------------
	public StackX3() // constructor
	{
		st = new int[SIZE]; // make array
		top = -1;
	}

	// -------------------------------------------------------------
	public boolean isEmpty() // true if nothing on stack
	{
		return (top == -1);
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public int peek() // peek at top of stack
	{
		return st[top];
	}

	// -------------------------------------------------------------
	public int pop() // take item off stack
	{
		return st[top--];
	}

	// -------------------------------------------------------------
	public void push(final int j) // put item on stack
	{
		st[++top] = j;
	}
} // end class StackX
	////////////////////////////////////////////////////////////////

class Vertex3 {
	public char label; // label (e.g. 'A')
	public boolean wasVisited;

	// -------------------------------------------------------------
	public Vertex3(final char lab) // constructor
	{
		label = lab;
		wasVisited = false;
	}
	// -------------------------------------------------------------
} // end class Vertex
