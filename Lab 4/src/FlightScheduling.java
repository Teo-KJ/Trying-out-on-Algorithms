import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FlightScheduling {

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

		System.out.println("Welcome to the Flight Path Search!");
		System.out.println("Please choose the region you would be travelling in.");
		System.out.println("1. Southeast Asia \n2. The World \n0. Quit");

		do {
			option = sc.nextInt();
			LinkedList<Integer>[] graph;
			
			switch (option) {
			case 1:
				csvFile = "C:/Users/tkjie/Documents/GitHub/Trying-Out-on-Algorithms/Lab 4/src/southeastAsiaGraph.csv";
				graph = newGraph(10);
				breadthFirstSearch(graph);
				System.out.println();
				depthFirstSearch(departureID, graph, path, visited);
				break;
			case 2:
				csvFile = "C:/Users/tkjie/Documents/GitHub/Trying-Out-on-Algorithms/Lab 4/src/worldGraph.csv";
				graph = newGraph(22);
				breadthFirstSearch(graph);
				System.out.println();
				depthFirstSearch(departureID, graph, path, visited);
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
	
	static LinkedList<Integer>[] newGraph(int size){
		
		LinkedList<Integer>[] graph = new LinkedList[size];
		int lineCounter = 0;
		sc.nextLine();
		System.out.println("Here are your city options:");
		
		try{
			bufferedReader = new BufferedReader(new FileReader(csvFile));

			while ((line = bufferedReader.readLine()) != null){
				String[] entry = line.split(cvsSplitBy);
				graph[lineCounter] = new LinkedList<Integer>();
				
				System.out.printf("(%s) %s\n", entry[1], entry[0]);
				
				for (int i = 1; i < entry.length; i++){
					graph[lineCounter].add(Integer.parseInt(entry[i]));
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
	
	static void depthFirstSearch(Integer current, LinkedList<Integer>[] graph, LinkedList<Integer> path, LinkedList<Integer> visited) {
		for (Integer neighbour : graph[current]){
			if (!visited.contains(neighbour)){
				visited.add(neighbour);
				if (neighbour == destinationID) {
					path.add(neighbour);
					System.out.println("=========================================================================================");
					System.out.printf("If the depth first search algorithm is used, the following is the alternative route presented.\n");
					System.out.println("\nYour route from " + findName(departureID) + " to " + findName(destinationID) + " is:");
					for (Integer next : path){
						if (next != null){
						System.out.println(findName(next));
						}
					}
				}
				else {
					path.add(neighbour);
					depthFirstSearch(neighbour, graph, path, visited);
					path.removeLast();
				}
			}
		}
	}
	
	static void breadthFirstSearch(LinkedList<Integer>[] graph){
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

			for (Integer neighbour : graph[current]){
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
