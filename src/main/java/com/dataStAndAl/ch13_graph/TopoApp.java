package com.dataStAndAl.ch13_graph;

////////////////////////////////////////////////////////////////
class Graph4 {
	private final int MAX_VERTS = 20;
	private final Vertex4 vertexList[]; // list of vertices
	private final int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private final char sortedArray[];

	// -------------------------------------------------------------
	public Graph4() // constructor
	{
		vertexList = new Vertex4[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int j = 0; j < MAX_VERTS; j++) {
			for (int k = 0; k < MAX_VERTS; k++) {
				adjMat[j][k] = 0;
			}
		}
		sortedArray = new char[MAX_VERTS]; // sorted vert labels
	} // end constructor
		// -------------------------------------------------------------

	public void addEdge(final int start, final int end) {
		adjMat[start][end] = 1;
	}

	// -------------------------------------------------------------
	public void addVertex(final char lab) {
		vertexList[nVerts++] = new Vertex4(lab);
	}

	// -------------------------------------------------------------
	public void deleteVertex(final int delVert) {
		if (delVert != nVerts - 1) // if not last vertex,
		{ // delete from vertexList
			for (int j = delVert; j < nVerts - 1; j++) {
				vertexList[j] = vertexList[j + 1];
			}
			// delete row from adjMat
			for (int row = delVert; row < nVerts - 1; row++) {
				moveRowUp(row, nVerts);
			}
			// delete col from adjMat
			for (int col = delVert; col < nVerts - 1; col++) {
				moveColLeft(col, nVerts - 1);
			}
		}
		nVerts--; // one less vertex
	} // end deleteVertex
		// -------------------------------------------------------------

	public void displayVertex(final int v) {
		System.out.print(vertexList[v].label);
	}

	// -------------------------------------------------------------
	public int noSuccessors() // returns vert with no successors
	{ // (or -1 if no such verts)
		boolean isEdge; // edge from row to column in adjMat

		for (int row = 0; row < nVerts; row++) // for each vertex,
		{
			isEdge = false; // check edges
			for (int col = 0; col < nVerts; col++) {
				// 横坐标有值，说明有边
				if (adjMat[row][col] > 0) // if edge to
				{ // another,
					isEdge = true;
					break; // this vertex
				} // has a successor
			} // try another
			if (!isEdge) {
				return row; // has no successors
			}
		}
		return -1; // no such vertex
	} // end noSuccessors()
		// -------------------------------------------------------------

	public void topo() // toplogical sort
	{
		final int orig_nVerts = nVerts; // remember how many verts

		while (nVerts > 0) // while vertices remain,
		{
			// get a vertex with no successors, or -1
			final int currentVertex = noSuccessors();
			if (currentVertex == -1) // must be a cycle
			{
				System.out.println("ERROR: Graph has cycles");
				return;
			}
			// insert vertex label in sorted array (start at end)
			sortedArray[nVerts - 1] = vertexList[currentVertex].label;

			deleteVertex(currentVertex); // delete vertex
		} // end while

		// vertices all gone; display sortedArray
		System.out.print("Topologically sorted order: ");
		for (int j = 0; j < orig_nVerts; j++) {
			System.out.print(sortedArray[j]);
		}
		System.out.println("");
	} // end topo
		// -------------------------------------------------------------

	private void moveColLeft(final int col, final int length) {
		for (int row = 0; row < length; row++) {
			adjMat[row][col] = adjMat[row][col + 1];
		}
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	private void moveRowUp(final int row, final int length) {
		for (int col = 0; col < length; col++) {
			adjMat[row][col] = adjMat[row + 1][col];
		}
	}
} // end class Graph
	////////////////////////////////////////////////////////////////

class TopoApp {
	public static void main(final String[] args) {
		final Graph4 theGraph = new Graph4();
		theGraph.addVertex('A'); // 0
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4
		theGraph.addVertex('F'); // 5
		theGraph.addVertex('G'); // 6
		theGraph.addVertex('H'); // 7

		theGraph.addEdge(0, 3); // AD
		theGraph.addEdge(0, 4); // AE
		theGraph.addEdge(1, 4); // BE
		theGraph.addEdge(2, 5); // CF
		theGraph.addEdge(3, 6); // DG
		theGraph.addEdge(4, 6); // EG
		theGraph.addEdge(5, 7); // FH
		theGraph.addEdge(6, 7); // GH

		theGraph.topo(); // do the sort
	} // end main()
} // end class TopoApp
	////////////////////////////////////////////////////////////////
	// topo.java
	// demonstrates topological sorting
	// to run this program: C>java TopoApp
	////////////////////////////////////////////////////////////////

class Vertex4 {
	public char label; // label (e.g. 'A')
	// -------------------------------------------------------------

	public Vertex4(final char lab) // constructor
	{
		label = lab;
	}
} // end class Vertex
