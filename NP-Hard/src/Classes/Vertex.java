package Classes;

import java.util.LinkedList;

public class Vertex implements Comparable<Vertex>{
	
	public int value;
	
	public int degree;
	public int color;
	
	public LinkedList<Vertex> adjacencyList;
	
	
	public Vertex(int value) {
		this.value = value;
		this.degree = 0;
		this.color = -1;

		this.adjacencyList = new LinkedList<Vertex>();
	}
	
	public void addEdge(Vertex v) {
		
		if(this.adjacencyList.contains(v))
			return;
		
		this.adjacencyList.addLast(v);
		this.degree++;
		
	}
	
	public void removeEdge(Vertex v) {
		for(Vertex vertex : this.adjacencyList) {
			if(vertex.equals(v)) {
				this.adjacencyList.remove(v);
				this.degree--;
			}

		}

	}
	
	public boolean isAdjacentTo (Vertex v) {
		
		for(Vertex vertex : this.adjacencyList) {
			if(vertex.value == v.value)
				return true;
		}
		return false;
	}
	
	
	public void printAdjacencyList() {
		if(this.adjacencyList.isEmpty())
			return;
		for(Vertex v : this.adjacencyList)
			System.out.print(v + " ");
	}
	
	
	//It will make possible sort an array of vertices with respect to their degree
	public int compareTo(Vertex other) {
		return this.degree - other.degree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	//Vertices will be equal according to the value they store
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
	
	
}
