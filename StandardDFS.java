package shortest_path;

public class StandardDFS {
    private static boolean pathFound = false; // Stop DFS once a path is found
    private static CustomStack<String> finalPath; // Use CustomStack that stores the final path with strings once reached 

    public static void findPath(String startCity, String endCity) { // Finding the path method from start to end
        // Reset global variables for each new search
        pathFound = false;
        finalPath = new CustomStack<>();
        
        // Create dimensional arrays using GraphData class
        int[][] adjacencyMatrix = GraphData.getAdjacencyMatrix();
        String[] cityNames = GraphData.getCityNames();
        
        // Find indices of start and end(destination) cities with get method
        int startIndex = getCityIndex(startCity, cityNames);
        int endIndex = getCityIndex(endCity, cityNames);
        // Time Complexity: O(n)

        if (startIndex == -1 || endIndex == -1) { // Input validation
            System.out.println("Invalid city name or names. Please try again.");
            return;
        }
        CustomStack<String> currentPath = new CustomStack<>(); // Stack that keeps track of the curernt DFS path (O(1))
        currentPath.push(startCity); // Push start city as first node of the stack (O(1))
        boolean[] visited = new boolean[cityNames.length]; // Assign a visited array to avoid revisiting cities (O(n))
        
        // Start recursive DFS traversal 
        dfsRecursive(adjacencyMatrix, cityNames, currentPath, visited, startIndex, endIndex);

        if (pathFound) { 
            System.out.println("Path found with Standard DFS:"); // If a path is found, print it on the screen

            // Reverse the final path to print it in the correct order
            CustomStack<String> reversedPath = new CustomStack<>();
            while (!finalPath.isEmpty()) { 
                reversedPath.push(finalPath.pop()); // O(n)
            }
            // Overall Time Complexity: O(k) k: number of cities along the path
            
            //Print out the path found
            while (!reversedPath.isEmpty()) {
                System.out.print(reversedPath.pop() + (reversedPath.size() > 0 ? " -> " : ""));
            }
            System.out.println();

            } else {
            System.out.println("Path couldn't be found with Standard DFS."); // Print no path found between the inputs
        }
        }
        private static void dfsRecursive(int[][] matrix, String[] cityNames, CustomStack<String> currentPath, boolean[] visited, int currentIndex, int endIndex) {
            if (pathFound) { // Stop recursion if a path is found
            return;
        }
        visited[currentIndex] = true; // Mark current city index as visited
        if (currentIndex == endIndex) { // If destination reached 
            pathFound = true;
            finalPath = (CustomStack<String>) currentPath.clone(); // Clone current path to preserve the found result
            return;
        }

        for (int nextIndex = 0; nextIndex < matrix.length; nextIndex++) { // Iterate through all neighbour cities
            int weight = matrix[currentIndex][nextIndex];
            if (weight != Integer.MAX_VALUE && weight != 0 && !visited[nextIndex]) { 
                // Check if there is a valid edge and the city is not visited
                currentPath.push(cityNames[nextIndex]); // Add the next city that is found to the current path
                // Recursive DFS call
                dfsRecursive(matrix, cityNames, currentPath, visited, nextIndex, endIndex);

            if (pathFound) { // If a path is found, stop executing recursion
                return;
            }
            currentPath.pop(); // Backtracking, remove the city from the path
        }
    }
    visited[currentIndex] = false; //  Allow the current index city to be used in alternative paths, inputs

        }
        private static int getCityIndex(String city, String[] cityNames) { 
            // get method to find the city index in the cityNames array
        if (city == null) return -1;
            // Time Complexity: O(n)
        
        for (int i = 0; i < cityNames.length; i++) { // Case-insensitive comparison for user convenience
            if (cityNames[i].equalsIgnoreCase(city)) {
                return i;
            }
        }
        return -1; // City couldn't be found
    }
}

        
