package shortest_path;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class MainApp {
    private JFrame frame;
    private JComboBox<String> startCityComboBox;
    private JComboBox<String> endCityComboBox;
    private JTextArea resultArea;
    private String[] cityNames;

    public MainApp() {
        try {
            // Reading the csv 
            GraphData.initializeGraph("src/TurkishCities.csv");
            
            //Get the city names 
            cityNames = GraphData.getCityNames();
            
            //if there is no city names throw an exception
            if (cityNames == null || cityNames.length == 0) {
                throw new IOException("City names could not be loaded from GraphData.");
            }
            
            // Sorting the city names for easier reading
            Arrays.sort(cityNames);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\n"
                    + "Error: Data file not loaded. The program is terminating. Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        initialize();
    }

    private void initialize() {
        
        // app interface 
        frame = new JFrame("Shortest Path Finder (COMP 201)"); //frame name
        frame.setBounds(100, 100, 750, 600); //setting the boundaries. 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the tab, the app will close entirely 
        frame.getContentPane().setLayout(new BorderLayout()); 
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        //placing the control panel to the center
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        controlPanel.setBackground(new Color(220, 235, 250)); 
        frame.getContentPane().add(controlPanel, BorderLayout.NORTH); 

        // choosing the target and starting city
        controlPanel.add(new JLabel("Start City:"));
        startCityComboBox = new JComboBox<>(cityNames);
        controlPanel.add(startCityComboBox);

        controlPanel.add(new JLabel("Target City:"));
        endCityComboBox = new JComboBox<>(cityNames);
        controlPanel.add(endCityComboBox);

        JButton findPathButton = new JButton("Run Dijkstra & DFS Compare");
        controlPanel.add(findPathButton);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findPathAndMeasureTime();
            }
        });
    }
    
    
    private void findPathAndMeasureTime() {
        String startCity = (String) startCityComboBox.getSelectedItem();
        String endCity = (String) endCityComboBox.getSelectedItem();
        
        if (startCity == null || endCity == null || startCity.equals(endCity)) {
            JOptionPane.showMessageDialog(frame, "Please select different and valid cities.");
            return;
        }

        resultArea.setText(""); // clean the last output
        
        // checkpoint 
        java.io.PrintStream originalOut = System.out;
        
        // {Algorithm Name, Time (nanos), Output}
        // Size reduced to 2 for Dijkstra and DFS
        String[][] results = new String[2][3]; 
        
        String[] algorithmNames = {"Dijkstra", "Modified DFS"};
        
        for (int i = 0; i < algorithmNames.length; i++) {
            String algoName = algorithmNames[i];
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            
            long startTime = 0;
            long durationNanos = 0;
            String outputText;

            try {
                System.setOut(new PrintStream(outputStream)); // Start capturing output
                
                startTime = System.nanoTime();
                
                if (algoName.equals("Dijkstra")) {
                    Dijkstra.findShortestPath(startCity, endCity);
                } else if (algoName.equals("Modified DFS")) {
                    DFS.findShortestPath(startCity, endCity);
                }
                
                durationNanos = System.nanoTime() - startTime;
                outputText = outputStream.toString();
                
            } catch (Exception ex) {
                durationNanos = -1; // Mark as error state
                outputText = "!!! ALGORITHM ERROR: " + ex.getMessage();
                ex.printStackTrace(new PrintStream(outputStream));
            } finally {
                System.setOut(originalOut); // Restore console to normal
            }

            // Save the results
            results[i][0] = algoName;
            results[i][1] = String.valueOf(durationNanos);
            results[i][2] = outputText;
        }

        // --- Printing the Outputs ---
        
        resultArea.append("========================================================\n");
        resultArea.append("   RESULTS: " + startCity + " -> " + endCity + "\n");
        resultArea.append("========================================================\n\n");
        
        long dijkstraTimeNanos = Long.parseLong(results[0][1]); // Dijkstra (0)
        long dfsTimeNanos = Long.parseLong(results[1][1]); // Modified DFS (1)

        for (String[] result : results) {
            String algoName = result[0];
            long duration = Long.parseLong(result[1]);
            String output = result[2];
            double durationMicros = duration / 1000.0;
            
            resultArea.append("--------------------------------------------------------\n");
            resultArea.append("Algorithm: " + algoName + "\n");
            resultArea.append("--------------------------------------------------------\n");
            
            if (duration < 0) {
                 resultArea.append("ERROR: The algorithm could not be executed.\n"+ "\n" + output);
            } else {
                 resultArea.append(output);
                 resultArea.append(String.format(">> Time: %.3f µs (microseconds)\n", durationMicros));
            }
            resultArea.append("\n");
        }
        
        // --- Performans Karşılaştırması ---
        resultArea.append("===================== Comparing ====================\n");
        if (dijkstraTimeNanos > 0 && dfsTimeNanos > 0) {
            resultArea.append(String.format("Dijkstra (%.3f µs) vs. Modified DFS (%.3f µs)\n", dijkstraTimeNanos / 1000.0, dfsTimeNanos / 1000.0));
            
            if (dijkstraTimeNanos < dfsTimeNanos) {
                resultArea.append(String.format("Dijkstra is %.2f times faster than DFS. (EXPECTED RESULT)\n", (double)dfsTimeNanos / dijkstraTimeNanos));
            } else {
                resultArea.append(String.format("DFS was %.2f times faster than Dijkstra. (SMALL DATASET EFFECT)\n", (double)dfsTimeNanos / dijkstraTimeNanos));
            }
        }
        resultArea.append("========================================================");
    }

    public void show() {
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApp().show();
            }
        });
    }
}
