package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Graph graph = new Graph();
		
		Main.getIntances(graph);
		
		System.out.println("Chromatic number: " + graph.WelshPowell());
		//graph.printVertexColor();
	}
	
	public static void getIntances(Graph graph) throws FileNotFoundException {
		
		try {
			File instances = new File("src/instances.txt");
			
			if(!instances.canRead()) {
				System.out.println("File is not readable");
				return;
			}

			Scanner reader = new Scanner(instances);
			
			Main.readInstances(reader, graph);
			reader.close();
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}
	
	public static void readInstances(Scanner reader, Graph graph) {
		String line = reader.nextLine();
		String[] information = line.split(" ");
		
		System.out.println("Number of vertices: " + information[2]+"\n"
		+ "Number of edges: " + information[3]);
		
		
		//Considering the implementation of string with an array,
			// we cannot use an iterator over it
		
		while(reader.hasNextLine()) {
			line = reader.nextLine();
			line = line.strip();
			
			String[] values = line.split(" ");
			
			int u = Integer.valueOf(values[1]).intValue();
			int v = Integer.valueOf(values[2]).intValue();
			//System.out.format("Edge: (%d,%d)\n",u, v);
			
			graph.addEdge(u, v);
			
		}
		
	}

}
