import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

// Name: 		Inderpreet Dhillon
// UCID:  		10159608
// Class:  		CPSC 331
// Purpose:		This program is capable of solving logical expressions given that they are formatted correctly
// Usage: 		Enter an expression consisting of letters (A-z) and symbols (+, *, -, "(, ')') and the truth table will be printed
// 					Valid expression: 		((A+B)+(-C))
//					Invalid expression:	(A+B)+-C, this expression is missing outer parentheses and brackets around -C

public class Main {

	// Declare global variables for storing input, independent variables and sub-expressions
	private static String input;
	private static LinkedList<Character> variables;
	private static HashMap<String, String> expressions;
	
	// Final truth table and height of the table
	private static String[][] truthTable;
	private static int rowHeight;
	
	// A unique identifier key for the sub-expressions in the HashMap
	private static final String IDENTIFIER = "LE";

	public static void main(String[] args) {
		// Get input string from the user
		getInput();
		
		// Find independent variables and sub-expressions
		parse();
		
		// Evaluate the sup-expressions and store the results in the truth table
		evaluate();

		// Print the independent variables
		printVariables();
		
		// Print the sub-expressions
		printExpressions();
		
		// Print the final truth table
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

		int index2 = 0;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isLetter(c)) {
				if (indexOf(variables, c) == -1) {
					variables.add(c);
				}
			} else if (c == '(') {
				indices.push(i + 1);
			} else if (c == ')') {
				String sub = input.substring(indices.pop(), i);
				if (indexOf(expressions, sub) == -1) {
					expressions.put(IDENTIFIER + index2, sub);
					index2++;
				}
			}
		}

		rowHeight = power(2, variables.size());
	}

	private static void evaluate() {
		truthTable = new String[1 + rowHeight][variables.size() + expressions.size()];

		for (int i = 0; i < truthTable[0].length; i++) {
			String key = "";
			String expression = "";
			if (i < variables.size()) {
				key = String.valueOf(variables.get(i));
				expression = key;
			} else {
				key = IDENTIFIER + (i - variables.size());
				expression = expressions.get(key);
			}
			truthTable[0][i] = key;
			String value = getTruthValues(expression);
			for (int j = 1; j < truthTable.length; j++) {
				truthTable[j][i] = String.valueOf(value.charAt(j - 1));
			}
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
		for (int i = 0; i < expressions.size(); i++) {
			String key = IDENTIFIER + i;
			System.out.printf("%s, %s\n", key, expressions.get(key));
		}
	}

	private static void printTruthTable() {
		System.out.println("Truth Table:");
		for (int i = 0; i < truthTable.length; i++) {
			for (int j = 0; j < truthTable[i].length; j++) {
				if (i == 0) {
					System.out.print(truthTable[i][j] + "\t");
				} else {
					char[] pad = new char[truthTable[0][j].length() / 2];
					String padding = new String(pad).replace('\0', ' ');
					System.out.print(padding + truthTable[i][j] + "\t");
				}
			}
			System.out.println();
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
		for (int i = prefix.length() - 1; i > -1; i--) {
			char c = prefix.charAt(i);
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
		}
		return stack.pop();
	}

	private static String getTruthColumn(char c) {
		String values = "";
		int split = rowHeight / power(2, indexOf(variables, c) + 1);
		int counter = 0;
		boolean isTrue = true;
		for (int i = 0; i < rowHeight; i++) {
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