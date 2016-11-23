package array;
import java.util.Random;

public class ArrayPractice {

	public static void main(String[] args) {
		Random r = new Random();
		int[][] ia = new int[4][4];
		for (int i = 0; i < ia.length; i++) {
			for (int j = 0; j < ia[i].length; j++) {
				ia[i][j] = r.nextInt(10);
			}
		}
		printArray(ia);
		System.out.println();
		boolean changed = findAndReplace(ia, 3, 0);
		changed = findAndReplace(ia, 4, 0);
		if (changed) {
			printArray(ia);
		} else {
			System.out.println("No changes");
		}
	}

	private static void printArray(int[][] ia) {
		for (int i = 0; i < ia.length; i++) {
			for (int j = 0; j < ia[i].length; j++) {
				if (j == 0) {
					System.out.print("[ ");
					System.out.printf("%d ", ia[i][j]);
				} else if (j == ia[i].length - 1) {
					System.out.printf("%d ", ia[i][j]);
					System.out.print("]");
				} else {
					System.out.printf("%d ", ia[i][j]);
				}
			}
			System.out.println();
		}
	}
	
	private static boolean findAndReplace(int[][] ia, int value, int newValue) {
		boolean changed = false;
		for (int i = 0; i < ia.length; i++) {
			for (int j = 0; j < ia[i].length; j++) {
				if (ia[i][j] == value) {
					ia[i][j] = newValue;
					changed = true;
				}
			}
		}
		return changed;
	}
}
