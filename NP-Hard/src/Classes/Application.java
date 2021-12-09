package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class Application {
	
	public long algorithmDuration;
	public String instanceFileName;
	
	public Application() {
		this.algorithmDuration = 0;
		this.instanceFileName = "";
	}

	public static void main(String[] args) {
		
		Application app = new Application();
		app.run();
		
	}
	
	
	public void run() {
		
		this.instanceFileName = this.selectInstance();
		
		Graph graph = this.setUpGraph();
		
		if(graph == null) {
			System.out.println("Error during the graph creation");
			return;
		}
		
		
		long start = System.nanoTime();
		int chromaticNumber = graph.RLF();
		this.algorithmDuration = System.nanoTime() - start;
		
		
		this.printExecutionInfo(graph, chromaticNumber);
		
		this.saveExecutionInformation(graph, chromaticNumber);
	}
	
	public String getCurrentTime() {
		LocalTime time = LocalTime.now();
		
		Integer hour = time.getHour();
		Integer minute = time.getMinute();
		
		return hour.toString() + "h" + minute.toString();
	}
	
	private void saveExecutionInformation(Graph graph, int chromaticNumber) {
		try {
			
			String fileName = "src/reports/" + this.getCurrentTime() + "_execution-report.txt";
			
			FileWriter fileWriter = new FileWriter(fileName);
			
			fileWriter.write("Instance file: " + this.instanceFileName + "\n");
			fileWriter.write("Number of Vertices: " + graph.getVerticesNumber() + "\n");
			fileWriter.write("Number of Edges: " + graph.getEdgesNumber() + "\n");
			
			fileWriter.write("Number of colors used: " + chromaticNumber + "\n");
			fileWriter.write("Duration: " + this.algorithmDuration + " ns\n");
			
			System.out.println("Execution information saved on " + fileName);
			
			fileWriter.close();
		}
		catch(IOException e) {
			System.out.println("Error: it was not possible to write on file");
			System.out.println(e.getMessage());
		}
	}
	
	
	private void printExecutionInfo(Graph graph, int chromaticNumber) {
		System.out.println("");
		
		System.out.println("Execution information:");
		
		System.out.println("\tInstance file: " + this.instanceFileName);
		System.out.println("");
		System.out.format("\tNumber of Vertices: %d\n", graph.getVerticesNumber());
		System.out.format("\tNumber of Edges: %d\n", graph.getEdgesNumber());
		
		System.out.println("");
		
		if(graph.verifyColoring())
			System.out.println("\tColoring is correct.");
		else
			System.out.println("\tColoring is incorrect.");
		
		System.out.println("");
		
		System.out.format("\tChromatic number: %d\n", chromaticNumber);
		System.out.println("");
		System.out.format("\tDuration: %d ns", this.algorithmDuration);
		System.out.println("");
		graph.printVertexColor();
	}
	
	
	
	private void printAvailableInstances() {
		
		
		File folder = new File("src/instances/");
		System.out.println("Available instance files: ");
			
		for(File file : folder.listFiles()) {
			if(file.isFile() && file.canRead()) 
				System.out.println("\t" + file.getName());
		}

	}
	
	
	
	
	private String selectInstance()  {
		
		this.printAvailableInstances();
		
		Scanner sc = new Scanner(System.in);  
		System.out.print("Digite o nome do arquivo de teste (nome_arquivo.txt): ");
		String instanceFileName = sc.nextLine();
		sc.close();
		
		instanceFileName = "src/instances/" + instanceFileName;
		
		return instanceFileName;
	}
	

	
	
	
	public Graph setUpGraph() {
		return this.setUpGraph(new Graph(), this.instanceFileName);
	}
	
	
	private Graph setUpGraph (Graph graph, String instanceFileName)  {
		
		try {
			File instanceFile = new File (instanceFileName);
			
			Scanner fileReader = new Scanner(instanceFile);
			this.readInstances(fileReader, graph);
			
			fileReader.close();
			
			return graph;
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private void readInstances(Scanner fileReader, Graph graph) {
		String line = fileReader.nextLine();
		String[] information = line.split(" ");
		

		graph.setNumberEdges(Integer.valueOf(information[3]).intValue());
		
		
		while(fileReader.hasNextLine()) {
			line = fileReader.nextLine();
			line = line.strip();
			
			String[] values = line.split(" ");
			
			int u = Integer.valueOf(values[1]).intValue();
			int v = Integer.valueOf(values[2]).intValue();
			
			//System.out.format("Edge (%d,%d)\n", u,v);
			
			graph.addEdge(u, v);
			
		}
		
	}

}
