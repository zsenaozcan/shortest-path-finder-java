package shortest_path;
import java.util.PriorityQueue;

public class Dijkstra {
        // A constant representing unreachable paths or no connection between cities
    private static final int NO_PATH = 99999;
    // Main method that computes the shortest path between two cities using Dijkstra's algorithm.
    // It retrieves city indices, initializes arrays, applies the algorithm, and prints results.
    
    public static void findShortestPath(String startCity, String endCity) {
        
  // Retrieve adjacency matrix and city names from GraphData
        int[][] adjacencyMatrix = GraphData.getAdjacencyMatrix();
        String[] cityNames = GraphData.getCityNames();
        int n = cityNames.length;
        // Convert city names to their corresponding indices

        int start = getCityIndex(startCity, cityNames);
        int end = getCityIndex(endCity, cityNames);
              
        // Check if cities exist in dataset
        if (start == -1 || end == -1) {
            System.out.println("City name does not exist. Please enter another city name: ");
            return;
        }
       // distances[] holds the shortest known distance from start city to each city
        int[] distances = new int[n];
      // prev[] holds the predecessor city used to reconstruct the final path
        int[] prev = new int[n];
        // Initialize arrays
        for (int i = 0; i<n; i++ ) {
            distances[i] = NO_PATH; // initially all distances are infinite-like
            prev[i] = -1;           // -1 means no predecessor assigned yet
        }
        distances[start] = 0;// distance to the start city is always 0

        // Priority queue used for extracting the city with the smallest tentative distance
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        pq.add(new NodeDistance(start, 0));
        
        /**
         * -----------------------------
         *   MAIN DIJKSTRA ALGORITHM
         * -----------------------------
         */
        
        while (!pq.isEmpty()) {
        // Extract node with smallest current distance
            NodeDistance current = pq.poll(); // O(logV)
            int u = current.cityIndex;
            int dist_u = current.distance;
        // Skip if this is an outdated (non-optimal) entry
            if(dist_u > distances[u]) {
                continue;
            }
             // Stop early if destination is reached
            if (u == end) break;

            // Check all neighbors of current city
            for (int v = 0; v < n; v++) {
                int weight = adjacencyMatrix[u][v];
            // Only process valid and existing edges
                if (weight != NO_PATH && weight != 0) {

                    // Relaxation step: attempt to improve the shortest known distance
                    int newDist = distances[u] + weight;
                    if (newDist < distances[v]) {
                       
                        
                         distances[v] = newDist; // update with new shortest distance
                        prev[v] = u;             // record predecessor for path reconstruction

                        // Add updated distance to the queue
                        pq.add(new NodeDistance(v, newDist));
                    }
                }
            }
        }
         // If destination is still unreachable
        if (distances[end] == NO_PATH) {
            System.out.println("Path couldn't be found with Dijkstra from " + startCity + " to " + endCity);
            return;
        }

        /**
         * -------------------------------------
         *   PATH RECONSTRUCTION USING prev[]
         * -------------------------------------
         * Starting from the destination city, move backwards using prev[] until the start is reached.
         * Cities are pushed into a stack so they can be printed in forward order.
         */

        CustomStack<String> path = new CustomStack<>();
        for (int currentIndex=end; currentIndex!=-1; currentIndex=prev[currentIndex]) {
            path.push(cityNames[currentIndex]);
   
        }

         // Print results
        System.out.println("Dijkstra's Algorithm (Guaranteed Shortest Path)");
        System.out.println("Shortest path from " + startCity + " to " + endCity);

        // Print the first city
        System.out.println(path.pop());

        // Print rest of the path
        while (!path.isEmpty()) {
            System.out.print(" -> " + path.pop());
        }

        
        System.out.println();
        System.out.println("Total distance: " + distances[end]);
    }
     //Helper method that returns the index of a city name in the cityNames array.
     // If the city is not found, returns -1.
     

    private static int getCityIndex(String city, String[] cityNames) {
        for (int i=0; i<cityNames.length; i++) {
            if(cityNames[i].equals(city)) {
                return i;
            }
        }
        return -1;
    }
}

