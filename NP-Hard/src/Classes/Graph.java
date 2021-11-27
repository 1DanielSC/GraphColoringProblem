package Classes;

import java.util.ArrayList;


public class Graph {
	
	public ArrayList<Vertex> vertices;
	
	public Graph() {
		this.vertices = new ArrayList<Vertex>();
	}
	
	public void addVertex(int u) {
		
		Vertex vertex = new Vertex(u);
		
		if(this.vertices.contains(vertex))
			return;
		
		this.vertices.add(vertex);
		
	}
	
	public void addEdge(int u, int v) {
		
		Vertex v1 = new Vertex(u);
		Vertex v2 = new Vertex(v);
		
		if(!this.vertices.contains(v1)) 
			this.vertices.add(v1);
		if(!this.vertices.contains(v2)) 
			this.vertices.add(v2);
	
		
		
		for(Vertex vertex : this.vertices) {
			if(vertex.equals(v1)) 
				vertex.addEdge(v2);
		
			else if(vertex.equals(v2)) 
				vertex.addEdge(v1);
		}
		
		
	}
	
	//Edge (u,v)
	public void removeEdge(Vertex u, Vertex v) {
		
		u.removeEdge(v);
		v.removeEdge(u);
		/*
		for(Vertex vertex : this.vertices) {
			if(vertex.equals(v)) {
				vertex.adjacencyList.remove(u);
				vertex.degree--;
			}
				
			else if(vertex.equals(u)) {
				vertex.adjacencyList.remove(v);
				vertex.degree--;
			}
		}
		 */
		
	}
	
	
	
	
	
	//Heuristic greedy algorithm for the minimum graph coloring problem
		//Time complexity: O(n^2)
		//Return: The chromatic number
	
	public int WelshPowell() {
		int chromaticNumber = 0;
		
		//ArrayList sorted in decreasing order according to each vertex degree
		this.vertices.sort(null);
		/*
		 * If the specified comparator is null 
		 * then all elements in this list must implement 
		 * the Comparable interface and 
		 * the elements' natural ordering should be used. 
		 */
		
		//Select vertex to color with a new color
		for(int i = 0; i < this.vertices.size(); i++) {
			
			Vertex u = this.vertices.get(i);
			
			if(u.color != -1) //Vertex has already been assigned a color
				continue;
			
			u.color=chromaticNumber;
			
			/*
			 * Precisamos verificar todos os vertices v restantes:
			 * 		Se (v nao foi colorido) e 
			 * 			(v nao eh adjacente a um vertice com a cor u.color)
			 * 		Entao, v.color = colorsNumber
			 * 
			 * 		Senao, skip
			 */
			
			//Now, color all the possible vertices
			for(int j = i+1; j < this.vertices.size(); j++) {
				Vertex v = this.vertices.get(j);
				
				if(v.color != -1 || u.isAdjacentTo(v)) 
					//O vertice v ja foi colorido ou eh adjacente ao vertice u
					continue;
				
				boolean flag = false;
				
				
				for(Vertex vertex : v.adjacencyList) {
					if(vertex.color == chromaticNumber) {
						flag = true;
						break;
					}
				}

				
				if(!flag)
					v.color = chromaticNumber;
				
			}
			
			chromaticNumber++; //atualizar a cor
			
			//Proxima iteracao: colorir os vertices adjacentes ao vertice u
		}
		
		return chromaticNumber;
	}
	
	public void printVertexColor() {
		for(Vertex v : this.vertices)
			System.out.println("Vertex: " + v.value + "\t Color: " + v.color);
	}
	
	
}
