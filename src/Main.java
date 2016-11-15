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
			if (index < variables.size()) {
				String key = String.valueOf(variables.get(index));
				truthTable[0][index] = key;
			} else {
				String key = IDENTIFIER + (index - variables.size());
				truthTable[0][index] = key;
			}
			index++;
		}

		index = 0;
		int length = variables.size() + expressions.size();
		while (index < length) {
			String expression = "";
			if (index < variables.size()) {
				expression = truthTable[0][index];
			} else {
				expression = expressions.get(truthTable[0][index]);
			}
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
		int split = power(2, indexOf(variables, c) + 1);
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
		return values;
	}

	private static String toPrefix(String in) {
		String out = "";
		Stack<Character> stack = new Stack<Character>();
		int index = in.length() - 1;
		while (index > -1) {
			char c = in.charAt(index);
			if (Character.isLetter(c)) {
				out = c + out;
			} else if (c == '(') {
				out = stack.pop() + out;
			} else if (c != ')') {
				stack.push(c);
			}
			index--;
		}
		while (!stack.isEmpty()) {
			out = stack.pop() + out;
		}
		return out;
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