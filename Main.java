import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CityGraph cityGraph = readCSV("Turkishcities.csv"); // Update the file name

        Scanner scanner = new Scanner(System.in);
         while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Find shortest path using BFS");
            System.out.println("2. Find shortest path using DFS");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    findShortestPath(cityGraph, scanner, true);
                    break;
                case 2:
                    findShortestPath(cityGraph, scanner, false);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 0.");
            }
        }
    }

    private static CityGraph readCSV(String csvFile) {
        CityGraph cityGraph = new CityGraph();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            String[] cities = br.readLine().split(",");

            while ((line = br.readLine()) != null) {
                String[] distances = line.split(",");
                String currentCity = distances[0];
                Map<String, Integer> distancesMap = new HashMap<>();

                for (int i = 1; i < distances.length; i++) {
                    String targetCity = cities[i];
                    int distance = Integer.parseInt(distances[i]);
                    distancesMap.put(targetCity, distance);
                }

                cityGraph.addCity(currentCity, distancesMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityGraph;
    }
    
    private static void findShortestPath(CityGraph cityGraph, Scanner scanner, boolean useBFS) {
        System.out.print("Enter the start city: ");
        String startCity = scanner.next().trim();
        System.out.print("Enter the destination city: ");
        String destinationCity = scanner.next().trim();

        if (useBFS) {
            ShortestPathBFS shortestPathBFS = new ShortestPathBFS(cityGraph);
            shortestPathBFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathBFSResult = shortestPathBFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathBFSResult, "BFS");
        } else {
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(cityGraph);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");
        }
    }

    private static void displayResult(String startCity, String destinationCity, Map<String, Object> result, String algorithm) {
        if (result != null) {
            List<String> path = (List<String>) result.get("path");

            System.out.println("Shortest path from " + startCity + " to " + destinationCity + " using " + algorithm + ":");
            System.out.println("Path: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found between " + startCity + " and " + destinationCity + " using " + algorithm);
        }
    }



    private static void printPath(Map<String, String> path, String startCity, String destinationCity) {
        System.out.print("Path: ");
        List<String> cities = new ArrayList<>();
        String currentCity = destinationCity;

        while (currentCity != null) {
            cities.add(currentCity);
            currentCity = path.get(currentCity);
        }

        for (int i = cities.size() - 1; i >= 0; i--) {
            System.out.print(cities.get(i));
            if (i > 0) {
                System.out.print(" -> ");
            }
        }

        System.out.println();
    }
}

