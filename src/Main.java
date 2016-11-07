import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	private static String input;
	private static LinkedList<Character> variables;
	private static HashMap<String, String> expressions;
	private static String[][] truthTable;
	private static final String IDENTIFIER = "EX";

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
		int index = 0;

		while (index < input.length()) {
			char c = input.charAt(index);
			if (Character.isLetter(c)) {
				if (!inList(variables, c)) {
					variables.add(c);
				}
			}
			index++;
		}

		index = 0;
		int index2 = 0;
		LinkedList<Integer> indices = new LinkedList<Integer>();
		expressions = new HashMap<String, String>();

		while (index < input.length()) {
			char c = input.charAt(index);
			if (c == '(') {
				indices.addFirst(index + 1);
			} else if (c == ')') {
				String sub = input.substring(indices.pop(), index);
				if (!inHashMap(expressions, sub)) {
					expressions.put(IDENTIFIER + index2, sub);
					index2++;
				}
			}
			index++;
		}
	}

	private static void evaluate() {
		int index = 0;
		int rowHeight = 1;

		while (index < variables.size()) {
			rowHeight *= 2;
			index++;
		}

		truthTable = new String[1 + rowHeight][variables.size() + expressions.size()];

		index = 0;
		while (index < variables.size()) {
			truthTable[0][index] = String.valueOf(variables.get(index));
			int index2 = 1;
			while (index2 < truthTable.length) {
				if ((index2 - 1) % rowHeight < rowHeight / 2) {
					truthTable[index2][index] = "T";
				} else {
					truthTable[index2][index] = "F";
				}
				index2++;
			}
			index++;
			rowHeight /= 2;
		}

		index = 0;
		while (index < expressions.size()) {
			String key = IDENTIFIER + index;
			truthTable[0][index + variables.size()] = expressions.get(key);
			int index2 = 1;
			while (index2 < truthTable.length) {
				String expression = expressions.get(key);
				truthTable[index2][index + variables.size()] = expression;
				index2++;
			}
			index++;
		}
	}

	private static void printVariables() {
		System.out.println("Output:\nSet of independent variables:");
		Iterator<Character> iterator = variables.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	private static void printExpressions() {
		System.out.println("Set of logical subexpressions and logical expression:");
		Iterator<String> iterator = expressions.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.printf("%s, %s\n", key, expressions.get(key));
		}
	}

	private static void printTruthTable() {
		System.out.println("Truth Table:");
		int index = 0;
		while (index < truthTable.length) {
			int index2 = 0;
			while (index2 < truthTable[index].length) {
				System.out.print(truthTable[index][index2] + "\t");
				index2++;
			}
			System.out.println();
			index++;
		}
	}

	private static boolean inList(LinkedList<Character> list, Character o) {
		Iterator<Character> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == o) {
				return true;
			}
		}
		return false;
	}

	private static boolean inHashMap(HashMap<String, String> map, String s) {
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (map.get(key).equals(s)) {
				return true;
			}
		}
		return false;
	}
}