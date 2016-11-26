import java.io.File;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	private static String fileName;
	private static int numVertices;
	private static int[][] adjacencyMatrix;
	private static LinkedList<Integer>[] adjacencyList;

	public static void main(String[] args) {
		getInput();
		initStructures();
		readCSVFile();
		printMPV();
		printLPV();
		writeCSVFile(matrixAsString(), "AdjacencyMatrix.csv");
		writeCSVFile(listAsString(), "AdjacencyList.csv");
	}

	private static void getInput() {
		Scanner keyb = new Scanner(System.in);

		System.out.print("Number of Vertices: ");
		numVertices = Integer.parseInt(keyb.nextLine());

		System.out.print("Input CSV File: ");
		fileName = keyb.nextLine();

		keyb.close();
	}

	private static void initStructures() {
		adjacencyMatrix = new int[numVertices][numVertices];
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				adjacencyMatrix[i][j] = 0;
			}
		}

		adjacencyList = new LinkedList[numVertices];
		for (int i = 0; i < numVertices; i++) {
			adjacencyList[i] = new LinkedList<Integer>();
		}
	}

	private static void readCSVFile() {
		try {
			Scanner fileReader = new Scanner(new File(fileName));

			while (fileReader.hasNext()) {
				String[] vertices = fileReader.next().split(",");

				int x = Integer.parseInt(vertices[0]);
				int y = Integer.parseInt(vertices[1]);

				adjacencyMatrix[x][y] = 1;
				adjacencyMatrix[y][x] = 1;

				adjacencyList[x].add(y);
				adjacencyList[y].add(x);
			}

			for (int i = 0; i < numVertices; i++) {
				adjacencyList[i].sort(new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});
			}

			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private static String matrixAsString() {
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

	private static String listAsString() {
		String out = "";
		for (int i = 0; i < adjacencyList.length; i++) {
			out += i;
			for (int j = 0; j < adjacencyList[i].size(); j++) {
				out += "," + adjacencyList[i].get(j);
			}
			out += "\n";
		}
		return out.substring(0, out.length() - 1);
	}

	private static void writeCSVFile(String out, String filePath) {
		try {
			PrintStream fileWriter = new PrintStream(new File(filePath));
			fileWriter.print(out);
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
