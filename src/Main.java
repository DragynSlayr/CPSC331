import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	private static String input;
	private static LinkedList<Character> variables;
	private static HashMap<String, String> expressions;
	private static String[][] truthTable;

	public static void main(String[] args) {
		getInput();
		parse();
		evaluate();

		printVariables();
		printExpressions();
		printTruthTable();
	}

	private static void getInput() {
		Scanner keyb = new Scanner(System.in);
		System.out.print("Input string: ");
		input = keyb.nextLine().toUpperCase();
		keyb.close();
	}

	private static void parse() {
		variables = new LinkedList<Character>();
		short index = 0;

		while (index < input.length()) {
			char c = input.charAt(index);
			if (c > 64 && c < 91) {
				if (!inList(variables, c)) {
					variables.add(c);
				}
			}
			index++;
		}

		index = 0;
		short index2 = 0;
		LinkedList<Short> indices = new LinkedList<Short>();
		expressions = new HashMap<String, String>();

		while (index < input.length()) {
			char c = input.charAt(index);
			if (c == '(') {
				indices.addFirst((short) (index + 1));
			} else if (c == ')') {
				String sub = input.substring(indices.peek(), index);
				if (!inHashMap(expressions, sub)) {
					expressions.put("EQ0" + index2, sub);
					indices.pop();
					index2++;
				}
			}
			index++;
		}
	}

	private static void evaluate() {

	}

	private static void printVariables() {
		System.out.println("Output:\nSet of independent variables:");
		while (!variables.isEmpty()) {
			System.out.println(variables.poll());
		}
	}

	private static void printExpressions() {
		System.out.println("Set of logical subexpressions and logical expression:");
		for (String k : expressions.keySet()) {
			System.out.printf("%s, %s\n", k, expressions.get(k));
		}
	}

	private static void printTruthTable() {
	}

	private static boolean inList(LinkedList<Character> l, Character o) {
		short index = 0;
		while (index < l.size()) {
			if (l.get(index) == o) {
				return true;
			}
			index++;
		}
		return false;
	}

	private static boolean inHashMap(HashMap<String, String> h, String s) {
		for (String k : h.keySet()) {
			if (h.get(k).equals(s)) {
				return true;
			}
		}
		return false;
	}
}
