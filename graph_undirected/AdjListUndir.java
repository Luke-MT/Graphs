package upo.graph20038688;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class AdjListUndir implements Graph {
	
	protected Map<String, Integer> adjVertices;
	protected List<Map<Integer, Double>> edgeList;
	
	protected VisitForest forest;
	protected int time;
	
	public AdjListUndir() {
		super();
		this.adjVertices = new HashMap<>();
		this.edgeList = new ArrayList<>();	
		this.forest = null;
		this.time = 0;
	}
	
	public static void main(String[] args) {
		
	}
	 

	@Override
	public String toString() {
		String graph = "";
		for(int i = 0; i<this.size(); i++) {
			graph += this.getVertexLabel(i)+"\t-> ";
			for(String e : this.getAdjacent(this.getVertexLabel(i)))
				graph+=e+", ";
			graph+="\n";
		}			
		return graph+"\n";
	}


	@Override
	public void addEdge(String arg0, String arg1) throws IllegalArgumentException {
		if (this.containsVertex(arg0) && this.containsVertex(arg1)) {
			int indexArg0 = this.getVertexIndex(arg0);
			int indexArg1 = this.getVertexIndex(arg1);
			this.edgeList.get(indexArg0).putIfAbsent(indexArg1, 0.0);
			if(indexArg0 != indexArg1)
				this.edgeList.get(indexArg1).putIfAbsent(indexArg0, 0.0);
		} 
		else
			throw new IllegalArgumentException();

	}

	@Override
	public int addVertex(String arg0) {
		this.adjVertices.putIfAbsent(arg0, this.size());
		this.edgeList.add(new HashMap<>());
		return this.size();		
	}

	@Override
	public Set<Set<String>> connectedComponents() throws UnsupportedOperationException {
		Set<Set<String>> connectedComps = new HashSet<>();	
		VisitForest forest = this.initializeForest(new VisitForest(this,VisitForest.VisitType.BFS));
		
		for(String i : this.adjVertices.keySet())
			if(forest.getColor(i) == VisitForest.Color.WHITE) {
				forest = getBFSTree(i);
				Set<String> tmp = new HashSet<>();				
				for(String j : this.adjVertices.keySet())
					if(forest.getColor(j) == VisitForest.Color.BLACK)
						tmp.add(j);
				connectedComps.add(tmp);
			}
		return connectedComps;
	}

	@Override
	public boolean containsEdge(String arg0, String arg1) throws IllegalArgumentException {
		if (this.containsVertex(arg0) && this.containsVertex(arg1)) {
			int indexArg0 = this.getVertexIndex(arg0);
			int indexArg1 = this.getVertexIndex(arg1);
			boolean found = false;
			
			for(Integer e : this.edgeList.get(indexArg0).keySet()) {
				if(e == indexArg1 || e == indexArg0)
					found = true;
			}
			return found;
		}
		else
			throw new IllegalArgumentException();
	}

	@Override
	public boolean containsVertex(String arg0) {
		return this.adjVertices.keySet().contains(arg0);
	}

	@Override
	public Set<String> getAdjacent(String arg0) throws NoSuchElementException {
		if (this.containsVertex(arg0)) {
			Set<String> adjSet = new HashSet<String>(); 
			for(Integer e : this.edgeList.get(this.getVertexIndex(arg0)).keySet())
				adjSet.add(this.getVertexLabel(e));			
			
			return adjSet;
		}
		else
			throw new NoSuchElementException();

	}
	
	protected VisitForest initializeForest(VisitForest f) {
		for(String i : this.adjVertices.keySet()) {
			f.setColor(i, VisitForest.Color.WHITE);
		}
		return f;			
	}
	

	@Override
	public VisitForest getBFSTree(String arg0) throws UnsupportedOperationException, IllegalArgumentException {
		if(!containsVertex(arg0))
			throw new IllegalArgumentException("Il vertice " + arg0 + " non appartiene al grafo!");
		
		Queue<String> queue = new LinkedList<>();
		this.forest = this.initializeForest(new VisitForest(this, VisitForest.VisitType.BFS));
		this.forest.setColor(arg0, VisitForest.Color.GRAY);
		this.forest.setDistance(arg0, 0);
		queue.add(arg0);
		
		while(!queue.isEmpty()) {
			String u = queue.poll();
			for(String v : this.getAdjacent(u)) {
				if(this.forest.getColor(v) == VisitForest.Color.WHITE) {
					this.forest.setColor(v, VisitForest.Color.GRAY);
					this.forest.setParent(v, u);
					this.forest.setDistance(v, this.forest.getDistance(u)+1);
					queue.add(v);
				}
			}
			this.forest.setColor(u, VisitForest.Color.BLACK);		
		}
		return this.forest;
	}

	@Override
	public VisitForest getDFSTOTForest(String arg0) throws UnsupportedOperationException, IllegalArgumentException {
		if(!containsVertex(arg0))
			throw new IllegalArgumentException("Il vertice " + arg0 + " non appartiene al grafo!");	
		
		this.forest = this.initializeForest(new VisitForest(this,VisitForest.VisitType.DFS_TOT));
		time = 0;
		
		for(String v : this.adjVertices.keySet()) 
			if(this.forest.getColor(v) == VisitForest.Color.WHITE)
				this.ricGetDFSTree(v);
		
		return this.forest;
	}
	
	@Override
	public VisitForest getDFSTOTForest(String[] arg0) throws UnsupportedOperationException, IllegalArgumentException {
		for(String v : arg0)
			if(!containsVertex(v))
				throw new IllegalArgumentException("Il vertice " + v + " non appartiene al grafo!");	
		
		this.forest = this.initializeForest(new VisitForest(this,VisitForest.VisitType.DFS_TOT));
		this.time = 0;
		
		for(String v : arg0) 
			if(this.forest.getColor(v) == VisitForest.Color.WHITE)
				this.ricGetDFSTree(v);
		
		return this.forest;
	}

	@Override
	public VisitForest getDFSTree(String arg0) throws UnsupportedOperationException, IllegalArgumentException {
		if(!containsVertex(arg0))
			throw new IllegalArgumentException("Il vertice " + arg0 + " non appartiene al grafo!");
		
		this.forest = this.initializeForest(new VisitForest(this,VisitForest.VisitType.DFS)); 		
		this.time = 0;
		
		this.ricGetDFSTree(arg0);
		
		return this.forest;
	}
	
	protected void ricGetDFSTree(String u) {
		this.forest.setColor(u, VisitForest.Color.GRAY);
		this.forest.setStartTime(u, time++);
		
		for(String v : this.getAdjacent(u)) 
			if(this.forest.getColor(v) == VisitForest.Color.WHITE) {
				this.forest.setParent(v, u);
				ricGetDFSTree(v);
			}
		
		this.forest.setColor(u, VisitForest.Color.BLACK);
		this.forest.setEndTime(u, time++);
	}

	@Override
	public int getVertexIndex(String arg0) {
		return this.adjVertices.get(arg0);
	}

	@Override
	public String getVertexLabel(int arg0) {
		for(String key : this.adjVertices.keySet()) {
			if(this.adjVertices.get(key) == arg0)
				return key;
		}
		return null;
		
	}

	@Override
	public boolean isAdjacent(String arg0, String arg1) throws IllegalArgumentException {
		if (this.containsVertex(arg0) && this.containsVertex(arg1)) {
			return this.containsEdge(arg0, arg1) && this.containsEdge(arg1, arg0);
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public boolean isCyclic() {
		this.forest = this.initializeForest(new VisitForest(this,VisitForest.VisitType.DFS_TOT));
		for(String i : this.adjVertices.keySet()) 
			if(this.forest.getColor(i) == VisitForest.Color.WHITE && ricIsCyclic(i, this.forest))
				return true;
		return false;
	}
	
	protected boolean ricIsCyclic(String u, VisitForest forest) {
		forest.setColor(u, VisitForest.Color.GRAY);
		for(String v : getAdjacent(u)) {
			if(forest.getColor(v) == VisitForest.Color.WHITE) {
				forest.setParent(v, u);
				if(ricIsCyclic(v, forest))
					return true;
			}
			else if(v != forest.getPartent(u)) 
				return true;
		}
		forest.setColor(u, VisitForest.Color.BLACK);
		return false;
	}

	@Override
	public boolean isDAG() {
		return false;
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	@Override
	public void removeEdge(String arg0, String arg1) throws IllegalArgumentException, NoSuchElementException {
		
		if (this.containsVertex(arg0) && this.containsVertex(arg1)) {
			
			if(this.containsEdge(arg0, arg1) == false || this.containsEdge(arg1, arg0) == false) 
				throw new NoSuchElementException();
			
			else {
				int indexArg0 = this.getVertexIndex(arg0);
				int indexArg1 = this.getVertexIndex(arg1);
	
				for(Integer e : this.edgeList.get(indexArg0).keySet()) {
					if(e == indexArg1) {
						this.edgeList.get(indexArg0).remove(e);	
						break;
					}					
				}
				
				for(Integer e : this.edgeList.get(indexArg1).keySet()) {
					if(e == indexArg0) {
						this.edgeList.get(indexArg1).remove(e);	
						break;
					}
				}
			}
		}
		else
			throw new IllegalArgumentException();

	}

	@Override
	public void removeVertex(String arg0) throws NoSuchElementException {

		if (this.containsVertex(arg0)) {
			int index = this.getVertexIndex(arg0);
			
			for(String v : this.adjVertices.keySet()) {
				if(this.containsEdge(arg0, v))				
					this.removeEdge(arg0, v);
				}
			
			this.edgeList.remove(index);				
			this.adjVertices.remove(arg0);
						
			for(String key : this.adjVertices.keySet()) {
				if(this.adjVertices.get(key) > index)
					this.adjVertices.replace(key, this.adjVertices.get(key)-1);
			}
			for(Map<Integer,Double> m : this.edgeList) {
				List<Integer> in = new ArrayList<>(m.keySet());
				for(int i = 0; i<in.size(); i++) {
					if(in.get(i) > index) 
						m.putIfAbsent(in.get(i)-1, m.remove(in.get(i)));
				}
			}
		} 
		else
			throw new NoSuchElementException();
	}

	@Override
	public int size() {
		return this.adjVertices.keySet().size();
	}

	@Override
	public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Il grafo non è orientato, stronglyConnectedComponents() non è supportato");

	}

	@Override
	public String[] topologicalSort() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Il grafo non è un DAG, topologicalSort() non è supportato");
	}


	@Override
	public int hashCode() {
		return Objects.hash(forest, edgeList, adjVertices, time);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AdjListUndir))
			return false;
		AdjListUndir other = (AdjListUndir) obj;
		return Objects.equals(forest, other.forest) && Objects.equals(edgeList, other.edgeList)
				&& Objects.equals(adjVertices, other.adjVertices) && time == other.time;
	}
	

}
