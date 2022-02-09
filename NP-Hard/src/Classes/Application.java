package Classes;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class Application {
	
	public long heuristicAlgorithmDuration;
	public long exactAlgorithmDuration;
	public long greedyAlgorithmDuration;
	public long bruteForceAlgorithmDuration;
	
	public int chromaticNumberBruteForce;
	public int chromaticNumberHeuristic;
	public int chromaticNumberGreedy;
	public int chromaticNumberExact;
	
	public boolean bruteForce;
	public boolean backtracking;
	
	public double graphDensity;
	public String instanceFileName;
	
	public Application() {
		this.heuristicAlgorithmDuration = 0;
		this.exactAlgorithmDuration = 0;
		this.greedyAlgorithmDuration = 0;
		this.bruteForceAlgorithmDuration = 0;
		
		this.chromaticNumberBruteForce = 0;
		this.chromaticNumberHeuristic = 0;
		this.chromaticNumberGreedy = 0;
		this.chromaticNumberExact = 0;
		
		this.backtracking = false;
		this.bruteForce = false;
		
		this.graphDensity = 0.0;
		this.instanceFileName = "";
	}

	public static void main(String[] args){
		
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
		
		
	   
		
		Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
		System.out.print("Rodar algoritmo Backtracking? (sim ou nao): ");
		String answerBacktracking = sc.nextLine();
		
		System.out.print("Rodar algoritmo Forca Bruta? (sim ou nao): ");
		String answerBruteForce = sc.nextLine();
		
		
		
		
		
		
		this.printGraphInformation(graph);
	
		this.runHeuristicAlgorithm(graph);
		graph.removeColors();
		
		this.runGreedyAlgorithm(graph);
		
		
		if(answerBacktracking.equals(new String("sim")) || answerBacktracking.equals(new String("SIM"))) {
			System.out.println(answerBacktracking);
			this.backtracking = true;
			graph.removeColors();
			this.runExactAlgorithm(graph);
		}
		
		
		
		
		if(answerBruteForce.equals(new String("sim")) || answerBruteForce.equals(new String("SIM"))) {
			
			this.bruteForce = true;
			graph.removeColors();
			this.runBruteForceAlgorithm(graph);
		}
		
		graph.removeColors();
		
		this.simulatedAnnealing(graph);
		
		sc.close();
		
		
		this.saveExecutionInformation(graph);
	}
	
	
	
	
	public void printGraphInformation(Graph graph) {
		System.out.println();
		System.out.println("Graph info:");
		System.out.println("\tInstance file: \t\t" + this.instanceFileName);
		System.out.format("\tNumber of Vertices: \t%d\n", graph.getVerticesNumber());
		System.out.format("\tNumber of Edges: \t%d\n", graph.getEdgesNumber());
		
		this.graphDensity = (2 * (graph.getEdgesNumber() * 1.0)) / (graph.getVerticesNumber() * (graph.getVerticesNumber() - 1));
		System.out.format("\tGraph density: \t\t%.2f\n", this.graphDensity);
		System.out.println();
	}
	
	
	
	
	
	
	public void runHeuristicAlgorithm(Graph graph) {
		
		long start = System.nanoTime();
		this.chromaticNumberHeuristic = graph.RLF();
		this.heuristicAlgorithmDuration = System.nanoTime() - start;
		
		this.printExecutionInformation(graph, "Heuristic Algorithm");
	}
	
	public void runGreedyAlgorithm(Graph graph) {
		
		long startGreedy = System.nanoTime();
		graph.greedyColoring();
		this.greedyAlgorithmDuration = System.nanoTime() - startGreedy;
		this.chromaticNumberGreedy = graph.getChromaticNumber();
		
		this.printExecutionInformation(graph, "Greedy Algorithm");
	}
	
	
	public void runExactAlgorithm(Graph graph) {
		
		long startExactAlgorithm = System.nanoTime();
		this.chromaticNumberExact = graph.backtracking();
		this.exactAlgorithmDuration = System.nanoTime() - startExactAlgorithm;
		
		this.printExecutionInformation(graph, "Exact Algorithm (Backtracking)");
	}
	
	public void runBruteForceAlgorithm(Graph graph) {
		long startBruteForceAlgorithm = System.nanoTime();
		
		
		graph.bruteForce();
		this.chromaticNumberBruteForce = graph.menorNumeroCromaticoForcaBruta;
		
		this.bruteForceAlgorithmDuration = System.nanoTime() - startBruteForceAlgorithm;
		
		this.printExecutionInformation(graph, "Brute Force Algorithm");
	}
	
	
	
public void simulatedAnnealing(Graph graph) {
	
		System.out.println();
		System.out.println("Simulated Annealing:");
		System.out.println();
		System.out.println();
		System.out.println("Digite os valores para os parametros (Simulated Annealing)");
		
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.print("Temperatura inicial: ");
			this.initialTemperature = sc.nextFloat();
			
			if(this.initialTemperature <= 1.0)
				System.out.println("Digite um valor real positivo maior que 1.0");
			
		}while(this.initialTemperature <= 1.0);
		
		
		
		do {
			System.out.print("Parametro L - iteracoes por temperatura: ");
			this.L = sc.nextInt();
			
			if(this.L <= 0)
				System.out.println("Digite um valor inteiro maior que 0");
			
		}while(this.L <= 0);
		
		
		do {
			System.out.print("Parametro alfa - taxa de resfriamento: ");
			this.coolingRate = sc.nextFloat();
			
			if(this.coolingRate <= 0)
				System.out.println("Digite um valor real maior que 0");
			else if(this.coolingRate >= 1.0)
				System.out.println("Digite um valor real menor que 1");
			
		}while(this.coolingRate <= 0 || this.coolingRate >= 1.0);
		
		
		this.runSimulatedAnnealing(graph);
	}
	

	
	public void runSimulatedAnnealing(Graph graph) {
		
		
		long startNExecutions = System.nanoTime();
		this.fastestRunSA = Integer.MAX_VALUE;
		this.slowestRunSA = Integer.MIN_VALUE;
		this.bestChromaticNumberSA = Integer.MAX_VALUE;
		
		Graph bestSolution = null;
		
		for(int i = 0; i < 50; i++) {
			
			graph.removeColors();
			
			long start = System.nanoTime();
			Graph graphTest = graph.simulatedAnnealing(this.initialTemperature, this.L, this.coolingRate);
			long duration = System.nanoTime() - start;
			
			
			//Store fastest run
			if(duration < this.fastestRunSA) 
				this.fastestRunSA = duration;
			//Store slowest run
			else if(duration > this.slowestRunSA)
				this.slowestRunSA = duration;
			
			int chromaticNumber = graphTest.getChromaticNumber();
			
			//Store best solution found
			if(chromaticNumber < this.bestChromaticNumberSA) {
				bestSolution = new Graph(graphTest);
				this.bestChromaticNumberSA = chromaticNumber;
				this.solutionFrequencySA = 1;
			}
			else if(chromaticNumber == this.bestChromaticNumberSA)
				this.solutionFrequencySA++;
			
		}
		
		long totalDuration = System.nanoTime() - startNExecutions;
		this.avgTimeSA = totalDuration / 50;
		
		this.printSAinformation(bestSolution);
		
		
		
	}
	
	private void printSAinformation(Graph bestSolution) {
		
		
		System.out.println("Number of executions: " + 50);
		
		System.out.println();
		
		System.out.println("Average time: \t" + this.avgTimeSA + " ns.");
		System.out.println("Fastest run: \t" + this.fastestRunSA + " ns.");
		System.out.println("Slowest run: \t" + this.slowestRunSA + " ns.");
		
		System.out.println();
		
		System.out.println("Chromatic number: " + this.bestChromaticNumberSA);
		System.out.println("Number of times the best solution was achieved: " + this.solutionFrequencySA);
		
		System.out.println();
		
		System.out.println("Best solution coloring:");
		bestSolution.printVertexColor();
		
		if(bestSolution.verifyColoring()) 
			System.out.println("Correct coloring");
		else
			System.out.println("Incorrect coloring");
		
	}
	
	
	
	public void printExecutionInformation(Graph graph, String algorithmExecuted) {
		
		System.out.println("Execution Information - " + algorithmExecuted +"\n");
		
		
		
		if(algorithmExecuted == "Heuristic Algorithm") {
			System.out.println("(Daniel-RLF)");
			System.out.println("\tChromatic Number: " + this.chromaticNumberHeuristic);
			System.out.println("\tDuration: " + this.heuristicAlgorithmDuration + " ns.\n");
		}
		else if(algorithmExecuted == "Greedy Algorithm") {
			System.out.println("\tChromatic Number: " + this.chromaticNumberGreedy);
			System.out.println("\tDuration: " + this.greedyAlgorithmDuration + " ns.\n");
		}
		else if(algorithmExecuted == "Exact Algorithm (Backtracking)" || algorithmExecuted == "Brute Force Algorithm") {
			
			if(algorithmExecuted == "Exact Algorithm (Backtracking)") {
				System.out.println("\tChromatic Number: " + this.chromaticNumberExact);
				System.out.println("\tDuration: " + this.exactAlgorithmDuration + " ns.\n");
			}
			else {
				
				System.out.println("\tChromatic Number: " + this.chromaticNumberBruteForce);
				System.out.println("\tDuration: " + this.bruteForceAlgorithmDuration + " ns.\n");
			}
			
			if(graph.verifySavedColoring())
				System.out.println("\tColoring is correct.");
			else
				System.out.println("\tColoring is incorrect.");
			
			
			System.out.println("\tColor assignment: ");
			graph.print();
			return;
		}
		
		
		if(graph.verifyColoring())
			System.out.println("\tColoring is correct.");
		else
			System.out.println("\tColoring is incorrect.");
		
		System.out.println("\tColor assignment: ");
		
		graph.printVertexColor();
		
	}
	
	
	
	
	public String getCurrentTime() {
		LocalTime time = LocalTime.now();
		
		Integer hour = time.getHour();
		Integer minute = time.getMinute();
		
		return hour.toString() + "h" + minute.toString();
	}
	
	private void saveExecutionInformation(Graph graph) {
		try {
			
			String fileName = "src/reports/" + this.getCurrentTime() + "_execution-report.txt";
			
			FileWriter fileWriter = new FileWriter(fileName);
			
			fileWriter.write("Instance file: " + this.instanceFileName + "\n");
			fileWriter.write("Number of Vertices: " + graph.getVerticesNumber() + "\n");
			fileWriter.write("Number of Edges: " + graph.getEdgesNumber() + "\n");
			fileWriter.write("Graph Density: " + this.graphDensity + "\n");
			
			
			fileWriter.write("\n");
			fileWriter.write("\n");
			
			
			fileWriter.write("HEURISTIC ALGORITHM (Daniel-RLF)\n");
			fileWriter.write("Number of colors used: " + this.chromaticNumberHeuristic + "\n");
			fileWriter.write("Duration: " + this.heuristicAlgorithmDuration + " ns\n");
			
			fileWriter.write("\n");
			fileWriter.write("\n");
			
			
			fileWriter.write("GREEDY ALGORITHM\n");
			fileWriter.write("Number of colors used: " + this.chromaticNumberGreedy + "\n");
			fileWriter.write("Duration: " + this.greedyAlgorithmDuration + " ns\n");
			
			
			if(this.backtracking) {
				fileWriter.write("\n");
				fileWriter.write("\n");
				fileWriter.write("BACKTRACKING ALGORITHM (EXACT)\n");
				fileWriter.write("Number of colors used: " + this.chromaticNumberExact + "\n");
				fileWriter.write("Duration: " + this.exactAlgorithmDuration + " ns\n");
			}
			
			
			
			if(this.bruteForce) {
				fileWriter.write("\n");
				fileWriter.write("\n");
				fileWriter.write("BRUTE FORCE ALGORITHM (EXACT)\n");
				fileWriter.write("Number of colors used: " + this.chromaticNumberBruteForce + "\n");
				fileWriter.write("Duration: " + this.bruteForceAlgorithmDuration + " ns\n");
			}
			
			
			//Simulated Annealing
			
			fileWriter.write("\n");
			fileWriter.write("SIMULATED ANNEALING\n");
			fileWriter.write("\n");
			
			fileWriter.write("Number of executions: \t" + 50 + "\n");
			fileWriter.write("Best chromatic number: \t" + this.bestChromaticNumberSA + "\n");
			fileWriter.write("Frequency of best solution achieved: \t" + this.solutionFrequencySA + "\n");
			
			fileWriter.write("\n");
			fileWriter.write("Average time: \t" + this.avgTimeSA + " ns.\n");
			fileWriter.write("Fastest run: \t" + this.fastestRunSA + " ns.\n");
			fileWriter.write("Slowest run: \t" + this.slowestRunSA + " ns.\n");
			
			fileWriter.write("\n");
			
			fileWriter.write("Valores dos Parametros: \n");
			fileWriter.write("Temperatura inicial (T): \t" + this.initialTemperature + "\n");
			fileWriter.write("Iteracoes por temperatura (L): \t" + this.L + "\n");
			fileWriter.write("Taxa de resfriamento (alfa): \t" + this.coolingRate + "\n");
			
			System.out.println("Execution information saved on " + fileName);
			
			fileWriter.close();
		}
		catch(IOException e) {
			System.out.println("Error: it was not possible to write on file");
			System.out.println(e.getMessage());
		}
	}
	
	
	public long slowestRunSA;
	public long fastestRunSA;
	public long avgTimeSA;
	public int bestChromaticNumberSA;
	public float coolingRate;
	public int L;
	public float initialTemperature;
	public int solutionFrequencySA;
	
	
	
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
		

		graph.setNumberEdges(Integer.valueOf(information[3]).intValue()/2);
		
		
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
