import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	private static String input;
	private static LinkedList<Character> variables;
	private static HashMap<String, String> expressions;
	private static String[][] truthTable;
	private static final String IDENTIFIER = "LE";

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
		expressions = new HashMap<String, String>();
		Stack<Integer> indices = new Stack<Integer>();

		int index = 0;
		int index2 = 0;

		while (index < input.length()) {
			char c = input.charAt(index);
			if (Character.isLetter(c)) {
				if (indexOf(variables, c) == -1) {
					variables.add(c);
				}
			} else if (c == '(') {
				indices.push(index + 1);
			} else if (c == ')') {
				String sub = input.substring(indices.pop(), index);
				if (indexOf(expressions, sub) == -1) {
					expressions.put(IDENTIFIER + index2, sub);
					index2++;
				}
			}
			index++;
		}

		rowHeight = power(2, variables.size());
	}

	private static void evaluate() {
		truthTable = new String[1 + rowHeight][variables.size() + expressions.size()];

		int index = 0;
		while (index < truthTable[0].length) {
			String key = "";
			String expression = "";
			if (index < variables.size()) {
				key = String.valueOf(variables.get(index));
				expression = key;
			} else {
				key = IDENTIFIER + (index - variables.size());
				expression = expressions.get(key);
			}
			truthTable[0][index] = key;
			String value = getTruthValues(expression);
			int index2 = 1;
			while (index2 < truthTable.length) {
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
					String padding = new String(pad).replace('\0', ' ');
					System.out.print(padding + truthTable[index][index2] + "\t");
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
			if (map.get(iterator.next()).equals(s)) {
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
		if (expression.length() == 1) {
			return getTruthColumn(expression.charAt(0));
		}
		Stack<String> stack = new Stack<String>();
		String prefix = toPrefix(expression);
		int index = prefix.length() - 1;
		while (index > -1) {
			char c = prefix.charAt(index);
			if (Character.isLetter(c)) {
				stack.push(getTruthColumn(c));
			} else {
				if (c == '-') {
					stack.push(negate(stack.pop()));
				} else if (c == '+') {
					stack.push(or(stack.pop(), stack.pop()));
				} else if (c == '*') {
					stack.push(and(stack.pop(), stack.pop()));
				}
			}
			index--;
		}
		return stack.pop();
	}

	private static String getTruthColumn(char c) {
		String values = "";
		int split = rowHeight / power(2, indexOf(variables, c) + 1);
		int index = 0;
		int counter = 0;
		boolean isTrue = true;
		while (index < rowHeight) {
			if (counter == split) {
				counter = 0;
				isTrue = !isTrue;
			}
			if (isTrue) {
				values += 'T';
			} else {
				values += 'F';
			}
			counter++;
			index++;
		}
		return values;
	}

	private static String toPrefix(String in) {
		String out = "";
		Stack<Character> stack = new Stack<Character>();
		for (int i = in.length() - 1; i > -1; i--) {
			char c = in.charAt(i);
			if (Character.isLetter(c)) {
				out = c + out;
			} else if (c == '(') {
				out = stack.pop() + out;
			} else if (c != ')') {
				stack.push(c);
			}
		}
		while (!stack.isEmpty()) {
			out = stack.pop() + out;
		}
		return out;
	}

	private static String negate(String in) {
		String result = "";
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == 'T') {
				result += 'F';
			} else {
				result += 'T';
			}
		}
		return result;
	}

	private static String or(String s1, String s2) {
		String result = "";
		for (int i = 0; i < s1.length(); i++) {
			char a = s1.charAt(i);
			char b = s2.charAt(i);
			if (a == 'T' || b == 'T') {
				result += 'T';
			} else {
				result += 'F';
			}
		}
		return result;
	}

	private static String and(String s1, String s2) {
		String result = "";
		for (int i = 0; i < s1.length(); i++) {
			char a = s1.charAt(i);
			char b = s2.charAt(i);
			if (a == 'T' && b == 'T') {
				result += 'T';
			} else {
				result += 'F';
			}
		}
		return result;
	}

	private static int power(int base, int power) {
		if (base == 0) {
			return 0;
		}
		if (power == 0) {
			return 1;
		}
		int result = base;
		for (int i = 1; i < power; i++) {
			result *= base;
		}
		return result;
	}
}