package assignments;
// Name: 		Inderpreet Dhillon
// UCID:  		10159608
// Class:  		CPSC 331
// Purpose:		This program is capable of adding/subtracting very large numbers as well as identifying the the larger of the 2

import java.util.Scanner;

public class Adder {

	// Declare global variables for storing different results and intermediate values
	private static String num1, num2, larger;
	private static String integer1, integer2, decimal1, decimal2;
	private static String sumInteger, sumDecimal;
	private static String differenceInteger, differenceDecimal;
	private static String biggerInteger, biggerDecimal;
	private static String smallerInteger, smallerDecimal;

	public static void main(String[] args) {
		// Get 2 input strings from the user that represent numbers
		getInput();

		// Find the larger of the 2 numbers
		findLarger();

		// Find the sum of the 2 numbers
		calculateSum();

		// Find the difference between the numbers
		calculateDifference();

		// Print all the above findings
		printResults();
	}

	/*
	 * Gets 2 strings from the user, these represent floating point numbers
	 *
	 * Pre-Condition: 
	 * ~ num1, num2, integer1, decimal1, integer2, decimal2 have been declared but not initialized
	 * 
	 * Post-Condition: 
	 * ~ num1, num2 have the user's values
	 * ~ integer1, integer2 have the content of respective numbers before the decimal 
	 * ~ decimal1, decimal2 have the content of respective numbers after the decimal 
	 * ~ integer1, integer2 have the same length
	 * ~ decimal1, decimal2 have the same length
	 */
	private static void getInput() {
		// Declare a Scanner object for taking input
		Scanner keyb = new Scanner(System.in);

		// Prompt use for input and store string
		System.out.print("String1 = ");
		num1 = keyb.nextLine();

		// Prompt use for input and store string
		System.out.print("String2 = ");
		num2 = keyb.nextLine();

		// Close Scanner object
		keyb.close();

		// Find index of '.' in first string
		short idx1 = (short) num1.indexOf(".");

		// Split string into 2 parts, integer and decimal
		integer1 = num1.substring(0, idx1);
		decimal1 = num1.substring(idx1 + 1, num1.length());

		// Find index of '.' in second string
		short idx2 = (short) num2.indexOf(".");

		// Split string into 2 parts, integer and decimal
		integer2 = num2.substring(0, idx2);
		decimal2 = num2.substring(idx2 + 1, num2.length());

		// Make both integer strings the same length by prepending zeroes
		if (integer1.length() < integer2.length()) {
			integer1 = padString(integer1, (short) integer2.length(), true);
		} else if (integer2.length() < integer1.length()) {
			integer2 = padString(integer2, (short) integer1.length(), true);
		}
		
		// Make both decimal strings the same length by appending zeroes
		if (decimal1.length() < decimal2.length()) {
			decimal1 = padString(decimal1, (short) decimal2.length(), false);
		} else if (decimal2.length() < decimal1.length()) {
			decimal2 = padString(decimal2, (short) decimal1.length(), false);
		}
	}

	/*
	 * Finds the larger of num1, num2
	 * 
	 * Pre-Condition:
	 * ~ num1, num2, integer1, integer2, decimal1, decimal2 have values
	 * ~ larger, biggerInteger, biggerDecimal, smallerInteger, smallerDecimal have been declared but not initialized
	 * 
	 * Post-Condition:
	 * ~ num1, num2 are not changed
	 * ~ larger has the value of the larger of num1 and num2
	 * ~ biggerInteger, smallerInteger hold the value before the decimal of the respective number
	 * ~ biggerDecimal, smallerDecimal hold the value after the decimal of the respective number
	 */
	private static void findLarger() {
		// Boolean flag for when the larger number is found
		boolean largerFound = false;

		// Concatenate both parts of both strings
		String s1 = integer1 + decimal1;
		String s2 = integer2 + decimal2;

		// Counter for while loop
		short idx = 0;

		// Loop until counter >= length of the strings
		while (idx < s1.length()) {
			
			// Check if the larger number has been found
			if (!largerFound) {
				// Get the integer value of each character of each string
				short a = (short) Character.getNumericValue(s1.charAt(idx));
				short b = (short) Character.getNumericValue(s2.charAt(idx));

				// Compare both numbers
				if (a < b) {
					// Set larger to number 2
					larger = num2;
					largerFound = true;
				} else if (a > b) {
					// Set larger to number 1
					larger = num1;
					largerFound = true;
				}
			}
			// Increment loop counter
			idx++;
		}

		// In case both numbers are the same, choose num1 to be larger
		if (!largerFound) {
			larger = num1;
		}

		// Classify the parts of both numbers, needed for sum and difference later
		if (larger.equals(num1)) {
			biggerInteger = integer1;
			biggerDecimal = decimal1;
			smallerInteger = integer2;
			smallerDecimal = decimal2;
		} else {
			biggerInteger = integer2;
			biggerDecimal = decimal2;
			smallerInteger = integer1;
			smallerDecimal = decimal1;
		}
	}

