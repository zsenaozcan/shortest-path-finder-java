package shortest_path;

public class GraphData {
	//These fields are static because they are shared across all across the GraphData class and stay still, ensuring the graph data is consistent.
    private static int[][] adjacencyMatrix; //A 2 dimensional matrix array to store closeness between cities
    private static String[] cityNames;    // Array that stores city names
    

    public static void initializeGraph(String filePath) {
    	// Reads the CSV file using BufferedReader, from the package java.io
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) { 
        	
        	//br.readLine(): reads a line character by character, so complexity is proportional to the length of the line: O(n).
            String line = br.readLine(); // Reads the first row in the file, which contains city names & commas ,
            
            //split(","): splits the line into an array. The complexity depends on the number of elements: O(n).
            String[] headers = line.split(","); // the cities are stored in an array split by commas
            
            cityNames = new String[headers.length - 1]; // the first element in the header row is empty so it excluded
            //array initialization: O(1).
            
//overall complexity for the creating header line is: O(n+m).
// n = number of characters in the header line
// m = number of elements obtained after splitting the header line by commas

            // extracting city names
            for (int i = 1; i < headers.length; i++) { 
                cityNames[i - 1] = headers[i]; //since the first row element was empty, we start extracting cities from headers at index 1
            } //the headers are read by br.readline() and no explicit skipping logic is needed after the first br.readLine() call is done
            
//Now we can skip to the second row of the file
//time complexity in the for loop is the iteration range which is: O(m-1).
//and in each iteration the complexity for cityNames initialization is: O(1)
//so the overall time complexity for extracting city names is: O(m).

            int size = cityNames.length; // actual size of city elements 
//time complexity for initializing size: O(1)
            adjacencyMatrix = new int[size][size]; //2D array for initialising distances between cities
//TC for initializing a nxn matrix is n^2: O(size^2)
            int row = 0; //not the actual row 0 of the file (because that would just have header names) but the row 0 for distances between cities
//initialization: O(1)
            while ((line = br.readLine()) != null) { // While the file still has content, continue reading: 2nd row of the file is starting point
                String[] values = line.split(","); //Elements are split into a values array, based on commas (line by line)
                for (int col = 1; col < values.length; col++) { // Start storing elements from column 1 since thats where the number values start
                    adjacencyMatrix[row][col - 1] = Integer.parseInt(values[col]); // store the distances accurately, e.g adj[0][0]=0 (Ankara-Ankara)
                    //Parse integer method from the integer class converts the string elements into integer numbers
                }
                row++; //After initializing row 0 of adjacency matrix is done, skip to the next row. adj[0]={0,449,9999,153..}
            }
        } catch (Exception e) {
            e.printStackTrace(); // Catches any exceptions that occur during the code.(file reading, parsing etc.)
        }
    }

    // getter methods, both have the same TC: O(1).They simply return the stored arrays.
    public static int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public static String[] getCityNames() {
        return cityNames;
    }
}
