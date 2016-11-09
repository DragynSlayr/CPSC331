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

	private static int rowHeight;

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
				if (indexOf(variables, c) == -1) {
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
				if (indexOf(expressions, sub) == -1) {
					expressions.put(IDENTIFIER + index2, sub);
					index2++;
				}
			}
			index++;
		}
	}

	private static void evaluate() {
		int index = 0;
		rowHeight = 1;

		while (index < variables.size()) {
			rowHeight *= 2;
			index++;
		}

		truthTable = new String[1 + rowHeight][variables.size() + expressions.size()];

		index = 0;
		while (index < variables.size()) {
			truthTable[0][index] = String.valueOf(variables.get(index));
			index++;
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

		index = 0;
		while (index < truthTable[0].length) {
			String value = getTruthValues(truthTable[0][index]);
			int index2 = 1;
			while (index2 < truthTable.length) {
				System.out.println("Value: " + value);
				truthTable[index2][index] = String.valueOf(value.charAt(index2 - 1));
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
		int index = 0;
		int size = expressions.size();
		while (index < size) {
			String key = IDENTIFIER + index;
			System.out.printf("%s, %s\n", key, expressions.get(key));
			index++;
		}
	}

	private static void printTruthTable() {
		System.out.println("Truth Table:");
		int index = 0;
		while (index < truthTable.length) {
			int index2 = 0;
			while (index2 < truthTable[index].length) {
				if (index == 0) {
					System.out.print(truthTable[index][index2] + "\t");
				} else {
					char[] pad = new char[truthTable[0][index2].length() / 2];
					System.out.print(new String(pad).replace('\0', ' ') + truthTable[index][index2] + "\t");
				}
				index2++;
			}
			System.out.println();
			index++;
		}
	}

	private static int indexOf(LinkedList<Character> list, Character o) {
		int index = -1;
		boolean found = false;
		Iterator<Character> iterator = list.iterator();
		while (iterator.hasNext() && !found) {
			if (iterator.next() == o) {
				found = true;
			}
			index++;
		}
		if (found) {
			return index;
		} else {
			return -1;
		}
	}

	private static int indexOf(HashMap<String, String> map, String s) {
		int index = -1;
		boolean found = false;
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext() && !found) {
			String key = iterator.next();
			if (map.get(key).equals(s)) {
				found = true;
			}
			index++;
		}
		if (found) {
			return index;
		} else {
			return -1;
		}
	}

	private static String getTruthValues(String expression) {
		String values = "";
		if (expression.length() > 1) {
			int index = indexOf(expressions, expression);
			System.out.printf("%s, %d\n", expression, index);
			if (expression.contains(")")) {
				String[] splitted = expression.split("\\)");
				values = "TTFFTTFFTTFFTTFF";
			} else {
				if (expression.indexOf('+') > -1) {
					int idx = expression.indexOf('+');
					String a = expression.substring(0, idx);
					String b = expression.substring(idx + 1);
					values = or(getTruthValues(a), getTruthValues(b));
				}
			}
		} else {
			int split = power(2, indexOf(variables, expression.charAt(0)) + 1);
			int index = 0;
			while (index < rowHeight) {
				int a = index % ((2 * rowHeight) / split);
				int b = rowHeight / split;
				if (a < b) {
					values += "T";
				} else {
					values += "F";
				}
				index++;
			}
		}
		return values;
	}

	private static String negate(String in) {
		String result = "";
		int index = 0;
		while (index < in.length()) {
			char c = in.charAt(index);
			if (c == 'T') {
				result += "F";
			} else {
				result += "T";
			}
			index++;
		}
		return result;
	}

	private static String or(String s1, String s2) {
		String result = "";
		int index = 0;
		while (index < s1.length()) {
			char a = s1.charAt(index);
			char b = s2.charAt(index);
			if (a == 'T' || b == 'T') {
				result += "T";
			} else {
				result += "F";
			}
			index++;
		}
		return result;
	}

	private static String and(String s1, String s2) {
		String result = "";
		int index = 0;
		while (index < s1.length()) {
			char a = s1.charAt(index);
			char b = s2.charAt(index);
			if (a == b && a == 'T') {
				result += "T";
			} else {
				result += "F";
			}
			index++;
		}
		return result;
	}

	private static int power(int a, int b) {
		if (a == 0) {
			return 0;
		}
		if (b == 0) {
			return 1;
		}
		int result = a;
		int index = 1;
		while (index < b) {
			result *= a;
			index++;
		}
		return result;
	}
}