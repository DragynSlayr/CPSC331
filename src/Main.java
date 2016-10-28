import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	private static String num1, num2, product;
	private static LinkedList<String> intermediateStack, finalStack;

	public static void main(String[] args) {
		intermediateStack = new LinkedList<String>();
		finalStack = new LinkedList<String>();
		getInput();
		getIntermediates();
		sumIntermediates();
		printResults();
	}

	private static void getInput() {
		Scanner keyb = new Scanner(System.in);

		System.out.print("String1 = ");
		num1 = keyb.nextLine();

		System.out.print("String2 = ");
		num2 = keyb.nextLine();

		keyb.close();
	}

	private static void getIntermediates() {
		short j = (short) (num2.length() - 1);
		short carry = 0;
		short zeroes = 0;
		String product = "";
		while (j >= 0) {
			short i = (short) (num1.length() - 1);
			while (i >= 0) {
				short a = (short) Character.getNumericValue(num1.charAt(i));
				short b = (short) Character.getNumericValue(num2.charAt(j));
				short c = (short) ((a * b) + carry);
				carry = (short) (c / 10);
				c = (short) (c % 10);
				product = c + product;
				i--;
			}
			if (carry != 0) {
				product = carry + product;
			}
			carry = 0;
			intermediateStack.push(product);
			product = "";
			zeroes++;
			while (product.length() != zeroes) {
				product += "0";
			}
			j--;
		}
	}

	private static void sumIntermediates() {
		product = intermediateStack.pop();
		finalStack.push(product);
		while (!intermediateStack.isEmpty()) {
			String smaller = intermediateStack.pop();
			String temp = padString(smaller, (short) product.length());
			product = sumNumbers(product, temp);
			finalStack.push(smaller);
		}
	}

	private static void printResults() {
		System.out.print("Results:\nProduct: ");
		System.out.println(product);
		System.out.println("Intermediate Calculations:");
		short index = 1;
		while (!finalStack.isEmpty()) {
			System.out.printf("%d) %s\n", index, finalStack.pop());
			index++;
		}
	}

	private static String sumNumbers(String s1, String s2) {
		String sum = "";
		short i = (short) (s1.length() - 1);
		short carry = 0;
		while (i >= 0) {
			short a = (short) Character.getNumericValue(s1.charAt(i));
			short b = (short) Character.getNumericValue(s2.charAt(i));
			short c = (short) (a + b + carry);
			carry = (short) (c / 10);
			c = (short) (c % 10);
			sum = c + sum;
			i--;
		}
		if (carry != 0) {
			sum = carry + sum;
		}
		return sum;
	}

	private static String padString(String s, short length) {
		while (s.length() < length) {
			s = "0" + s;
		}
		return s;
	}
}
