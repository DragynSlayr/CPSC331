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

	// Declare global variables for storing input, independent variables and
	// sub-expressions
	private static String input;
	private static LinkedList<Character> variables;
	private static HashMap<String, String> expressions;

	// Declare final truth table and number of truth values per variable
	private static String[][] truthTable;
	private static int numTruthValues;

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
		// Declare Scanner object for taking input
		Scanner keyb = new Scanner(System.in);

		// Prompt user for input and store upper-cased string
		System.out.print("Input string: ");
		input = keyb.nextLine().toUpperCase();

		// Close Scanner object
		keyb.close();
	}

	private static void parse() {
		// Initialize independent variables list
		variables = new LinkedList<Character>();

		// Initialize sub-expressions map
		expressions = new HashMap<String, String>();

		// Create stack to hold indices of all '(' in the expression
		Stack<Integer> indices = new Stack<Integer>();

		// Initialize counter for the number of sub-expressions
		int expressionCount = 0;

		// Traverse the input string
		for (int i = 0; i < input.length(); i++) {

			// Get the current character
			char c = input.charAt(i);

			// Handle the current character
			if (Character.isLetter(c)) {

				// Check if the list has the variable
				if (indexOf(variables, c) == -1) {
					// Add the character to the variables list
					variables.add(c);
				}
			} else if (c == '(') {
				// When the character is a '(', add the index to the stack
				indices.push(i);
			} else if (c == ')') {
				// Get the sub-expression from the last '(' to the current index
				String sub = input.substring(indices.pop() + 1, i);

				// Check if the map has the sub-expression
				if (indexOf(expressions, sub) == -1) {
					// Put the sub-expression in the map
					expressions.put(IDENTIFIER + expressionCount, sub);

					// Increment expression counter
					expressionCount++;
				}
			}
		}

		// Calculate the number of truth values based on the number of variables
		numTruthValues = power(2, variables.size());
	}

	private static void evaluate() {
		// Initialize the truth table with the correct dimensions
		truthTable = new String[1 + numTruthValues][variables.size() + expressions.size()];

		// Traverse each column of the truth table
		for (int i = 0; i < truthTable[0].length; i++) {

			// Declare empty truth values string
			String values = "";

			// Determine if the current column holds a variable or expression
			if (i < variables.size()) {
				// Get the current variable
				char variable = variables.get(i);

				// Set the current column header to variable at the index
				truthTable[0][i] = String.valueOf(variable);

				// Set the values to be those for the variable
				values = getTruthColumn(variable);
			} else {
				// Get the key for the expression
				String key = IDENTIFIER + (i - variables.size());

				// Set values to be the evaluated truth values of the expression
				values = getTruthValues(expressions.get(key));
			}

			// Traverse each row of the truth table
			for (int j = 1; j < truthTable.length; j++) {

				// Get the current truth value
				char value = values.charAt(j - 1);

				// Store the value in the array
				truthTable[j][i] = String.valueOf(value);
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
		int split = numTruthValues / power(2, indexOf(variables, c) + 1);
		int counter = 0;
		boolean isTrue = true;
		for (int i = 0; i < numTruthValues; i++) {
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