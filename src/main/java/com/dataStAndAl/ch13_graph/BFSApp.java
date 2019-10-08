package com.dataStAndAl.ch13_graph;

//bfs.java
// demonstrates breadth-first search
// to run this program: C>java BFSApp

class BFSApp {
	public static void main(final String[] args) {
		final Graph2 theGraph = new Graph2();
		theGraph.addVertex('A'); // 0 (start for bfs)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4

		theGraph.addEdge(0, 1); // AB
		theGraph.addEdge(1, 2); // BC
		theGraph.addEdge(0, 3); // AD
		theGraph.addEdge(3, 4); // DE

		System.out.print("Visits: ");
		theGraph.bfs(); // breadth-first search
		System.out.println();
	} // end main()
} // end class BFSApp

class Graph2 {
	private final int MAX_VERTS = 20;
	private final Vertex2 vertexList[]; // list of vertices
	private final int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private final Queue theQueue;

	// ------------------------------------------------------------
	public Graph2() // constructor
	{
		vertexList = new Vertex2[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int j = 0; j < MAX_VERTS; j++) {
			for (int k = 0; k < MAX_VERTS; k++) {
				adjMat[j][k] = 0;
			}
		}
		theQueue = new Queue();
	} // end constructor

	// -------------------------------------------------------------
	public void addEdge(final int start, final int end) {
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}

	// -------------------------------------------------------------
	public void addVertex(final char lab) {
		vertexList[nVerts++] = new Vertex2(lab);
	}

	// -------------------------------------------------------------
	public void bfs() // breadth-first search
	{ // begin at vertex 0
		vertexList[0].wasVisited = true; // mark it
		displayVertex(0); // display it
		theQueue.insert(0); // insert at tail
		int v2;

		// 外层循环等待队列为空，而内层循环依次寻找当前顶点的未访问邻接点
		while (!theQueue.isEmpty()) // until queue empty,
		{
			final int v1 = theQueue.remove(); // remove vertex at head
			// until it has no unvisited neighbors
			while ((v2 = getAdjUnvisitedVertex(v1)) != -1) { // get one,
				vertexList[v2].wasVisited = true; // mark it
				displayVertex(v2); // display it
				theQueue.insert(v2); // insert it
			} // end while
		} // end while(queue not empty)

		// queue is empty, so we're done
		for (int j = 0; j < nVerts; j++) {
			vertexList[j].wasVisited = false;
		}
	} // end bfs()

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
	} // end getAdjUnvisitedVertex()
		// -------------------------------------------------------------
} // end class Graph

class Queue {
	private final int SIZE = 20;
	private final int[] queArray;
	private int front;
	private int rear;

	// -------------------------------------------------------------
	public Queue() // constructor
	{
		queArray = new int[SIZE];
		front = 0;
		rear = -1;
	}

	// -------------------------------------------------------------
	public void insert(final int j) // put item at rear of queue
	{
		if (rear == SIZE - 1) {
			rear = -1;
		}
		queArray[++rear] = j;
	}

	// -------------------------------------------------------------
	public boolean isEmpty() // true if queue is empty
	{
		return (rear + 1 == front || (front + SIZE - 1 == rear));
	}

	// -------------------------------------------------------------
	// -------------------------------------------------------------
	public int remove() // take item from front of queue
	{
		final int temp = queArray[front++];
		if (front == SIZE) {
			front = 0;
		}
		return temp;
	}
} // end class Queue

class Vertex2 {
	public char label; // label (e.g. 'A')
	public boolean wasVisited;

	// -------------------------------------------------------------
	public Vertex2(final char lab) // constructor
	{
		label = lab;
		wasVisited = false;
	}
	// -------------------------------------------------------------
} // end class Vertex