	/*
	 * Calculates the sum between num1 and num2
	 * 
	 * Pre-Condition: 
	 * ~ integer1, decimal1, integer2, decimal2 are declared and have values
	 * ~ sumInteger, sumDecimal have been declared but have no values
	 * 
	 * Post-Condition: 
	 * ~ integer1, decimal1, integer2, decimal2 have not changed values
	 * ~ sumInteger, sumDecimal have been given values
	 */
	private static void calculateSum() {
		// Concatenate both parts of both strings
		String s1 = integer1 + decimal1;
		String s2 = integer2 + decimal2;

		// Declare string for sum
		String totalSum = "";

		// Initialize carry value for summation
		short carry = 0;

		// Counter for while loop
		short idx = (short) (s1.length() - 1);

		// Loop until counter < 0
		while (idx >= 0) {
			
			// Get integer value of each character in the string
			short a = (short) Character.getNumericValue(s1.charAt(idx));
			short b = (short) Character.getNumericValue(s2.charAt(idx));

			// Add both integers and the carry
			short sum = (short) (a + b + carry);

			// Fix carry and sum if sum is out of decimal bounds (0 to 9)
			if (sum >= 10) {
				sum = (short) (sum - 10);
				carry = 1;
			} else {
				carry = 0;
			}

			// Prepend sum to total sum
			totalSum = String.valueOf(sum) + totalSum;

			// Decrement counter
			idx--;
		}

		// Split total sum into it's parts
		sumInteger = totalSum.substring(0, integer1.length());
		sumDecimal = totalSum.substring(integer1.length(), totalSum.length());

		// Prepend '1' to integer sum when carry is not zero
		if (carry != 0) {
			sumInteger = "1" + sumInteger;
		}
	}

	/*
	 * Finds the difference between the bigger number and the smaller number
	 * 
	 * Pre-Condition: 
	 * ~ biggerInteger, biggerDecimal, smallerInteger, smallerDecimal are declared and have values
	 * ~ differenceInteger, differenceDecimal have been declared but have no values
	 * 
	 * Post-Condition: 
	 * ~ biggerInteger, biggerDecimal, smallerInteger, smallerDecimal have not changed values
	 * ~ differenceInteger, differenceDecimal have been given values
	 */
	private static void calculateDifference() {
		// Concatenate both parts of both strings
		String s1 = biggerInteger + biggerDecimal;
		String s2 = smallerInteger + smallerDecimal;

		// String to hold the difference
		String totalDifference = "";

		// Initialize carry for subtraction
		short carry = 0;

		// Counter for while loop
		short idx = (short) (s1.length() - 1);

		// Loop until counter < 0
		while (idx >= 0) {
			
			// Get integer value of each character in the string
			short a = (short) Character.getNumericValue(s1.charAt(idx));
			short b = (short) Character.getNumericValue(s2.charAt(idx));

			// Calculate the difference between the two numbers
			short difference = (short) ((a - carry) - b);

			// Check if the difference is negative
			if (difference < 0) {
				// Fix difference and set carry
				difference += 10;
				carry = 1;
			} else {
				carry = 0;
			}

			// Prepend the difference to the total difference
			totalDifference = String.valueOf(difference) + totalDifference;

			// Decrement count
			idx--;
		}

		// Split total difference into it's parts
		differenceInteger = totalDifference
				.substring(0, biggerInteger.length());
		differenceDecimal = totalDifference.substring(biggerInteger.length(),
				totalDifference.length());
	}

	/*
	 * Prints the results of the program
	 * 
	 * Pre-Condition: 
	 * ~ larger has been declared and has a value
	 * ~ sumInteger, sumDecimal has been declared and has a value
	 * ~ differenceInteger, differenceDecimal has been declared and has a value
	 * 
	 * Post-Condition: 
	 * ~ larger, sumInteger, sumDecimal, differenceInteger, differenceDecimal have not been changed
	 * ~ the results have been printed to the screen
	 */
	private static void printResults() {
		// Print the results
		System.out.println("Results:");
		System.out.printf("Larger: %s\n", larger);
		System.out.printf("Sum: %s.%s\n", sumInteger, sumDecimal);
		System.out.printf("Difference: %s.%s\n", differenceInteger,
				differenceDecimal);
	}

	/*
	 * Adds zero padding to a string
	 * 
	 * Pre-Condition: 
	 * ~ s is a String
	 * ~ length is a short
	 * ~ isFront is a boolean
	 * 
	 * Post-Condition: 
	 * ~ s is unchanged
	 * ~ length is unchanged
	 * ~ isFront is unchanged
	 * ~ a padded copy of s is returned
	 */
	private static String padString(String s, short length, boolean isFront) {
		// Loop until s.length >= length
		while (s.length() < length) {
			if (isFront) {
				// Prepend '0' when front padding
				s = "0" + s;
			} else {
				// Append '0' when back padding
				s = s + "0";
			}
		}

		// Return the padded string
		return s;
	}
}