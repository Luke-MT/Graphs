package upo.graph_undirected;

import java.util.HashMap;
import java.util.NoSuchElementException;

import upo.graph.base.VisitForest;
import upo.graph.base.WeightedGraph;

public class AdjListUndirWeight extends AdjListUndir implements WeightedGraph{
	
	public AdjListUndirWeight() {
		super();
	}
	
	public static void main(String[] args) {

	}
	
	@Override
	public double getEdgeWeight(String arg0, String arg1) throws IllegalArgumentException, NoSuchElementException {
		if(!super.containsVertex(arg0) || !super.containsVertex(arg0))
			throw new IllegalArgumentException();
		if(!super.containsEdge(arg0, arg1))
			throw new NoSuchElementException();
		
		return super.edgeList.get(super.getVertexIndex(arg0)).get(super.getVertexIndex(arg1));
	}
	
	@Override
	public void setEdgeWeight(String arg0, String arg1, double arg2) throws IllegalArgumentException, NoSuchElementException {
		if(!super.containsVertex(arg0) || !super.containsVertex(arg0))
			throw new IllegalArgumentException();
		if(!super.containsEdge(arg0, arg1))
			throw new NoSuchElementException();
		
		super.edgeList.get(super.getVertexIndex(arg0)).replace(super.getVertexIndex(arg1), arg2);
		super.edgeList.get(super.getVertexIndex(arg1)).replace(super.getVertexIndex(arg0), arg2);
		
	}
	
	@Override
	public String toString() {
		String graph = "";
		for(int i = 0; i<this.size(); i++) {
			graph += this.getVertexLabel(i)+"\t-> ";
			for(String e : this.getAdjacent(this.getVertexLabel(i)))
				graph+=e+" "+this.getEdgeWeight(e, this.getVertexLabel(i))+", ";
			graph+="\n";
		}			
		return graph+"\n";
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AdjListUndirWeight))
			return false;
		return true;
	}

	@Override
	public WeightedGraph getBellmanFordShortestPaths(String arg0)
			throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException("Da NON implementare");
	}

	@Override
	public WeightedGraph getDijkstraShortestPaths(String arg0)
			throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException("Da NON implementare");
	}	

	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Da NON implementare");
	}

	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Da NON implementare");
	}

	@Override
	public WeightedGraph getPrimMST(String arg0) throws UnsupportedOperationException, IllegalArgumentException {
		if(!this.containsVertex(arg0))
			throw new IllegalArgumentException();
		HashMap<String, Boolean> def = new HashMap<>();
		HashMap<String, Double> priorQ = new HashMap<>();
		
		VisitForest f = new VisitForest(this, VisitForest.VisitType.BFS);
		f.setDistance(arg0, 0);
		priorQ.put(arg0, 0.0);
		
		
		for(String v : this.adjVertices.keySet()) {
			//priorQ.put(v,f.getDistance(v));
			def.put(v, false);
		}
		while(!priorQ.isEmpty()) {
			Double mn = -1.0;
			String u = null;
			for(String i : priorQ.keySet()) 
				if(mn == -1.0 || mn > priorQ.get(i)) {
					u = i;
					mn = priorQ.get(i);
				}
			priorQ.remove(u);
			
			
			//add u all'albero
			def.put(u, true);//fare lista per def, non map
			
			for(String w : this.getAdjacent(u)) {
				if(def.get(w) == false) {
					if(priorQ.keySet().contains(w))
						if(priorQ.get(w) <= this.getEdgeWeight(u, w))
							continue;
					
					f.setParent(w, u);
					f.setDistance(w, this.getEdgeWeight(u, w));
					//decrease key
					priorQ.put(w, this.getEdgeWeight(u, w));
				}
			}				
		}
		
		
		AdjListUndirWeight mst = new AdjListUndirWeight();
		for(String n : this.adjVertices.keySet()) 
			mst.addVertex(n);
		
		for(String n : this.adjVertices.keySet()) 
			if(n != arg0) {
				mst.addEdge(n, f.getPartent(n));
				mst.setEdgeWeight(n, f.getPartent(n), f.getDistance(n));
			}
		
		return mst;
	}

	

}
