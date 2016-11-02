
// Name: 		Inderpreet Dhillon
// UCID:  		10159608
// Class:  		CPSC 331
// Purpose:		This program is capable of multiplying 2 numbers and displaying their product as well as intermediate products

import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	// Declare global variables for storing results and intermediate values
	private static String num1, num2, product;
	private static LinkedList<String> intermediateStack, finalStack;

	public static void main(String[] args) {
		// Get 2 input strings from the use that represent numbers
		getInput();

		// Create a Date object that represents when the program started
		Date start = new Date();

		// Calculate the product of the 2 numbers
		getProduct();

		// Prints the product of the 2 numbers
		printResults();

		// Prints the intermediate products
		printIntermediates();

		// Create a Date object that represents when the program ended
		Date end = new Date();

		// Print total execution time
		System.out.printf("Total Time: %dms\n", end.getTime() - start.getTime());
	}

	private static void getInput() {
		// Declare Scanner object for taking input
		Scanner keyb = new Scanner(System.in);

		// Prompt user for input and store string
		System.out.print("String1 = ");
		num1 = keyb.nextLine();

		// Prompt user for input and store string
		System.out.print("String2 = ");
		num2 = keyb.nextLine();

		// Close scanner object
		keyb.close();
	}

	private static void getProduct() {
		// Multiply the numbers and find intermediates
		getIntermediates();

		// Sum all the intermediate to find final product
		sumIntermediates();
	}

	private static void printResults() {
		// Print the product
		System.out.print("Results:\nProduct: ");
		System.out.println(product);
	}

	private static void printIntermediates() {
		// Print the intermediate results
		System.out.println("Intermediate Calculations:");

		// Keep track of current index for printing
		short index = 1;

		// Loop until the stack is empty
		while (!finalStack.isEmpty()) {
			// Print the index and the element from the top of the stack
			System.out.printf("%d) %s\n", index, finalStack.pop());

			// Increment index
			index++;
		}
	}

	private static void getIntermediates() {
		// Initialize stack for intermediates
		intermediateStack = new LinkedList<String>();

		// Initialize index for second number
		short num2Index = (short) (num2.length() - 1);

		// Initialize carry for multiplication
		short carry = 0;

		// Initialize counter for number of zeroes needed at the end of a number
		short zeroes = 0;

		// Initialize string to hold intermediate
		String intermediate = "";

		// Loop until num2Index < 0
		while (num2Index >= 0) {

			// Initialize index for first number
			short num1Index = (short) (num1.length() - 1);

			// Loop until num1Index < 0
			while (num1Index >= 0) {

				// Get integer value of each character in the strings
				short a = (short) Character.getNumericValue(num1.charAt(num1Index));
				short b = (short) Character.getNumericValue(num2.charAt(num2Index));

				// Multiply the numbers and add the carry
				short c = (short) ((a * b) + carry);

				// Adjust carry to be 0 or the first digit of c
				carry = (short) (c / 10);

				// Adjust c to be between 0 and 9
				c = (short) (c % 10);

				// Prepend c to intermediate
				intermediate = c + intermediate;

				// Decrement num1Index
				num1Index--;
			}

			// Check if carry has a value
			if (carry != 0) {
				// Prepend carry to intermediate
				intermediate = carry + intermediate;
			}

			// Reset carry
			carry = 0;

			// Push intermediate to the top of the stack
			intermediateStack.push(intermediate);

			// Reset intermediate
			intermediate = "";

			// Increment zeroes
			zeroes++;

			// Loop until intermediate length is the same as zeroes
			while (intermediate.length() != zeroes) {
				// Append 0 to intermediate
				intermediate += "0";
			}

			// Decrement num2Index
			num2Index--;
		}
	}

	private static void sumIntermediates() {
		// Initialize stack for final printing
		finalStack = new LinkedList<String>();

		// Set product to the top of the intermediate
		product = intermediateStack.pop();

		// Push the intermediate to the top of final stack
		finalStack.push(product);

		// Loop until the intermediate stack is empty
		while (!intermediateStack.isEmpty()) {

			// Get the next intermediate
			String smaller = intermediateStack.pop();

			// Put the intermediate on the final stack
			finalStack.push(smaller);

			// Pad the string to the same length as product
			String temp = padString(smaller, (short) product.length());

			// Add product and temp to get the new product
			product = sumNumbers(product, temp);
		}
	}

	private static String padString(String s, short length) {
		// Loop until length of s == length
		while (s.length() < length) {
			// Prepend 0 to s
			s = "0" + s;
		}

		// Return the padded string
		return s;
	}

	private static String sumNumbers(String s1, String s2) {
		// Initialize a string for the sum
		String sum = "";

		// Initialize index for loop
		short index = (short) (s1.length() - 1);

		// Initialize carry for addition
		short carry = 0;

		// Loop until index < 0
		while (index >= 0) {

			// Get integer value of character at index from both strings
			short a = (short) Character.getNumericValue(s1.charAt(index));
			short b = (short) Character.getNumericValue(s2.charAt(index));

			// Add integers and carry
			short c = (short) (a + b + carry);

			// Adjust carry to be 0 or the first digit of c
			carry = (short) (c / 10);

			// Adjust c to be between 0 and 9
			c = (short) (c % 10);

			// Prepend c to sum
			sum = c + sum;

			// Decrement index
			index--;
		}

		// Check if carry has a value
		if (carry != 0) {
			// Prepend carry to sum
			sum = carry + sum;
		}

		// Return sum of the 2 numbers
		return sum;
	}
}
