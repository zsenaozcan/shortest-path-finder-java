package shortest_path;

// A simple helper class used in Dijkstra's algorithm.
 // It stores a city index and the current shortest-known distance to that city.
 // PriorityQueue uses this class to decide which city to process next.
class NodeDistance implements Comparable<NodeDistance> {

    // The index of the city in the graph
    public int cityIndex;

    // The shortest distance found so far to this city
    public int distance;

  
      //Constructor that creates a NodeDistance object with a given city index and distance.
     
    public NodeDistance(int cityIndex, int distance) {
        this.cityIndex = cityIndex;
        this.distance = distance;
    }

     //This method allows NodeDistance objects to be compared based on their distance.
     // The PriorityQueue uses this to always pick the city with the smallest distance first.
     
    @Override
    public int compareTo(NodeDistance other) {
        return Integer.compare(this.distance, other.distance);
    }
}

