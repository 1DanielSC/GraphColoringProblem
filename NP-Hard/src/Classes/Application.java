package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {
	
	public Application() {
		
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		Application app = new Application();
		app.run();
		
	}
	
	
	public void run() throws FileNotFoundException {
		String instanceFileName = this.selectInstance();
		
		Graph graph = this.setUpGraph(instanceFileName);
		
		if(graph == null) {
			System.out.println("Error during the creation of graph");
			return;
		}
		
		
		long start = System.currentTimeMillis();
		int chromaticNumber = graph.RLF();
		long timeElapsed = System.currentTimeMillis() - start;
		
		this.printExecutionInfo(timeElapsed, chromaticNumber);
	}
	
	
	private void printExecutionInfo(long timeElapsed, int chromaticNumber) {
		System.out.format("Chromatic number: %d\n", chromaticNumber);
		System.out.format("Duration: %d ms", timeElapsed);
	}
	
	
	private String selectInstance() throws FileNotFoundException {
		
		this.printAvailableInstances();
		
		Scanner sc = new Scanner(System.in);  
		System.out.print("Digite o nome do arquivo de teste (nome_arquivo.txt) para execucao: ");
		String instanceFileName = sc.nextLine();
		sc.close();
		
		instanceFileName = "src/instances/" + instanceFileName;
		
		return instanceFileName;
	}
	
	
	private void printAvailableInstances() {
		File folder = new File("src/instances/");
		System.out.println("Instancias disponiveis: ");
		
		for(File file : folder.listFiles()) {
			if(file.isFile() && file.canRead()) 
				System.out.println("\t" + file.getName());
		}
	}
	
	
	
	public Graph setUpGraph(String instanceFileName) throws FileNotFoundException {
		return this.setUpGraph(new Graph(), instanceFileName);
	}
	
	
	private Graph setUpGraph (Graph graph, String instanceFileName) throws FileNotFoundException {
		
		try {
			File instanceFile = new File (instanceFileName);
			Scanner fileReader = new Scanner(instanceFile);
			this.readInstances(fileReader, graph);
			fileReader.close();
			return graph;
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private void readInstances(Scanner fileReader, Graph graph) {
		String line = fileReader.nextLine();
		String[] information = line.split(" ");
		
		
		System.out.println("Number of vertices: " + information[2]+"\n"
		+ "Number of edges: " + information[3]);
		
		
		//Considering the implementation of string with an array,
			// we cannot use an iterator over it
		
		while(fileReader.hasNextLine()) {
			line = fileReader.nextLine();
			line = line.strip();
			
			String[] values = line.split(" ");
			
			int u = Integer.valueOf(values[1]).intValue();
			int v = Integer.valueOf(values[2]).intValue();
			
			graph.addEdge(u, v);
			
		}
		
	}

}
