package Assignment1;

import java.util.*;

/*pseducode for dfs-method: 
Declare stack, searchOrder and isVisited (set to true). 
	loop thru list of parents and set all to minus one. (help-method) 
	push current vertex, set it to visited, and add vertex to searchOrder. 
	while the stack is not empty
	set the value to neighbor thru a help-method. 
		help-method: 
			loop thru list of neighbors, and checks if neighbor is visited. 
			if not, returning neighbor. 
			else returning -1 if all is visited. 
	if help-method returns minus one, pop on stack. 
	else 
		set neighbor to visited, add it to searchOrder and push. 
	return a new tree with vertex, parent and searchOrder. */

public abstract class AbstractGraph<V> implements Graph<V> {
	protected List<V> vertices = new ArrayList<>(); 
	protected List<List<Edge>> neighbors = new ArrayList<>(); 

	protected AbstractGraph() {
	}

	protected AbstractGraph(V[] vertices, int[][] edges) {
		for (int i = 0; i < vertices.length; i++)
			addVertex(vertices[i]);

		createAdjacencyLists(edges, vertices.length);
	}

	protected AbstractGraph(List<V> vertices, List<Edge> edges) {
		for (int i = 0; i < vertices.size(); i++)
			addVertex(vertices.get(i));

		createAdjacencyLists(edges, vertices.size());
	}

	protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
		for (int i = 0; i < numberOfVertices; i++)
			addVertex((V)(new Integer(i))); 

