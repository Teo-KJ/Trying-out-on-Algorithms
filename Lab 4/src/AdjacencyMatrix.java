import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AdjacencyMatrix {

	private static Scanner sc;
	private static Integer departureID;
	private static Integer destinationID;
	private static String csvFile;
	private static BufferedReader bufferedReader = null;
	private static String line = "";
	private static String cvsSplitBy = ",";
	
	public static void main(String[] args){
		LinkedList<Integer> visited = new LinkedList<Integer>();
		visited.add(departureID);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(departureID);
		sc = new Scanner(System.in);
		int option;
		int size;

		System.out.println("Welcome to the Flight Path Search!");
		System.out.println("Please choose the region you would be travelling in.");
		System.out.println("1. Southeast Asia \n2. The World \n3. Asia Pacific \n0. Quit");

		do {
			option = sc.nextInt();
			boolean[][] graph;
			
			switch (option) {
			case 1:
				csvFile = "src/southeastAsiaGraph.csv";
				size = 10;
				graph = newGraph(size);
				breadthFirstSearch(graph, size);
				System.out.println();
				break;
			case 2:
				csvFile = "src/worldGraph.csv";
				size = 27;
				graph = newGraph(size);
				breadthFirstSearch(graph, size);
				System.out.println();
				break;
			case 3:
				csvFile = "src/asiaPacificGraph.csv";
				size = 19;
				graph = newGraph(size);
				breadthFirstSearch(graph, size);
				System.out.println();
				break;
			case 0:
				System.out.println("Goodbye.");
				break;
			default:
				System.out.println("Goodbye.");
			}
		} while ((option < 3) && (option >= 0));
		sc.close();
	}
	
	static boolean[][] newGraph(int size){
		
		boolean[][] graph = new boolean[size][size];
		int lineCounter = 0;
		sc.nextLine();
		System.out.println("Here are your city options:");
		
		try{
			bufferedReader = new BufferedReader(new FileReader(csvFile));

			while ((line = bufferedReader.readLine()) != null){
				String[] entry = line.split(cvsSplitBy);
				
				System.out.printf("(%s) %s\n", entry[1], entry[0]);
				
				for (int i = 1; i < entry.length; i++){
					Integer city = Integer.parseInt(entry[i]);
					graph[city][lineCounter] = true;
					graph[lineCounter][city] = true;
				}
				lineCounter++;
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		
		finally{
			if (bufferedReader != null){
				try { bufferedReader.close(); }
				catch (IOException e) {e.printStackTrace(); }
			}
		}
		
		System.out.println("\nPlease enter your city of departure");
		departureID = sc.nextInt();
		System.out.println("Please enter your destination");
		destinationID = sc.nextInt();
		
		return graph;
	}
	
	static void breadthFirstSearch(boolean[][] graph, int size){
		Queue<LinkedList<Integer>> queue = new LinkedList<>();
		LinkedList<Integer> visited = new LinkedList<Integer>();
		LinkedList<Integer> path = new LinkedList<Integer>();
		long time = System.nanoTime();
		
		path.add(departureID);
		queue.add(path);
		visited.add(departureID);
		
		while (!queue.isEmpty()){
			path = queue.poll();
			Integer current = path.get(path.size() - 1);
			
			for (int neighbour = 0; neighbour < size; neighbour++)
			{
				if (graph[current][neighbour])
				{
					if (!visited.contains(neighbour)){
						LinkedList<Integer> nextPath = new LinkedList<Integer>(path);
						visited.add(neighbour);
						nextPath.add(neighbour);
						queue.add(nextPath);
					
						if (neighbour == destinationID){
							time = System.nanoTime() - time;
							
							System.out.println("\nYour route from " + findName(departureID) + " to " + findName(destinationID) + " is:");
							path.add(destinationID);
							for (Integer next : path){
								System.out.println(findName(next));
							}
							break;
						}
					}
				}
			}
		}
		// time_in_ms = time/Math.pow(10, 6);
		System.out.println("With the breadth first search algorithm, the route was computed in " + time/Math.pow(10, 6) + " millisecond (ms).\n");
	}
	
	static String findName(Integer locationID){
		try{
			bufferedReader = new BufferedReader(new FileReader(csvFile));

			while ((line = bufferedReader.readLine()) != null){
				String[] entry = line.split(cvsSplitBy);
				
				if (Integer.parseInt(entry[1]) == locationID)
					return entry[0];
				
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		
		finally{
			if (bufferedReader != null){
				try { bufferedReader.close(); }
				catch (IOException e) {e.printStackTrace(); }
			}
		}
		return "";
	}
}

