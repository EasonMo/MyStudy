package com.dataStAndAl.ch14_graphw;

// mstw.java
// demonstrates minimum spanning tree with weighted graphs
// to run this program: C>java MSTWApp
////////////////////////////////////////////////////////////////
class Edge {
	public int srcVert; // index of a vertex starting edge
	public int destVert; // index of a vertex ending edge
	public int distance; // distance from src to dest
	// -------------------------------------------------------------

	public Edge(final int sv, final int dv, final int d) // constructor
	{
		srcVert = sv;
		destVert = dv;
		distance = d;
	}
	// -------------------------------------------------------------
} // end class Edge
	////////////////////////////////////////////////////////////////

class Graph {
	private final int MAX_VERTS = 20;
	private final int INFINITY = 1000000;
	private final Vertex vertexList[]; // list of vertices
	private final int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private int currentVert;
	private final PriorityQ thePQ;
	private int nTree; // number of verts in tree
	// -------------------------------------------------------------

	public Graph() // constructor
	{
		vertexList = new Vertex[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int j = 0; j < MAX_VERTS; j++) {
			for (int k = 0; k < MAX_VERTS; k++) {
				adjMat[j][k] = INFINITY;
			}
		}
		thePQ = new PriorityQ();
	} // end constructor
		// -------------------------------------------------------------

	public void addEdge(final int start, final int end, final int weight) {
		adjMat[start][end] = weight;
		adjMat[end][start] = weight;
	}

	// -------------------------------------------------------------
	public void addVertex(final char lab) {
		vertexList[nVerts++] = new Vertex(lab);
	}

	// -------------------------------------------------------------
	public void displayVertex(final int v) {
		System.out.print(vertexList[v].label);
	}

	// -------------------------------------------------------------
	public void mstw() // minimum spanning tree
	{
		currentVert = 0; // start at 0

		while (nTree < nVerts - 1) // while not all verts in tree
		{ // put currentVert in tree
			vertexList[currentVert].isInTree = true;
			nTree++;

			// insert edges adjacent to currentVert into PQ
			for (int j = 0; j < nVerts; j++) // for each vertex,
			{
				if (j == currentVert) {
					continue;
				}
				if (vertexList[j].isInTree) {
					continue;
				}
				final int distance = adjMat[currentVert][j];
				if (distance == INFINITY) {
					continue;
				}
				putInPQ(j, distance); // put it in PQ (maybe)
			}
			if (thePQ.size() == 0) // no vertices in PQ?
			{
				System.out.println(" GRAPH NOT CONNECTED");
				return;
			}
			// remove edge with minimum distance, from PQ
			final Edge theEdge = thePQ.removeMin();
			final int sourceVert = theEdge.srcVert;
			currentVert = theEdge.destVert;

			// display edge from source to current
			System.out.print(vertexList[sourceVert].label);
			System.out.print(vertexList[currentVert].label);
			System.out.print(" ");
		} // end while(not all verts in tree)

		// mst is complete
		for (int j = 0; j < nVerts; j++) {
			vertexList[j].isInTree = false;
		}
	} // end mstw
		// -------------------------------------------------------------

	public void putInPQ(final int newVert, final int newDist) {
		// is there another edge with the same destination vertex?
		final int queueIndex = thePQ.find(newVert);// newVert是目标顶点
		if (queueIndex != -1) // got edge's index
		{
			final Edge tempEdge = thePQ.peekN(queueIndex); // get edge
			final int oldDist = tempEdge.distance;
			if (oldDist > newDist) // if new edge shorter,
			{
				thePQ.removeN(queueIndex); // remove old edge
				final Edge theEdge = new Edge(currentVert, newVert, newDist);
				thePQ.insert(theEdge); // insert new edge
			}
			// else no action; just leave the old vertex there
		} // end if
		else // no edge with same destination vertex
		{ // so insert new one
			final Edge theEdge = new Edge(currentVert, newVert, newDist);
			thePQ.insert(theEdge);
		}
	} // end putInPQ()
		// -------------------------------------------------------------
} // end class Graph
	////////////////////////////////////////////////////////////////

class MSTWApp {
	public static void main(final String[] args) {
		final Graph theGraph = new Graph();
		theGraph.addVertex('A'); // 0 (start for mst)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4
		theGraph.addVertex('F'); // 5

		theGraph.addEdge(0, 1, 6); // AB 6
		theGraph.addEdge(0, 3, 4); // AD 4
		theGraph.addEdge(1, 2, 10); // BC 10
		theGraph.addEdge(1, 3, 7); // BD 7
		theGraph.addEdge(1, 4, 7); // BE 7
		theGraph.addEdge(2, 3, 8); // CD 8
		theGraph.addEdge(2, 4, 5); // CE 5
		theGraph.addEdge(2, 5, 6); // CF 6
		theGraph.addEdge(3, 4, 12); // DE 12
		theGraph.addEdge(4, 5, 7); // EF 7

		System.out.print("Minimum spanning tree: ");
		theGraph.mstw(); // minimum spanning tree
		System.out.println();
	} // end main()
} // end class MSTWApp
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

class PriorityQ {
	// array in sorted order, from max at 0 to min at size-1
	private final int SIZE = 20;
	private final Edge[] queArray;
	private int size;

	// -------------------------------------------------------------
	public PriorityQ() // constructor
	{
		queArray = new Edge[SIZE];
		size = 0;
	}

	// -------------------------------------------------------------
	public int find(final int findDex) // find item with specified
	{ // destVert value
		for (int j = 0; j < size; j++) {
			if (queArray[j].destVert == findDex) {
				return j;
			}
		}
		return -1;
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public void insert(final Edge item) // insert item in sorted order
	{
		int j;

		for (j = 0; j < size; j++) {
			if (item.distance >= queArray[j].distance) {
				break;
			}
		}

		for (int k = size - 1; k >= j; k--) {
			queArray[k + 1] = queArray[k];
		}

		queArray[j] = item; // insert item
		size++;
	}

	// -------------------------------------------------------------
	public boolean isEmpty() // true if queue is empty
	{
		return (size == 0);
	}

	// -------------------------------------------------------------
	public Edge peekMin() // peek at minimum item
	{
		return queArray[size - 1];
	}

	// -------------------------------------------------------------
	public Edge peekN(final int n) // peek at item n
	{
		return queArray[n];
	}

	// -------------------------------------------------------------
	public Edge removeMin() // remove minimum item
	{
		return queArray[--size];
	}

	// -------------------------------------------------------------
	public void removeN(final int n) // remove item at n
	{
		for (int j = n; j < size - 1; j++) {
			queArray[j] = queArray[j + 1];
		}
		size--;
	}

	// -------------------------------------------------------------
	public int size() // return number of items
	{
		return size;
	}
} // end class PriorityQ
	////////////////////////////////////////////////////////////////

class Vertex {
	public char label; // label (e.g. 'A')
	public boolean isInTree;

	// -------------------------------------------------------------
	public Vertex(final char lab) // constructor
	{
		label = lab;
		isInTree = false;
	}
	// -------------------------------------------------------------
} // end class Vertex