		createAdjacencyLists(edges, numberOfVertices);
	}

	protected AbstractGraph(int[][] edges, int numberOfVertices) {
		for (int i = 0; i < numberOfVertices; i++)
			addVertex((V)(new Integer(i))); 

		createAdjacencyLists(edges, numberOfVertices);
	}

	private void createAdjacencyLists(
			int[][] edges, int numberOfVertices) {
		for (int i = 0; i < edges.length; i++) {
			addEdge(edges[i][0], edges[i][1]);
		}
	}

	private void createAdjacencyLists(
			List<Edge> edges, int numberOfVertices) {
		for (Edge edge: edges) {
			addEdge(edge.u, edge.v);
		}
	}

	@Override 
	public int getSize() {
		return vertices.size();
	}

	@Override 
	public List<V> getVertices() {
		return vertices;
	}

	@Override 
	public V getVertex(int index) {
		return vertices.get(index);
	}

	@Override 
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}

	@Override 
	public List<Integer> getNeighbors(int index) {
		List<Integer> result = new ArrayList<>();
		for (Edge e: neighbors.get(index))
			result.add(e.v);

		return result;
	}

	@Override 
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}

	@Override 
	public void printEdges() {
		for (int u = 0; u < neighbors.size(); u++) {
			System.out.print(getVertex(u) + " (" + u + "): ");
			for (Edge e: neighbors.get(u)) {
				System.out.print("(" + getVertex(e.u) + ", " +
						getVertex(e.v) + ") ");
			}
			System.out.println();
		}
	}

	@Override 
	public void clear() {
		vertices.clear();
		neighbors.clear();
	}

	@Override 
	public boolean addVertex(V vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			neighbors.add(new ArrayList<Edge>());
			return true;
		}
		else {
			return false;
		}
	}


	protected boolean addEdge(Edge e) {
		if (e.u < 0 || e.u > getSize() - 1)
			throw new IllegalArgumentException("No such index: " + e.u);

		if (e.v < 0 || e.v > getSize() - 1)
			throw new IllegalArgumentException("No such index: " + e.v);

		if (!neighbors.get(e.u).contains(e)) {
			neighbors.get(e.u).add(e);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean addEdge(int u, int v) {
		return addEdge(new Edge(u, v));
	}

	public static class Edge {
		public int u; 
		public int v; 

		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}   

		public boolean equals(Object o) {
			return u == ((Edge)o).u && v == ((Edge)o).v;
		}
	}


	@Override
	public Tree dfs(int vertex){
		List< Integer > searchOrder = new ArrayList<>();
		Deque<Integer> stack = new ArrayDeque<Integer>();
		int [] parent = new int[vertices.size()];
		boolean[] isVisited = new boolean [vertices.size()];

		setParentToMinusOne(parent);

		stack.push(vertex);
		isVisited[vertex] = true;
		searchOrder.add(vertex);

		while(!stack.isEmpty()){
			int neighbor = getNextUnvisitedVertex(stack.peek(), isVisited ,parent); 

			if(neighbor == -1)
				neighbor = stack.pop(); 

			else{
				isVisited[neighbor] = true;
				searchOrder.add(neighbor);
				stack.push(neighbor);
			}
		}
		return new Tree(vertex, parent, searchOrder);
	}

	private void setParentToMinusOne(int[] parent) {
		for(int i = 0; i < parent.length; i++)
			parent[i] = -1;
	}

	private int getNextUnvisitedVertex(int u, boolean isVisited[], int[] parent) {
		for (Integer neighbor : getNeighbors(u)) {
			if (isVisited[neighbor] == false) {
				parent[neighbor] = u;
				return neighbor;
			}
		}
		return -1;
	}

	@Override
	public  List<Integer> getPath(int u, int v){
		Tree tree = bfs( v );
		List<V> listVertex = tree.getPath(u);
		List<Integer> pathway = new ArrayList<>();

		for ( V vertices : listVertex ){
			int w = getIndex(vertices); 
			pathway.add(w);
		}   
		if(pathway.size() <= 1){
			return null;

		}else{

			return pathway; 
		}
	}

	@Override
	public boolean isCyclic(){
		int vertices = getSize(); 
		int edges = 0; 
		for (int i = 0; i < vertices; i++)
			edges = edges + getDegree(i);
		if((edges/2 >= vertices)){
			return true; 
		}
		else{
			return false; 
		}
	}
	@Override
	public boolean isConnected(){
		AbstractGraph<V>.Tree tree = bfs(0);
		boolean isConnected = (tree.getNumberOfVerticesFound() == vertices.size());
		return isConnected;
	}

	@Override
	public Tree bfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];

		setParentToMinusOne(parent); 

		java.util.LinkedList<Integer> queue = new java.util.LinkedList<>(); 
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v); 
		isVisited[v] = true; 

		while (!queue.isEmpty()) {
			int u = queue.poll(); 
			searchOrder.add(u);
			for (Edge e: neighbors.get(u)) {
				if (!isVisited[e.v]) {
					queue.offer(e.v); 
					parent[e.v] = u; 
					isVisited[e.v] = true;
				}
			}
		}
		return new Tree(v, parent, searchOrder);
	}

	public class Tree {
		private int root;
		private int[] parent;
		private List<Integer> searchOrder;

		public Tree(int root, int[] parent, List<Integer> searchOrder) {
			this.root = root;
			this.parent = parent;
			this.searchOrder = searchOrder;
		}

		public int getRoot() {
			return root;
		}

		public int getParent(int v) {
			return parent[v];
		}

		public List<Integer> getSearchOrder() {
			return searchOrder;
		}

		public int getNumberOfVerticesFound() {
			return searchOrder.size();
		}

		public List<V> getPath(int index) {
			ArrayList<V> path = new ArrayList<>();

			do {
				path.add(vertices.get(index));
				index = parent[index];
			}
			while (index != -1);

			return path;
		}

		public void printPath(int index) {
			List<V> path = getPath(index);
			System.out.print("A path from " + vertices.get(root) + " to " +
					vertices.get(index) + ": ");
			for (int i = path.size() - 1; i >= 0; i--)
				System.out.print(path.get(i) + " ");
		}

		/** Print the whole tree */
		public void printTree() {
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for (int i = 0; i < parent.length; i++) {
				if (parent[i] != -1) {
					// Display an edge
					System.out.print("(" + vertices.get(parent[i]) + ", " +
							vertices.get(i) + ") ");
				}
			}
			System.out.println();
		}
	}
}