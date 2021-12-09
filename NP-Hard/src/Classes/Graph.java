package Classes;

import java.util.ArrayList;


public class Graph {
	
	private ArrayList<Vertex> vertices;
	private int edgesNumber;
	
	
	public Graph() {
		this.vertices = new ArrayList<Vertex>();
		this.edgesNumber = 0;
	}
	
	
	
	public void setNumberEdges(int edgesNumber) {
		if(edgesNumber >= 0)
			this.edgesNumber = edgesNumber;
	}
	
	
	public int getEdgesNumber() {
		return this.edgesNumber;
	}
	
	
	public int getVerticesNumber() {
		if(this.vertices == null)
			return -1;
		return this.vertices.size();
	}
	
	
	
	
	public void addVertex(int u) {
		
		Vertex vertex = new Vertex(u);
		
		if(this.vertices.contains(vertex))
			return;
		
		this.vertices.add(vertex);
		
	}
	
	
	public void addEdge(int u, int v) {
		this.addEdge(new Vertex(u), new Vertex(v));
	}
	
	private void addEdge(Vertex u, Vertex v) {
		Vertex uReference = u;
		Vertex vReference = v;
		
		if(!this.vertices.contains(u))
			this.vertices.add(u);
		else {
			//get its reference
			for(Vertex vex : this.vertices) {
				if(vex.equals(u))
					uReference = vex;
			}

		}
		
		if(!this.vertices.contains(v))
			this.vertices.add(v);
		else {
			//get its reference
			for(Vertex vex : this.vertices) {
				if(vex.equals(v))
					vReference = vex;
			}

		}
		
		uReference.addEdge(vReference);
		vReference.addEdge(uReference);
	}
	
	
	
	//Edge (u,v)
	public void removeEdge(Vertex u, Vertex v) {
		
		u.removeEdge(v);
		v.removeEdge(u);
		
	}
	
	public boolean verifyColoring() {
		for(Vertex vertex : this.vertices) {
			
			for(Vertex neighbor : vertex.adjacencyList) {
				
				if(vertex.equals(neighbor))
					continue;
				if(vertex.color == neighbor.color) {
					System.out.println("Vertex: " + vertex.value + "\tColor: " + vertex.color);
					System.out.println("Neighbor: " + neighbor.value + "\tColor: " + neighbor.color);
					System.out.println();
					return false;
				}
					
			}
		}
		
		return true;

	}
	
	
	public void showEdges() {
		
		System.out.println();
		System.out.println("Number of vertices: " + this.vertices.size());
		int numberEdges = 0;
		
		for(Vertex u : this.vertices) {
			System.out.println("Vertex: " + u.value);
			
			for(Vertex v : u.adjacencyList) {
				System.out.println("\tAdj: " + v.value);
				numberEdges++;
			}
			
			System.out.println();
		}
		
		System.out.println("Number of edges computed: " + numberEdges);
		
	}
	
	
	
	
	
	//Heuristic greedy algorithm for the minimum graph coloring problem
		//Time complexity: O(n^2 * m)
		//Return: The chromatic number
	
	public int RLF() {
		int currentColor = 1;
		
		//ArrayList sorted in decreasing order according to each vertex degree
		this.vertices.sort(null);
		
		
		//Select vertex to color with a new color
		for(int i = 0; i < this.vertices.size(); i++) {
			
			Vertex u = this.vertices.get(i);
			
			if(u.color != -1) //Vertex has already been assigned a color
				continue;
			
			u.color=currentColor;
			
			//Now, color all the possible vertices
			for(int j = i+1; j < this.vertices.size(); j++) {
				Vertex v = this.vertices.get(j);
				
				//O vertice v ja foi colorido ou eh adjacente ao vertice u
				if(v.color != -1 || u.isAdjacentTo(v)) 
					continue;

				boolean flag = false;
				
				//v.color == -1
				//u nao eh vizinho de v
				

				for(Vertex vex : v.adjacencyList) {
					if(vex.color != - 1 && vex.color == currentColor) {
						flag = true;
						break;
					}
				}
	
				
				if(!flag) 
					v.color = currentColor;
			}
			//update to a new color
			currentColor++; 
		}
		
		return currentColor;
	}
	
	
	
	
	public void printVertexColor() {
		System.out.println();
		for(Vertex v : this.vertices)
			System.out.println("\tVertex: " + v.value + "\t Color: " + v.color);
	}
	
	
}
