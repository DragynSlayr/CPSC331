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

	/*
	 * Gets number of vertices and name of input file from user
	 * 
	 * Pre-Condition:
	 * ~ numVertices, fileName have been declared
	 * 
	 * Post-Condition:
	 * ~ numVertices, fileName have the user's values
	 */
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

	/*
	 * Sizes and resets adjacency matrix and list
	 * 
	 * Pre-Condition:
	 * ~ adjacencyMatrix, adjacencyList have been declared but not initialized
	 * ~ numVertices has an integer value
	 * 
	 * Post-Condition:
	 * ~ adjacencyMatrix is numVertices tall and wide
	 * ~ adjacencyList has length of numVertices
	 * ~ adjacencyMatrix is filled with zeroes
	 * ~ adjacencyList is filled with empty LinkedLists
	 * ~ numVertices has not changed
	 */
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

	/*
	 * Reads values from a CSV file
	 * 
	 * Pre-Condition:
	 * ~ fileName has a value
	 * ~ adjacencyList, adjacencyMatrix have been initialized
	 * 
	 * Post-Condition:
	 * ~ fileName has not changed
	 * ~ adjacencyMatrix, adjacencyList describe the relations from the CSV file at fileName
	 */
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

	/*
	 * Sorts a LinkedList of Integers
	 * 
	 * Pre-Condition:
	 * ~ in is a LinkedList of Integers
	 * 
	 * Post-Condition:
	 * ~ a sorted version of in is returned
	 */
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

	/*
	 * Finds and prints the most popular vertices
	 * 
	 * Pre-Condition:
	 * ~ adjacencyList has values
	 * 
	 * Post-Condition:
	 * ~ most popular vertices have been found and printed
	 * ~ adjacencyList has not been changed
	 */
	private static void printMPV() {
		// Set starting max to low value, every value is greater or equal to
		// this
		int max = 0;

		// Counter for loop
		int i = 0;

		// Loop through array
		while (i < adjacencyList.length) {

			// Compare length of current list to max
			if (adjacencyList[i].size() > max) {
				// Update max
				max = adjacencyList[i].size();
			}

			// Increment counter
			i++;
		}

		// Print description
		System.out.printf("Number of neighbors for MPV: %d\n\nMPV, Neighbors\n", max);

		// Reset counter
		i = 0;

		// Loop through array
		while (i < adjacencyList.length) {

			// Compare list size to biggest list
			if (adjacencyList[i].size() == max) {

				// Print current outer index
				System.out.print(i);

				// Counter for inner loop
				int j = 0;

				// Traverse list
				while (j < adjacencyList[i].size()) {
					// Print current element
					System.out.print("," + adjacencyList[i].get(j));

					// Increment counter
					j++;
				}

				// Print new line
				System.out.println();
			}

			// Increment counter
			i++;
		}

		// Print new line
		System.out.println();
	}

	/*
	 * Finds and prints the least popular vertices
	 * 
	 * Pre-Condition:
	 * ~ adjacencyList has values
	 * 
	 * Post-Condition:
	 * ~ least popular vertices have been found and printed
	 * ~ adjacencyList has not been changed
	 */
	private static void printLPV() {
		// Set starting max to high value, every value is less than or equal to
		// this
		int min = Integer.MAX_VALUE;

		// Counter for loop
		int i = 0;

		// Loop through array
		while (i < adjacencyList.length) {

			// Compare length of current list to min
			if (adjacencyList[i].size() < min) {
				// Update min
				min = adjacencyList[i].size();
			}

			// Increment counter
			i++;
		}

		// Print description
		System.out.printf("Number of neighbors for LPV: %d\n\nLPV, Neighbors\n", min);

		// Reset counter
		i = 0;

		// Loop through array
		while (i < adjacencyList.length) {

			// Compare list size to smallest list
			if (adjacencyList[i].size() == min) {

				// Print current outer index
				System.out.print(i);

				// Counter for inner loop
				int j = 0;

				// Traverse list
				while (j < adjacencyList[i].size()) {
					
					// Print current element
					System.out.print("," + adjacencyList[i].get(j));

					// Increment counter
					j++;
				}

				// Print new line
				System.out.println();
			}

			// Increment counter
			i++;
		}

		// Print new line
		System.out.println();
	}

	/*
	 * Gets a CSV String representation of the adjacency matrix
	 * 
	 * Pre-Condition:
	 * ~ numVerices, adjacencyMatrix have values
	 * 
	 * Post-Condition:
	 * ~ numVertices, adjacencyMatrix have not been changed
	 * ~ a String representation of adjacencyMatrix has been returned
	 */
	private static String matrixToString() {
		// String for output
		String out = "X";

		// Counter for loop
		int i = 0;

		// Loop through vertices
		while (i < numVertices) {
			
			// Append current vertex to out
			out += "," + i;

			// Increment counter
			i++;
		}

		// Append line to out
		out += "\n";

		// Reset counter
		i = 0;

		// Loop through rows of matrix
		while (i < adjacencyMatrix.length) {

			// Append current index to out
			out += i;

			// Counter for inner loop
			int j = 0;

			// Loop through columns of each row
			while (j < adjacencyMatrix[i].length) {
				
				// Append current element to out
				out += "," + adjacencyMatrix[i][j];

				// Increment counter
				j++;
			}

			// Append new line
			out += "\n";

			// Increment counter
			i++;
		}

		// Return output without trailing '\n'
		return out.substring(0, out.length() - 1);
	}

	/*
	 * Gets a CSV String representation of the adjacency list
	 * 
	 * Pre-Condition:
	 * ~ adjacencyList has values
	 * 
	 * Post-Condition:
	 * ~ adjacencyList has not been changed
	 * ~ a String representation of adjacencyList has been returned
	 */
	private static String listToString() {
		// String for output
		String out = "";

		// Counter for outer loop
		int i = 0;

		// Loop through array
		while (i < adjacencyList.length) {

			// Append i to out
			out += i;

			// Counter for inner loop
			int j = 0;

			// Loop through each list
			while (j < adjacencyList[i].size()) {

				// Append current element to out
				out += "," + adjacencyList[i].get(j);

				// Increment counter
				j++;
			}

			// Append line break to out
			out += "\n";

			// Increment counter
			i++;
		}

		// Return the output string without the trailing '\n'
		return out.substring(0, out.length() - 1);
	}

	/*
	 * Writes a String to a file
	 * 
	 * Pre-Condition:
	 * ~ out, filePath are Strings
	 * 
	 * Post-Condition:
	 * ~ out, filePath have not been modified
	 * ~ out has been written to the file at filePath
	 */
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
