package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class Graph {
	
	private ArrayList<Vertex> vertices;
	private int edgesNumber;
	
	
	public Graph() {
		this.vertices = new ArrayList<Vertex>();
		this.edgesNumber = 0;
	}
	
	public Graph(Graph g) {
		
		this.vertices = new ArrayList<Vertex>();
		for(Vertex v : g.vertices) {
			this.vertices.add(new Vertex(v));
		}
		
		this.edgesNumber = g.edgesNumber;
		
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
				if(vertex.color == neighbor.color)
					return false;	
			}
		}
		
		return true;
	}
	
	public boolean verifySavedColoring() {
		for(Vertex vertex : this.colored) {
			
			for(Vertex neighbor : vertex.adjacencyList) {
				
				if(vertex.equals(neighbor))
					continue;
				if(vertex.color == neighbor.color)
					return false;	
			}
		}
		
		return true;
	}
	
	
	
	//Heuristic greedy algorithm for the minimum graph coloring problem
		//Time complexity: O(n^2 * m)
		//Return: The chromatic number
	
	public int RLF() {
		int currentColor = 0;

		this.vertices.sort(null);
		
		//Select vertex to color
		for(int i = 0; i < this.vertices.size(); i++) {
			
			Vertex u = this.vertices.get(i);
			
			if(u.color != -1) //Vertex has already been assigned a color
				continue;
			
			u.color=currentColor;
			
			//Now, color all the possible vertices with "currentColor"
			
			for(int j = i+1; j < this.vertices.size(); j++) {
				Vertex v = this.vertices.get(j);
				
				//O vertice v ja foi colorido ou eh adjacente ao vertice u
				if(v.color != -1 || u.isAdjacentTo(v)) 
					continue;

				

				//Verificar se algum vertice adjacente ao vertice 'v'
				//	foi colorido com a cor "currentColor"
				//	Isso sera indicado pela variavel booleana "flag"
				boolean flag = false;
				
				for(Vertex vex : v.adjacencyList) {
					if(vex.color != - 1 && vex.color == currentColor) {
						flag = true;
						break;
					}
				}
	
				if(!flag) 
					v.color = currentColor;
			}
			
			//atualizar cor
			currentColor++; 
		}
		
		return currentColor;
	}
	
	
	public void removeColors() {
		for(Vertex vex : this.vertices)
			vex.color = -1;
	}
	
	
	

	private boolean isSafe(Vertex vertice, int color) {
		for(Vertex v : vertice.adjacencyList) {
			if(v.color != -1 && v.color == color)
				return false;
		}
		return true;
	}
	
	
	public void bruteForce() {
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		this.menorNumeroCromaticoForcaBruta = this.vertices.size();
		System.out.println("Algoritmo (forca bruta) em execucao...");
		this.bruteForce(0, map);
	}

	public int menorNumeroCromaticoForcaBruta;
	
	
	
	private void bruteForce(int index, Map<Integer, Integer> colorsUsed) {
		
		if(index == this.vertices.size()){
			if(this.verifyColoring()) {
				if(colorsUsed.size() < this.menorNumeroCromaticoForcaBruta) {
					
					System.out.println("Melhor numero cromatico encontrado ate o momento (forca bruta): " + colorsUsed.size());
					System.out.println("Algoritmo (forca bruta) em execucao...");
					this.menorNumeroCromaticoForcaBruta = colorsUsed.size();
					
				}
					
				return;
			}
			return;
		}
		
		Vertex currentVertex = this.vertices.get(index);
		
		for(int i = 1; i <= this.vertices.size(); i++) {
			
			currentVertex.color = i;
				
			if(!colorsUsed.containsKey(i)) {
				colorsUsed.put(i, 1);
			}
			else {
				Integer num = colorsUsed.get(i);
				num++;
				colorsUsed.put(i, num);
			}
				
			this.bruteForce(index+1,colorsUsed);
				
			
			if(colorsUsed.get(i) == 1) {
				colorsUsed.remove(i);
			}
			else {
				Integer num = colorsUsed.get(i);
				num--;
				colorsUsed.put(i, num);
			}
			
			
			currentVertex.color = -1;
		}
		return;
	}
	
	
	
	
	public int backtracking() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		this.menorNumeroCromaticoBacktracking = this.vertices.size();
		
		System.out.println("Algoritmo (backtracking) em execucao...");
		this.backtracking(0, map);
		
		
		return this.menorNumeroCromaticoBacktracking;
	}
	

	private int menorNumeroCromaticoBacktracking;
	private ArrayList<Vertex> colored;
	
	private void saveColoring() {
		this.colored = new ArrayList<Vertex>();
		for(Vertex v : this.vertices) {
			Vertex u = new Vertex(v);
			this.colored.add(u);
		}
	}
	
	
	private void backtracking(int index, Map<Integer, Integer> colorsUsed) {
		
		if(index == this.vertices.size()) {
			if(colorsUsed.size() < this.menorNumeroCromaticoBacktracking) {
				
				System.out.println("Melhor numero cromatico encontrado ate o momento (backtracking): " + colorsUsed.size());
				System.out.println("Algoritmo (backtracking) em execucao...");
				//save the coloring
				this.menorNumeroCromaticoBacktracking = colorsUsed.size();
				this.saveColoring();
			}
			return;
		}
			
		
		Vertex currentVertex = this.vertices.get(index);
		
		for(int i = 1; i <= this.vertices.size(); i++) {
			
			if(this.isSafe(currentVertex, i)) {
				
				currentVertex.color = i;
				
				if(!colorsUsed.containsKey(i)) 
					colorsUsed.put(i, 1);	
				else {
					Integer num = colorsUsed.get(i);
					num++;
					colorsUsed.put(i, num);
				}
					
				if(this.menorNumeroCromaticoBacktracking > colorsUsed.size())
					this.backtracking(index+1, colorsUsed);
					
				
				if(colorsUsed.get(i) == 1) 
					colorsUsed.remove(i);
				else {
					Integer num = colorsUsed.get(i);
					num--;
					colorsUsed.put(i, num);
				}
				

				currentVertex.color = -1;
			}

		}
		return;
	}
	
	
	
	
	
	
	
	public void printVertexColor() {
		System.out.println();
		for(Vertex v : this.vertices)
			System.out.println("\tVertex: " + v.value + "\t Color: " + v.color);
		System.out.println();
	}
	
	
	public void print() {
		System.out.println();
		for(Vertex v : this.colored)
			System.out.println("\tVertex: " + v.value + "\t Color: " + v.color);
		System.out.println();
	}
	
	
	
	
	
	
	public void greedyColoring() {
		
		//O vetor "available" sera utilizado para verificar 
				//	eh a qual a menor cor disponivel para atribuir a um vertice
		boolean available[] = new boolean[this.vertices.size()];
		Arrays.fill(available, true);
		
		
		//Colorir o primeiro vertice
		Vertex firstVertex = this.vertices.get(0);
		firstVertex.color = 0;
		
		
		for(int i = 1; i < this.vertices.size(); i++) {
			
			Vertex v = this.vertices.get(i);
			
			for(Vertex u : v.adjacencyList) {
				if(u.color != -1) 
					available[u.color] = false;
			}
			
			int colorAvailable = 0;
			
			while(colorAvailable < this.vertices.size()) {
				if(available[colorAvailable]) 
					break;
				colorAvailable++;
			}
			
			
			v.color = colorAvailable;
			Arrays.fill(available, true);
		}
		
		
			
	}
	
	
	public int getChromaticNumber() {
		return this.chromaticNumber();
	}
	
	
	private int chromaticNumber() {
		int chromaticNumber = 0;
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		
		for(Vertex v : this.vertices) {
			if(!map.containsKey(v.color)) {
				map.put(v.color, v.value);
				chromaticNumber++;
			}
		}
		
		return chromaticNumber;
	}
	
	
	public Graph randomNeighbor() {
		
		Graph neighbor = new Graph(this);
		
		Random rand = new Random();

		//[0 - this.vertices.size() - 1].
		int vertexIndex = rand.nextInt(this.vertices.size());
		
		Vertex v = neighbor.vertices.get(vertexIndex);
		
		int possibleColors = this.chromaticNumber() - 1;
		
		int randomColor;
		
		do {
			
			randomColor = rand.nextInt(possibleColors);
			possibleColors--;
			
		}while(possibleColors >=1 && (randomColor == v.color || !this.isSafe(v, randomColor)));
		
		if(this.isSafe(v, randomColor))
			v.color = randomColor;	
			
		
		return neighbor;
	}
	
	
	
	public Graph simulatedAnnealing(float initialTemperature, int L, float alfa) {
		float T = initialTemperature;
		double e = 2.718;
		//double alfa = 0.85; //cooling rate

		
		this.greedyColoring();//Initial graph
		Graph initialSolution = new Graph(this);

		
		while(T > 0.1) {
			
			for(int i = 0; i < L; i++) {
				Graph s_new = this.randomNeighbor();
				
				int delta = s_new.getChromaticNumber() - initialSolution.getChromaticNumber();
				
				if(delta <= 0) {
					initialSolution = new Graph(s_new);
				}
				else if(Math.random() < Math.pow(e, ((-1.0)*delta)/T)){
					initialSolution = s_new;
				}
			}
			
			
			T *= alfa; 
		}
		
		return initialSolution;
	}
	
	
	
	public boolean sameGraphs(Graph other) {
		for(int i = 0; i < this.vertices.size(); i++) {
			if(this.vertices.get(i).color != other.vertices.get(i).color)
				return false;
		}
		return true;
	}
	
}
