package shortest_path;
import java.util.Scanner; //install the scanner package for reading input from the screen
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // create a scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // initialize the graph using your custom graphData class and the filepath for the provided CSV file
        GraphData.initializeGraph("C:\\Users\\ztuan\\eclipse-workspace\\shortest_path//Turkishcities.csv");

        //infinite loop, no fixed time complexity
        while (true) { //The true inside the while (true) loop makes it an infinite loop, 
        	//the condition true is always true, so the loop will never stop on its own.
        	//it'll continue running until it is explicitly broken or stopped. e.g the user enters 'quit'
        	
        	System.out.println("Welcome to the Shortest Path Finder!");
        	System.out.println("\nReady to find out the shortest path between cities? Let’s begin!");

        	// Ask the user for the start city
        	System.out.println("\nPlease enter the *start city* (or type 'quit' to exit):");
        	String startCity = scanner.nextLine(); // Read the start city

        	// Check if the user wants to quit
        	if (startCity.equalsIgnoreCase("quit")) {
        	    System.out.println("\nGoodbye! Thank you for using the Shortest Path Finder.");
        	    break; // Exit the loop if 'quit' is typed
        	}

        	// Ask the user for the end city
        	System.out.println("\nNow, enter the *destination city*:");
        	String endCity = scanner.nextLine(); // Read the end city

        	// Ask the user which algorithm to use: BFS or DFS
        	System.out.println("\nLet's choose the algorithm for finding the shortest path:");
        	System.out.println("   1) Standard DFS (Path Exists)");
        	System.out.println("   2) DFS (Depth-First Search)");
            System.out.print("   3) Dijkstra's Algorithm (Guaranteed Shortest Path)");
        	System.out.print(" Please make your choice (1,2 or 3): ");
        	int choice = scanner.nextInt(); // Read the user's choice as an integer
        	scanner.nextLine(); // Consume the newline character after user presses enter

            //crucial since this step prevents the scanner from reading this empty space after other input prompts are made

            // Call the appropriate algorithm based on user input

        	// Declare variables to store timing results
            long startTime, endTime, durationdfs, durationDfs, duration;
            
            if (choice == 1) {
                System.out.println("Using BFS to find the shortest path...\n");
                
                //Calculating empirical time calculations
                startTime = System.nanoTime();
                StandardDFS.findPath(startCity, endCity);
                endTime = System.nanoTime();
                
                durationdfs = (endTime - startTime)/1000000;
                
                System.out.print("\nExecution Time: ");
                System.out.print(durationdfs);	
                System.out.println(" ms.\n*----------------*\n");
            } else if (choice == 2) {
                System.out.println("Using DFS to find the shortest path...\n");
              
                //Calculating empirical time calculations
                startTime = System.nanoTime();
                DFS.findShortestPath(startCity, endCity);  // DFS method to find the shortest path using startcity & endcity as parameters
                endTime = System.nanoTime();
                
                durationDfs = (endTime - startTime)/1000000;
                System.out.print("\nExecution Time: ");
                System.out.print(durationDfs);	
                System.out.println(" ms.\n*----------------*\n");
            } else if (choice == 3) {
                System.out.println("Using Dijkstra's Algorithm to find the shortest path...\n");

                startTime = System.nanoTime();
                Dijkstra.findShortestPath(startCity, endCity);
                endTime = System.nanoTime();

                duration = (endTime - startTime)/1000000;
                System.out.print("\nExecution Time: ");
                System.out.print(duration);
                System.out.println(" ms.\n*----------------*\n");    
            } else {
                System.out.println("Invalid choice. Please choose 1 for BFS or 2 for DFS."); //print the appropriate message if the input is invalid
            }
        }
//now we are out of the while(true) loop, which means the user must have entered 'quit' to exit the program
        // Close the scanner object and display the end message
        scanner.close(); // O(1)
        	System.out.println("Exiting the program. Goodbye!");
    }
}


