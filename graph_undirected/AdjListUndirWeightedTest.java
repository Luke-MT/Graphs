package upo.graph20038688;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjListUndirWeightedTest {
	private AdjListUndirWeight myGraph = new AdjListUndirWeight();
	
	@BeforeEach
	void setUp() throws Exception {
		String []nodi = {"a","b","c","d"};
		for(String n : nodi) 
			myGraph.addVertex(n);
		
		myGraph.addEdge("a", "b");
		myGraph.addEdge("a", "d");
		myGraph.addEdge("b", "c");
	}

	@Test
	void getEdgeWeightTest() {
		Assertions.assertThrows(NoSuchElementException.class,() -> myGraph.getEdgeWeight("a","c"));
		Assertions.assertThrows(IllegalArgumentException.class,() -> myGraph.getEdgeWeight("a","e"));
		
		Assertions.assertEquals(0, myGraph.getEdgeWeight("a", "b"));
	}
	@Test
	void setEdgeWeightTest() {
		Assertions.assertThrows(NoSuchElementException.class,() -> myGraph.setEdgeWeight("a","c",2));
		Assertions.assertThrows(IllegalArgumentException.class,() -> myGraph.setEdgeWeight("a","e",2));
		
		myGraph.setEdgeWeight("a", "b", 2);
		Assertions.assertEquals(2, myGraph.getEdgeWeight("a", "b"));		
	}
	@Test
	void GetPrimMstTest() {
		myGraph = new AdjListUndirWeight();
		String []nodi = {"a","b","c","d","e"};
		for(String n : nodi) 
			myGraph.addVertex(n);
		
		myGraph.addEdge("a", "b");
		myGraph.setEdgeWeight("a", "b", 2.0);
		myGraph.addEdge("a", "c");
		myGraph.setEdgeWeight("a", "c", 4.0);
		myGraph.addEdge("b", "d");
		myGraph.setEdgeWeight("b", "d", 3.0);
		myGraph.addEdge("c", "e");
		myGraph.setEdgeWeight("c", "e", 2.0);
		myGraph.addEdge("d", "e");
		myGraph.setEdgeWeight("d", "e", 2.0);
		
		AdjListUndirWeight mst = (AdjListUndirWeight) myGraph.getPrimMST("a");
		AdjListUndirWeight expected_mst = new AdjListUndirWeight();
		for(String n : nodi) 
			expected_mst.addVertex(n);
		
		expected_mst.addEdge("a", "b");
		expected_mst.setEdgeWeight("a", "b", 2.0);
		expected_mst.addEdge("b", "d");
		expected_mst.setEdgeWeight("b", "d", 3.0);
		expected_mst.addEdge("d", "e");
		expected_mst.setEdgeWeight("d", "e", 2.0);
		expected_mst.addEdge("e", "c");	
		expected_mst.setEdgeWeight("e", "c", 2.0);
		
		
		Assertions.assertEquals(mst, expected_mst);
		
	}

}
