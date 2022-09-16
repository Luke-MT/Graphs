package upo.graph20038688;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import upo.graph.base.VisitForest;

class AdjListUndirTest {
	
	private static AdjListUndir myGraph = new AdjListUndir();
	
	@BeforeAll
	@Test
	static void graphCreation() {
		String []nodi = {"a","b","c","d"};
		for(String n : nodi)
			myGraph.addVertex(n);
		Assertions.assertEquals(4, myGraph.size());
		Assertions.assertFalse(myGraph.containsVertex("g"));
		Assertions.assertDoesNotThrow(() -> myGraph.addEdge("a","b"));
		Assertions.assertDoesNotThrow(() -> myGraph.addEdge("b","c"));
		Assertions.assertDoesNotThrow(() -> myGraph.addEdge("a","d"));	
		Assertions.assertTrue(myGraph.containsEdge("b", "a"));
		Assertions.assertTrue(myGraph.containsEdge("a", "b"));
		Assertions.assertFalse(myGraph.containsEdge("b", "b"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> myGraph.containsEdge("g", "b"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> myGraph.addEdge("g", "b"));
		
	}
	
	@Test
	void rmEdgeTest() {
		Assertions.assertTrue(myGraph.containsVertex("c"));
		Assertions.assertTrue(myGraph.containsVertex("d"));
		myGraph.addEdge("c","d");
		Assertions.assertTrue(myGraph.containsEdge("d", "c"));
		Assertions.assertDoesNotThrow(() -> myGraph.removeEdge("c","d"));
		Assertions.assertFalse(myGraph.containsEdge("d", "c"));
		Assertions.assertFalse(myGraph.isAdjacent("d", "c"));
		Set<String> adjD = myGraph.getAdjacent("d");
		Assertions.assertFalse(adjD.contains("c"));
		Set<String> adjC = myGraph.getAdjacent("c");
		Assertions.assertFalse(adjC.contains("d"));
	}
	
	@Test
	void rmVertexTest() { 
		myGraph.addEdge("d","c");
		Assertions.assertTrue(myGraph.containsEdge("d", "c"));
		Assertions.assertDoesNotThrow(() -> myGraph.removeVertex("d"));
		Assertions.assertFalse(myGraph.containsVertex("d"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> myGraph.containsEdge("d", "e"));
		Set<String> adjTo1 = myGraph.getAdjacent("c");
		Assertions.assertFalse(adjTo1.contains("d"));
	}
	@Test
	void isCyclicTest() {
		AdjListUndir myGraph = new AdjListUndir();
		Assertions.assertFalse(myGraph.isCyclic());
		Assertions.assertEquals(0, myGraph.size());
		String []nodi = {"a","b","c","d"};
		for(String n : nodi)
			myGraph.addVertex(n);
		Assertions.assertFalse(myGraph.isCyclic()); 
		//aggiungo archi ma senza creare un ciclo
		myGraph.addEdge("a","b");
		myGraph.addEdge("c","d");
		myGraph.addEdge("b","c");
		Assertions.assertFalse(myGraph.isCyclic());
		//aggiungo l'arco per creare un ciclo
		myGraph.addEdge("a","d");
		Assertions.assertTrue(myGraph.isCyclic()); 		
	}
	@Test
	void getBFSTreeTest() {
		AdjListUndir myGraph = new AdjListUndir();
		VisitForest forest = null;
		String []nodi = {"a","b","c","d"};
		for(String n : nodi)
			myGraph.addVertex(n);
		myGraph.addVertex("e");
		
		myGraph.addEdge("a","b");
		myGraph.addEdge("b","c");
		myGraph.addEdge("a","d");
		
		forest = myGraph.getBFSTree("a");
		Assertions.assertEquals(VisitForest.VisitType.BFS, forest.visitType);
		for(String n : nodi)
			Assertions.assertEquals(forest.getColor(n), VisitForest.Color.BLACK);
		Assertions.assertEquals(forest.getColor("e"), VisitForest.Color.WHITE);;

		for(String n :  nodi) {
			switch(n) {	
				case "a":
					Assertions.assertEquals(forest.getDistance(n), 0);
					break;
				case "b":
					Assertions.assertEquals(forest.getPartent(n), "a"); 
					Assertions.assertEquals(forest.getDistance(n), 1);
					break;
				case "c":
					Assertions.assertEquals(forest.getPartent(n), "b"); 
					Assertions.assertEquals(forest.getDistance(n), 2);
					break;
				case "d":
					Assertions.assertEquals(forest.getPartent(n), "a"); 
					Assertions.assertEquals(forest.getDistance(n), 1);
					break;
				default:
					break;
			}
		}
	}
	@Test
	void getDFSTreeTest() {
		AdjListUndir myGraph = new AdjListUndir();
		VisitForest forest = null;
		String []nodi = {"a","b","c","d"};
		for(String n : nodi)
			myGraph.addVertex(n);
		myGraph.addVertex("e");
		
		myGraph.addEdge("a","b");
		myGraph.addEdge("b","c");
		myGraph.addEdge("a","d");
		
		forest = myGraph.getDFSTree("a");
		Assertions.assertEquals(VisitForest.VisitType.DFS, forest.visitType);
		for(String n : nodi)
			Assertions.assertEquals(forest.getColor(n), VisitForest.Color.BLACK);
		Assertions.assertEquals(forest.getColor("e"), VisitForest.Color.WHITE);;
		for(String n :  nodi) 
			switch(n) {
				case "a":
					Assertions.assertEquals(forest.getStartTime(n), 0); 
					Assertions.assertEquals(forest.getEndTime(n), 7); 
					break;
				case "b":
					Assertions.assertEquals(forest.getPartent(n), "a"); 
					Assertions.assertEquals(forest.getStartTime(n), 1);
					Assertions.assertEquals(forest.getEndTime(n), 4); 
					break;
				case "c":
					Assertions.assertEquals(forest.getPartent(n), "b"); 
					Assertions.assertEquals(forest.getStartTime(n), 2);
					Assertions.assertEquals(forest.getEndTime(n), 3); 
					break;
				case "d":
					Assertions.assertEquals(forest.getPartent(n), "a"); 
					Assertions.assertEquals(forest.getStartTime(n), 5);
					Assertions.assertEquals(forest.getEndTime(n), 6); 
					break;
				default:
					break;
			}	
	}
	@Test
	void getDFSTOTTest() {
		AdjListUndir myGraph = new AdjListUndir();
		VisitForest forest = null;
		String []nodi = {"a","b","c","d","e"};
		for(String n : nodi)
			myGraph.addVertex(n);
		
		myGraph.addEdge("a","b");
		myGraph.addEdge("b","c");
		myGraph.addEdge("a","d");
		
		forest = myGraph.getDFSTOTForest("a");
		Assertions.assertEquals(VisitForest.VisitType.DFS_TOT, forest.visitType);
		for(String n : nodi)
			Assertions.assertEquals(forest.getColor(n), VisitForest.Color.BLACK);
		Assertions.assertEquals(forest.getColor("e"), VisitForest.Color.BLACK);;
		
		for(String n :  nodi) 
			switch(n) {
				case "a":
					Assertions.assertEquals(forest.getStartTime(n), 0); 
					Assertions.assertEquals(forest.getEndTime(n), 7);
					break;
				case "b":
					Assertions.assertEquals(forest.getPartent(n), "a"); 
					Assertions.assertEquals(forest.getStartTime(n), 1);
					Assertions.assertEquals(forest.getEndTime(n), 4); 
					break;
				case "c":
					Assertions.assertEquals(forest.getPartent(n), "b"); 
					Assertions.assertEquals(forest.getStartTime(n), 2);
					Assertions.assertEquals(forest.getEndTime(n), 3); 
					break;
				case "d":
					Assertions.assertEquals(forest.getPartent(n), "a"); 
					Assertions.assertEquals(forest.getStartTime(n), 5);
					Assertions.assertEquals(forest.getEndTime(n), 6); 
					break;
				case "e":
					Assertions.assertEquals(forest.getStartTime(n), 8);
					Assertions.assertEquals(forest.getEndTime(n), 9);
					break;
				default:
					break;
			}	
	}
	
	@Test
	void connectedComponentsTest() {
		AdjListUndir myGraph = new AdjListUndir();
		String []nodi = {"a","b","c","d","e"};
		for(String n : nodi)
			myGraph.addVertex(n);
		
		Set<Set<String>> comps = myGraph.connectedComponents();
		Assertions.assertEquals(myGraph.size(), comps.size());
		
		myGraph.addEdge("a","b");
		myGraph.addEdge("b","c");
		myGraph.addEdge("a","d");
		
		/*
		 * (a,b,c,d)
		 * (e)
		 */
		comps = myGraph.connectedComponents();
		Assertions.assertEquals(2, comps.size());		
		
	}
	
	@Test
	void equalsTest() {
		AdjListUndir myGraph1 = new AdjListUndir();
		AdjListUndir myGraph2 = new AdjListUndir();
		Assertions.assertTrue(myGraph1.equals(myGraph2));
		
		myGraph1.addVertex("a");
		Assertions.assertFalse(myGraph1.equals(myGraph2));
		myGraph1.addVertex("b");		
		myGraph2.addVertex("a");
		myGraph2.addVertex("b");		
		Assertions.assertTrue(myGraph1.equals(myGraph2));
		
		myGraph1.addEdge("a", "b");
		Assertions.assertFalse(myGraph1.equals(myGraph2));
		myGraph2.addEdge("a", "b");
		Assertions.assertTrue(myGraph1.equals(myGraph2));
		
	}

}
