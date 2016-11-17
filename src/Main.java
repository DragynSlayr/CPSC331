import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
				if (!contains(expressions, sub)) {
					// Put the sub-expression in the map
					expressions.put(IDENTIFIER + expressionCount, sub);

					// Increment expression counter
					expressionCount++;
				}
			}
		}

		// Calculate the number of truth values based on the number of variables
		numTruthValues = (int) Math.pow(2, variables.size());
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

				// Set the values to be those of the variable
				values = getTruthColumn(variable);
			} else {
				// Get the key for the expression
				String key = IDENTIFIER + (i - variables.size());

				// Set the column header to the expression at the index
				truthTable[0][i] = key;

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
		// Print the independent variables
		System.out.println("Output:\nSet of independent variables:");

		// Get the variables list's iterator
		Iterator<Character> iterator = variables.iterator();

		// Loop until the iterator runs out of items
		while (iterator.hasNext()) {

			// Print the next variable
			System.out.println(iterator.next());
		}
	}

	private static void printExpressions() {
		// Print the sub-expressions
		System.out.println("Set of logical subexpressions and logical expression:");

		// Loop through the expressions
		for (int i = 0; i < expressions.size(); i++) {

			// Get the key from the current index
			String key = IDENTIFIER + i;

			// Print the key and sub-expression
			System.out.printf("%s, %s\n", key, expressions.get(key));
		}
	}

	private static void printTruthTable() {
		// Print the truth table
		System.out.println("Truth Table:");

		// Iterate through the rows
		for (int i = 0; i < truthTable.length; i++) {

			// Iterate through the columns
			for (int j = 0; j < truthTable[i].length; j++) {

				// Check which row we are on
				if (i == 0) {
					// Print the current element
					System.out.print(truthTable[i][j] + "\t");
				} else {
					// Create a char array of half the size of the column header
					char[] pad = new char[truthTable[0][j].length() / 2];

					// Create a string from the array and replace null
					// terminator character with spaces
					String padding = new String(pad).replace('\0', ' ');

					// Print the padding and current element
					System.out.print(padding + truthTable[i][j] + "\t");
				}
			}
			// Go to the next line
			System.out.println();
		}
	}

	private static <E> int indexOf(List<E> list, E object) {
		// Initialize index
		int index = -1;

		// Initialize found boolean
		boolean found = false;

		// Get iterator from list
		Iterator<E> iterator = list.iterator();

		// Traverse list until the object is found or all elements are used
		while (iterator.hasNext() && !found) {

			// Compare next element to the object
			if (iterator.next().equals(object)) {
				// Set found when the object has been located
				found = true;
			}

			// Increment index
			index++;
		}

		// Determine if the object has been found
		if (found) {
			// Return the index when the object is found
			return index;
		} else {
			// Return -1 when the object is not found
			return -1;
		}
	}

	private static <K, V> boolean contains(Map<K, V> map, V object) {
		// Initialize found boolean
		boolean found = false;

		// Get iterator from map's keys
		Iterator<K> iterator = map.keySet().iterator();

		// Traverse map until the object is found or all elements are used
		while (iterator.hasNext() && !found) {

			// Compare value of next key to the object
			if (map.get(iterator.next()).equals(object)) {
				// Set found when the object is in the map
				found = true;
			}
		}

		// True if the object is in the map, false other wise
		return found;
	}

	private static String toPrefix(String input) {
		// Initialize output string
		String output = "";
		Stack<Character> stack = new Stack<Character>();
		for (int i = input.length() - 1; i > -1; i--) {
			char c = input.charAt(i);
			if (Character.isLetter(c)) {
				output = c + output;
			} else if (c == '(') {
				output = stack.pop() + output;
			} else if (c != ')') {
				stack.push(c);
			}
		}
		while (!stack.isEmpty()) {
			output = stack.pop() + output;
		}
		return output;
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
		int split = (int) (numTruthValues / Math.pow(2, indexOf(variables, c) + 1));
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

	private static String negate(String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == 'T') {
				result += 'F';
			} else {
				result += 'T';
			}
		}
		return result;
	}

	private static String or(String input1, String input2) {
		String result = "";
		for (int i = 0; i < input1.length(); i++) {
			char a = input1.charAt(i);
			char b = input2.charAt(i);
			if (a == 'T' || b == 'T') {
				result += 'T';
			} else {
				result += 'F';
			}
		}
		return result;
	}

	private static String and(String input1, String input2) {
		String result = "";
		for (int i = 0; i < input1.length(); i++) {
			char a = input1.charAt(i);
			char b = input2.charAt(i);
			if (a == 'T' && b == 'T') {
				result += 'T';
			} else {
				result += 'F';
			}
		}
		return result;
	}
}