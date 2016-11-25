import java.io.File;
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
		printMatrix();
		printList();
	}

	private static void getInput() {
		Scanner keyb = new Scanner(System.in);

		System.out.print("Number of Vertices: ");
		numVertices = Integer.parseInt(keyb.nextLine()) + 1;

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

	private static void printMatrix() {
		System.out.println();
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
		System.out.println(out);
	}

	private static void printList() {
		System.out.println();
		String out = "";
		for (int i = 0; i < numVertices; i++) {
			out += i;
			for (int j = 0; j < adjacencyList[i].size(); j++) {
				out += "," + adjacencyList[i].get(j);
			}
			out += "\n";
		}
		System.out.println(out);
	}
}
