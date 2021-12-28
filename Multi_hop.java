import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.*;
import java.net.*;
public class Multi_hop
{
	public static void main (String args[])
	{
		Scanner scanner=new Scanner(System.in);	
		String input="";
		String enter="<!--Enter the no of nodes-->";
		String enter_ip="Enter the node name";
		String ip="Select center of the node (input format 1,2,3,...) ";
		String value="Enter node position";
		String distance="Enter Distance between the node of ";
		System.out.println(enter);
		int no_of_node=scanner.nextInt();
		int[][] distance_ip=new int[no_of_node][no_of_node];
		String[] ip_address=new String[no_of_node];
		//Get Ip address
		for(int i=0;i<ip_address.length;i++)
		{
			System.out.println(enter_ip+(i+1)+" :");
			if(scanner.hasNext())
			{
				ip_address[i]=scanner.next();
			}
		}
		System.out.println(ip);
		//Center Node
		for(int i=0;i<ip_address.length;i++)
		{
			System.out.println((i+1)+". "+ip_address[i]);
		}
		//Distance Calculator
		for(int i=0;i<ip_address.length;i++)
		{
			for(int j=i+1;j<ip_address.length;j++)
			{
				System.out.println(distance+ip_address[i]+" and "+ip_address[j]);
				if(scanner.hasNext())
				{
					int distance_len=scanner.nextInt();
					distance_ip[i][j]=distance_len;
					distance_ip[j][i]=distance_len;
				}
			}
		}
		//Entered Distance
		System.out.println("Nodes:"+Arrays.toString(ip_address));
		for (int[] row : distance_ip)
            System.out.println(Arrays.toString(row));
		System.out.println("Entered Distances:");
		for(int i=0;i<ip_address.length;i++)
		{
			for(int j=i+1;j<ip_address.length;j++)
			{
				System.out.println("Distance of "+ip_address[i]+" <--> "+ip_address[j]+" = "+distance_ip[i][j] +" and "+"Distance of "+ip_address[j]+" <--> "+ip_address[i]+" = "+distance_ip[j][i]);
			}
		}
		//Goes to file send
		//from node
		System.out.println("<!--Enter From node-->");
		for(int i=0;i<ip_address.length;i++)
		{
			System.out.println((i+1)+". "+ip_address[i]);
		}
		System.out.println(value);
		int from=(scanner.nextInt())-1;
		System.out.println("Selected node as : "+ip_address[from]);
		//To node
		System.out.println("<!--Enter To node-->");
		for(int i=0;i<ip_address.length;i++)
		{
			System.out.println((i+1)+". "+ip_address[i]);
		}
		System.out.println(value);
		int to=(scanner.nextInt())-1;
		DijkstrasAlgorithm vinoth=new DijkstrasAlgorithm();
		vinoth.dijkstra(distance_ip,from,to);
		System.out.println("Selected node as : "+ip_address[to]);
		
		//
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to send this file: " +
                    chooser.getSelectedFile());
        }
		String uniqueID = UUID.randomUUID().toString();
		File source=chooser.getSelectedFile();
		String encoded_file=uniqueID+chooser.getSelectedFile().getName();
		File dest=new File(encoded_file);
		try
		{
		Files.copy(source.toPath(),dest.toPath());
		}
		catch(IOException e)
		{
			
		}
		System.out.println("The receiver side received as unique file : " +encoded_file);
		
	}
}
 class DijkstrasAlgorithm { 

	private static final int NO_PARENT = -1; 

	// Function that implements Dijkstra's 
	// single source shortest path 
	// algorithm for a graph represented 
	// using adjacency matrix 
	// representation 
	 static void dijkstra(int[][] adjacencyMatrix,int startVertex,int end) 
	{ 
		int nVertices = adjacencyMatrix[0].length; 

		// shortestDistances[i] will hold the 
		// shortest distance from src to i 
		int[] shortestDistances = new int[nVertices]; 

		// added[i] will true if vertex i is 
		// included / in shortest path tree 
		// or shortest distance from src to 
		// i is finalized 
		boolean[] added = new boolean[nVertices]; 

		// Initialize all distances as 
		// INFINITE and added[] as false 
		for (int vertexIndex = 0; vertexIndex < nVertices; 
											vertexIndex++) 
		{ 
			shortestDistances[vertexIndex] = Integer.MAX_VALUE; 
			added[vertexIndex] = false; 
		} 
		
		// Distance of source vertex from 
		// itself is always 0 
		shortestDistances[startVertex] = 0; 

		// Parent array to store shortest 
		// path tree 
		int[] parents = new int[nVertices]; 

		// The starting vertex does not 
		// have a parent 
		parents[startVertex] = NO_PARENT; 

		// Find shortest path for all 
		// vertices 
		for (int i = 1; i < nVertices; i++)
		{ 

			// Pick the minimum distance vertex 
			// from the set of vertices not yet 
			// processed. nearestVertex is 
			// always equal to startNode in 
			// first iteration. 
			int nearestVertex = -1; 
			int shortestDistance = Integer.MAX_VALUE; 
			for (int vertexIndex = 0; 
					vertexIndex < nVertices; 
					vertexIndex++) 
			{ 
				if (!added[vertexIndex] && 
					shortestDistances[vertexIndex] < 
					shortestDistance) 
				{ 
					nearestVertex = vertexIndex; 
					shortestDistance = shortestDistances[vertexIndex]; 
				} 
			} 

			// Mark the picked vertex as 
			// processed 
			added[nearestVertex] = true; 

			// Update dist value of the 
			// adjacent vertices of the 
			// picked vertex. 
			for (int vertexIndex = 0; 
					vertexIndex < nVertices; 
					vertexIndex++) 
			{ 
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex]; 
				
				if (edgeDistance > 0
					&& ((shortestDistance + edgeDistance) < 
						shortestDistances[vertexIndex])) 
				{ 
					parents[vertexIndex] = nearestVertex; 
					shortestDistances[vertexIndex] = shortestDistance + 
													edgeDistance; 
				} 
			} 
		} 

		printSolution(startVertex, shortestDistances, parents,end); 
	} 

	// A utility function to print 
	// the constructed distances 
	// array and shortest paths 
	private static void printSolution(int startVertex, 
									int[] distances, 
									int[] parents,int end) 
	{ 
		int nVertices = distances.length; 
		//System.out.print("Vertex\t Distance\tPath"); 
		
		for (int vertexIndex = 0; 
				vertexIndex < nVertices; 
				vertexIndex++) 
		{ 
			if (vertexIndex != startVertex) 
			{ 
			//	System.out.print("\n" + startVertex + " -> "); 
			//	System.out.print(vertexIndex + " \t\t "); 
			//	System.out.print(distances[vertexIndex] + "\t\t"); 
			if(end==vertexIndex)
			{
				printPath(vertexIndex, parents,end);
				System.out.println();
			}
			} 
		} 
		
	} 

	// Function to print shortest path 
	// from source to currentVertex 
	// using parents array 
	private static void printPath(int currentVertex, 
								int[] parents,int end) 
	{ 
		
		// Base case : Source node has 
		// been processed 
		if (currentVertex == NO_PARENT) 
		{ 
			return; 
		}
		
		printPath(parents[currentVertex], parents,end);
		System.out.print((currentVertex+1) + " "); 
		
	} 

		// Driver Code 
	
} 
