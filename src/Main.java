import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	// Global variables for graph description
	private static String fileName;
	private static int numVertices;

	// Representations of the graph
	private static int[][] adjacencyMatrix;
	private static LinkedList<Integer>[] adjacencyList;

	public static void main(String[] args) {
		// Get file name of graph and the number of vertices from the user
		getInput();

		// Initialize the structures that represent the graph
		initStructures();

		// Read the graph from the file
		readCSVFile();

		// Find and print the most popular vertices
		printMPV();

		// Find and print the least popular vertices
		printLPV();

		// Get the string representation of the structures
		String matrix = matrixToString();
		String list = listToString();

		// Write the structures to a csv file
		writeCSVFile(matrix, "AdjacencyMatrix.csv");
		writeCSVFile(list, "AdjacencyList.csv");
	}

	private static void getInput() {
		// Create a Scanner for input
		Scanner keyb = new Scanner(System.in);

		// Get the number of vertices from the user
		System.out.print("Number of Vertices: ");
		numVertices = Integer.parseInt(keyb.nextLine());

		// Get the name of the input file from the user
		System.out.print("Input CSV File: ");
		fileName = keyb.nextLine();

		// Close the Scanner object
		keyb.close();
	}

	private static void initStructures() {
		// Set the size of the structures
		adjacencyMatrix = new int[numVertices][numVertices];
		adjacencyList = new LinkedList[numVertices];

		// Counter for outer loop
		int i = 0;

		// Loop through rows of matrix
		while (i < adjacencyMatrix.length) {

			// Create an empty list at the index
			adjacencyList[i] = new LinkedList<Integer>();

			// Counter for inner loop
			int j = 0;

			// Loop through columns of each row of the matrix
			while (j < adjacencyMatrix[i].length) {

				// Set current index to default value
				adjacencyMatrix[i][j] = 0;

				// Increment counter
				j++;
			}

			// Increment counter
			i++;
		}
	}

	private static void readCSVFile() {
		try {
			// Create a Scanner for the input file
			Scanner fileReader = new Scanner(new File(fileName));

			// Loop through all lines in the file
			while (fileReader.hasNext()) {

				// Get the next line and split at ','
				String[] vertices = fileReader.next().split(",");

				// Get coordinate from the string array
				int x = Integer.parseInt(vertices[0]);
				int y = Integer.parseInt(vertices[1]);

				// Set indices in the array to 1, indicating a connection
				adjacencyMatrix[x][y] = 1;
				adjacencyMatrix[y][x] = 1;

				// Set connection between vertices in the list
				adjacencyList[x].add(y);
				adjacencyList[y].add(x);
			}

			// Close file scanner
			fileReader.close();

			// Counter for loop
			int i = 0;

			// Loop through array of lists
			while (i < adjacencyList.length) {

				// Sort list at index
				adjacencyList[i] = sortList(adjacencyList[i]);

				// Increment counter
				i++;
			}
		} catch (IOException ioe) {
			// Print error message when a file isn't available
			System.out.println(ioe.getMessage());
		}
	}

	private static LinkedList<Integer> sortList(LinkedList<Integer> in) {
		// Create a new list
		LinkedList<Integer> out = new LinkedList<Integer>();

		// Loop until input list is empty
		while (!in.isEmpty()) {

			// Set index of minimum value
			int min = 0;

			// Counter for loop
			int i = 0;

			// Loop through input list
			while (i < in.size()) {

				// Compare current index to minimum
				if (in.get(i) < in.get(min)) {
					// Set minimum index to current index
					min = i;
				}

				// Increment counter
				i++;
			}

			// Add the element at the minimum index to the output list
			out.add(in.get(min));

			// Remove the element at the minimum index
			in.remove(min);
		}

		// Return output
		return out;
	}

	private static void printMPV() {
		int max = 0;
		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i].size() > max) {
				max = adjacencyList[i].size();
			}
		}

		System.out.printf("Number of neighbors for MPV: %d\n\nMPV, Neighbors\n", max);

		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i].size() == max) {
				System.out.print(i);
				for (int j = 0; j < adjacencyList[i].size(); j++) {
					System.out.print("," + adjacencyList[i].get(j));
				}
				System.out.println();
			}
		}

		System.out.println();
	}

	private static void printLPV() {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i].size() < min) {
				min = adjacencyList[i].size();
			}
		}

		System.out.printf("Number of neighbors for LPV: %d\n\nLPV, Neighbors\n", min);

		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i].size() == min) {
				System.out.print(i);
				for (int j = 0; j < adjacencyList[i].size(); j++) {
					System.out.print("," + adjacencyList[i].get(j));
				}
				System.out.println();
			}
		}

		System.out.println();
	}

	private static String matrixToString() {
		String out = "X";
		for (int i = 0; i < numVertices; i++) {
			out += "," + i;
		}
		out += "\n";
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			out += i;
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				out += "," + adjacencyMatrix[i][j];
			}
			out += "\n";
		}
		return out.substring(0, out.length() - 1);
	}

	// TODO: Check this section
	// Do listToString
	private static String listToString() {

		// Create String for out
		String out = "";

		// Loop until i < adjacencyList.length
		for (int i = 0; i < adjacencyList.length; i++) {

			// Append i to out
			out += i;

			// Loop until j < adjacencyList[i].size()
			for (int j = 0; j < adjacencyList[i].size(); j++) {

				// Append ", to out
				out += "," + adjacencyList[i].get(j);
			}

			// Append "\n" to out
			out += "\n";
		}

		// TODO: comment
		return out.substring(0, out.length() - 1);
	}

	private static void writeCSVFile(String out, String filePath) {
		try {
			// Create a stream for writing to the output file
			PrintStream fileWriter = new PrintStream(new File(filePath));

			// Write the output to the stream
			fileWriter.print(out);

			// Close the stream
			fileWriter.close();
		} catch (IOException ioe) {
			// Print error message when a file isn't available
			System.out.println(ioe.getMessage());
		}
	}
}
