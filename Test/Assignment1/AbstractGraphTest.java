package Assignment1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AbstractGraphTest {
	
	AbstractGraph<Integer>.Tree abstractGraph;

	Graph<Integer> graph; 
	Graph<Integer> emptyGraph; 
	Graph<Integer> cyclicGraph;
	Graph<Integer> notCyclicGraph;

	@Before
	public void setUp() {	
		Integer[] verticesEmpty = {};
		int[][] edgesEmpty = {};

		Integer[] verticesCycle = {0,1,2};
		int[][] edgesCycle = {
				{0, 1},{0, 2},
				{1, 0}, {1,2},
				{2, 0}, {2,1}
		};

		Integer[] vertices = {0, 1, 2, 3, 4};
		int[][] edges = {
				{0, 1}, {0, 2}, {0, 3},
				{1, 0}, {1, 2}, {1, 4},
				{2, 0}, {2, 1}, {2, 3},
				{3, 0}, {3, 2},
				{4, 1}
		};

		Integer[] verticesNotCyclic = {0,1,2};
		int [][] edgesNotCyclic = {
				{0, 1},{0, 2},
				{1, 0}, 
				{2, 0}
		}; 

		graph  = new UnweightedGraph<>(vertices, edges); 
		emptyGraph  = new UnweightedGraph<>(verticesEmpty, edgesEmpty); 
		cyclicGraph = new UnweightedGraph<>(verticesCycle, edgesCycle);
		notCyclicGraph = new UnweightedGraph<>(verticesNotCyclic, edgesNotCyclic);

		abstractGraph = graph.dfs(0);
	}
	@Test 
	public void dfs_emptyGraph(){
		assertEquals(0, emptyGraph.getSize());
	}
	@Test
	public void dfs_getNumberOfVertices_5(){
		assertEquals(5, abstractGraph.getNumberOfVerticesFound());
	}
	@Test
	public void dfs_getSize(){
		assertEquals(5, graph.getSize());
	}
	@Test 
	public void getRoot(){
		assertEquals(0, abstractGraph.getRoot()); 
	}
	@Test
	public void dfs_getParent(){
		assertEquals(-1, abstractGraph.getParent(0));  
		assertEquals(0, abstractGraph.getParent(1));	
		assertEquals(1, abstractGraph.getParent(2));
		assertEquals(2, abstractGraph.getParent(3));
		assertEquals(1, abstractGraph.getParent(4));
	}
	@Test 
	public void getPath_graph(){
		assertEquals("[0, 1]", graph.getPath(0, 1).toString()); 
		assertEquals("[0, 2]", graph.getPath(0, 2).toString());
		assertEquals("[0, 3]", graph.getPath(0, 3).toString());
		assertEquals("[0, 1, 4]", graph.getPath(0, 4).toString());
	}
	@Test
	public void isCyclic_assertTrue(){
		assertTrue(cyclicGraph.isCyclic());
	}
	@Test 
	public void isCyclic_assertFalse(){
		assertFalse(notCyclicGraph.isCyclic()); 
	}
	@Test
	public void isConnected_assertTrue(){
		assertTrue(graph.isConnected());
	}
	@Test
	public void isConnected_assertFalse(){
		graph.addVertex(5);
		assertFalse(graph.isConnected());
	}
}