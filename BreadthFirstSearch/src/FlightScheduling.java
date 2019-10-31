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
	
	public static void main(String[] args)
	{
		sc = new Scanner(System.in);
		
		int size = 0;
		 while (size != 10) // TODO add other size options
		{
			System.out.println("Please choose the size of the graph to run (10, 100)");
			size = sc.nextInt();
			sc.nextLine();
		}
		 
		if (size == 10) //TODO add other size options
		{	
			csvFile = "src/size10graph.cvs";
			breadthFirstSearch(newGraph(10));
		}
	}
	
	static LinkedList<Integer>[] newGraph(int size)
	{
		LinkedList<Integer>[] graph = new LinkedList[size];
		int lineCounter = 0;
		
		String departure, destination;
		System.out.println("Please enter your city of departure");
		departure = sc.nextLine();
		//sc.nextLine();
		System.out.println("Please enter your destination");
		destination = sc.nextLine();
		
		try
		{
			bufferedReader = new BufferedReader(new FileReader(csvFile));

			while ((line = bufferedReader.readLine()) != null)
			{
				String[] entry = line.split(cvsSplitBy);
				graph[lineCounter] = new LinkedList<Integer>();
				if (entry[0].equals(departure))
					{
						departureID = Integer.parseInt(entry[1]);

					}
				else if (entry[0].equals(destination))
					{
						destinationID = Integer.parseInt(entry[1]);
					}
				
				for (int i = 1; i < entry.length; i++)
				{
					graph[lineCounter].add(Integer.parseInt(entry[i]));
				}
				lineCounter++;
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		finally
		{
			if (bufferedReader != null)
			{
				try { bufferedReader.close(); }
				catch (IOException e) {e.printStackTrace(); }
			}
		}
		return graph;
	}
	
	static void breadthFirstSearch(LinkedList<Integer>[] graph)
	{
		Queue<LinkedList<Integer>> queue = new LinkedList<>();
		LinkedList<Integer> visited = new LinkedList<Integer>();
		LinkedList<Integer> path = new LinkedList<Integer>();
		long time = System.nanoTime();
		
		path.add(departureID);
		queue.add(path);
		visited.add(departureID);
		
		while (!queue.isEmpty())
		{
			path = queue.poll();
			Integer current = path.get(path.size() - 1);

			
			for (Integer neighbour : graph[current])
			{
				if (!visited.contains(neighbour))
				{
					LinkedList<Integer> nextPath = new LinkedList(path);
					visited.add(neighbour);
					nextPath.add(neighbour);
					queue.add(nextPath);
				
					if (neighbour == destinationID)
					{
						time = System.nanoTime() - time;
						System.out.printf("We have found your route in %d nanoseconds\n", time);
						System.out.println("Your route is:");
						path.add(destinationID);
						for (Integer next : path)
						{
							System.out.println(next);
							System.out.println(findName(next));
						}
						break;
					}
				}
			}
		}
	}
	
	static String findName(Integer locationID)
	{
		try
		{
			bufferedReader = new BufferedReader(new FileReader(csvFile));

			while ((line = bufferedReader.readLine()) != null)
			{
				String[] entry = line.split(cvsSplitBy);
				
				if (Integer.parseInt(entry[1]) == locationID)
				{
					return entry[0];
				}
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		finally
		{
			if (bufferedReader != null)
			{
				try { bufferedReader.close(); }
				catch (IOException e) {e.printStackTrace(); }
			}
		}
		return "";
	}

}


