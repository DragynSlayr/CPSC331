package input;

import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner fileReader = new Scanner(new File("src/input/data.txt"));

		while (fileReader.hasNext()) {
			String[] splitted = fileReader.next().split(",");
			System.out.println(splitted[0] + "|" + splitted[1]);
		}

		fileReader.close();
	}
}
