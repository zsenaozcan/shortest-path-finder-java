package shortest_path;

public class DFS { // DFS class
    
    // These variables keep the shortest distance and the corresponding path
    private static int shortestDistance = Integer.MAX_VALUE; 
    // Initialized with max value so it can be updated when a shorter path is found
    
    private static CustomStack<String> shortestPath = new CustomStack<>(); 
    // Stack to store the city names of the shortest path

    // This method finds the shortest path between two cities using DFS
    // Overall time complexity is O(n) for the following reasons:
    // - Getting the adjacency matrix and city names is O(1) since they are already prepared
    // - Finding the indices of the start and end cities takes O(n)
    // - Initializing stacks and visited array is linear, O(n)
    // - The DFS method’s complexity is explained separately
    // - Printing the result is O(n) because stacks are reversed and printed
    public static void findShortestPath(String startCity, String endCity) {
        shortestDistance = Integer.MAX_VALUE; 
        shortestPath = new CustomStack<>(); 
        
        // Get the adjacency matrix and city names from GraphData
        int[][] adjacencyMatrix = GraphData.getAdjacencyMatrix(); 
        String[] cityNames = GraphData.getCityNames(); 
        
        // Find the index of the start and end cities
        int startIndex = getCityIndex(startCity, cityNames);
        int endIndex = getCityIndex(endCity, cityNames);

        // If one of the city names is invalid, print an error message
        // City names are case-sensitive
        if (startIndex == -1 || endIndex == -1) {
            System.out.println("Invalid city name(s).");
            return;
        }

        // Stacks used during DFS
        CustomStack<Integer> stack = new CustomStack<>(); 
        // Stores city indices during traversal
        
        CustomStack<String> path = new CustomStack<>(); 
        // Stores city names of the current path
        
        boolean[] visited = new boolean[cityNames.length]; 
        // Keeps track of visited cities

        // Start DFS from the start city
        dfs(adjacencyMatrix, cityNames, stack, path, visited, startIndex, endIndex, 0);

        // If a shortest path is found, print it
        if (!shortestPath.isEmpty()) {
            System.out.println("Shortest path from " + startCity + " to " + endCity + " is:");
            
            // Reverse the stack to print the path in correct order
            CustomStack<String> correctOrderPath = new CustomStack<>();
            while (!shortestPath.isEmpty()) {
                correctOrderPath.push(shortestPath.pop());
            }
            
            // Add the start city to the beginning of the path
            correctOrderPath.push(startCity);
            
            // Print the path step by step
            while (!correctOrderPath.isEmpty()) {
                System.out.print(correctOrderPath.pop() + 
                        (correctOrderPath.size() > 0 ? " -> " : ""));
            }
            
            // Print the total distance of the shortest path
            System.out.println("\nWith a total distance of: " + shortestDistance);
        } else {
            // If no path exists between the cities
            System.out.println("No path found.");
        }
    }

    // Recursive DFS method that explores all possible paths
    // The recursive exploration of neighbors is the most expensive part
    // Average case complexity is around O(n + e) with pruning
    // Pruning improves performance by stopping unnecessary paths early
    // However, in the worst case, DFS may still approach O(n!) time complexity
    private static void dfs(int[][] matrix, String[] cityNames, CustomStack<Integer> stack, 
                            CustomStack<String> path, boolean[] visited, int currentIndex, 
                            int endIndex, int currentDistance) {
        
        // If destination city is reached
        if (currentIndex == endIndex) {
            // Check if this path is shorter than the previously found one
            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                // Save a copy of the current path
                shortestPath = (CustomStack<String>) path.clone();
            }
            // Stop searching deeper from this node
            return;
        }

        // Pruning: if current distance is already worse than the shortest one, stop
        if (currentDistance >= shortestDistance) {
            return;
        }

        // Mark current city as visited
        visited[currentIndex] = true;
        
        // Try all possible neighboring cities
        for (int nextIndex = 0; nextIndex < matrix.length; nextIndex++) {
            // Check connection and whether the city is not visited
            if (matrix[currentIndex][nextIndex] != Integer.MAX_VALUE && !visited[nextIndex]) {
                stack.push(nextIndex); 
                // Add city index to stack
                
                path.push(cityNames[nextIndex]); 
                // Add city name to current path
                
                // Recursive DFS call
                dfs(matrix, cityNames, stack, path, visited, nextIndex, 
                    endIndex, currentDistance + matrix[currentIndex][nextIndex]);
                
                // Backtracking: remove last city from stack and path
                stack.pop(); 
                path.pop(); 
            }
        }
        
        // Mark current city as unvisited for other possible paths
        visited[currentIndex] = false;
    }

    // Returns the index of the given city in the cityNames array
    // Time complexity is O(n) since it may check all elements
    private static int getCityIndex(String city, String[] cityNames) {
        for (int i = 0; i < cityNames.length; i++) {
            if (cityNames[i].equals(city)) {
                return i;
            }
        }
        // Return -1 if the city does not exist
        return -1;
    }
}



