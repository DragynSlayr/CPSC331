import java.util.Scanner;

public class Main {

	private static String num1, num2, larger = "";
	private static String integer1, integer2, decimal1, decimal2;
	private static String sumInteger = "", sumDecimal = "",
			differenceInteger = "", differenceDecimal = "";
	private static String biggerInteger, biggerDecimal, smallerInteger,
			smallerDecimal;

	public static void main(String[] args) {
		getInput();
		findLarger();
		padStrings();
		calculateSum();
		classifyNumbers();
		calculateDifference();
		printResults();
	}

	private static void getInput() {
		Scanner keyb = new Scanner(System.in);

		System.out.print("String1 = ");
		num1 = keyb.nextLine();

		System.out.print("String2 = ");
		num2 = keyb.nextLine();

		keyb.close();

		short idx1 = (short) num1.indexOf(".");
		integer1 = num1.substring(0, idx1);
		decimal1 = num1.substring(idx1 + 1, num1.length());

		short idx2 = (short) num2.indexOf(".");
		integer2 = num2.substring(0, idx2);
		decimal2 = num2.substring(idx2 + 1, num2.length());
	}

	private static void findLarger() {
		if (integer1.length() < integer2.length()) {
			larger = num2;
		} else if (integer1.length() > integer2.length()) {
			larger = num1;
		} else {
			for (short idx = 0; idx < integer1.length(); idx++) {
				short a = Short.parseShort(integer1.charAt(idx) + "");
				short b = Short.parseShort(integer2.charAt(idx) + "");
				if (a < b) {
					larger = num2;
					break;
				} else if (a > b) {
					larger = num1;
					break;
				}
			}
			if (larger.equals("")) {
				if (decimal1.length() < decimal2.length()) {
					larger = num2;
				} else if (decimal1.length() > decimal2.length()) {
					larger = num1;
				} else {
					for (short idx = 0; idx < decimal1.length(); idx++) {
						short a = Short.parseShort(decimal1.charAt(idx) + "");
						short b = Short.parseShort(decimal2.charAt(idx) + "");
						if (a < b) {
							larger = num2;
							break;
						} else if (a > b) {
							larger = num1;
							break;
						}
					}
				}
			}
		}
	}

	private static void calculateSum() {
		short carry = 0;
		for (short idx = (short) (decimal1.length() - 1); idx >= 0; idx--) {
			short a = Short.parseShort(decimal1.charAt(idx) + "");
			short b = Short.parseShort(decimal2.charAt(idx) + "");
			short s = (short) (a + b + carry);
			carry = (short) (s / 10);
			s %= 10;
			sumDecimal = String.valueOf(s) + sumDecimal;
		}
		for (short idx = (short) (integer1.length() - 1); idx >= 0; idx--) {
			short a = Short.parseShort(integer1.charAt(idx) + "");
			short b = Short.parseShort(integer2.charAt(idx) + "");
			short s = (short) (a + b + carry);
			carry = (short) (s / 10);
			s %= 10;
			sumInteger = String.valueOf(s) + sumInteger;
		}
	}

	private static void calculateDifference() {
		short carry = 0;
		for (short idx = (short) (biggerDecimal.length() - 1); idx >= 0; idx--) {
			short a = Short.parseShort(biggerDecimal.charAt(idx) + "");
			short b = Short.parseShort(smallerDecimal.charAt(idx) + "");
			short s = (short) ((a - carry) - b);
			if (s < 0) {
				s += 10;
				carry = 1;
			} else {
				carry = 0;
			}
			differenceDecimal = String.valueOf(s) + differenceDecimal;
		}
		for (short idx = (short) (biggerInteger.length() - 1); idx >= 0; idx--) {
			short a = Short.parseShort(biggerInteger.charAt(idx) + "");
			short b = Short.parseShort(smallerInteger.charAt(idx) + "");
			short s = (short) ((a - carry) - b);
			if (s < 0) {
				s += 10;
				carry = 1;
			} else {
				carry = 0;
			}
			differenceInteger = String.valueOf(s) + differenceInteger;
		}
	}

	private static void printResults() {
		System.out.println("Results:");
		System.out.printf("Larger: %s\n", larger);
		System.out.printf("Sum: %s.%s\n", sumInteger, sumDecimal);
		System.out.printf("Difference: %s.%s\n", differenceInteger,
				differenceDecimal);
	}

	private static void padStrings() {
		if (integer1.length() > integer2.length()) {
			while (integer2.length() != integer1.length()) {
				integer2 = "0" + integer2;
			}
		} else if (integer1.length() < integer2.length()) {
			while (integer1.length() != integer2.length()) {
				integer1 = "0" + integer1;
			}
		}
		if (decimal1.length() > decimal2.length()) {
			while (decimal2.length() != decimal1.length()) {
				decimal2 = decimal2 + "0";
			}
		} else if (decimal1.length() < decimal2.length()) {
			while (decimal1.length() != decimal2.length()) {
				decimal1 = decimal1 + "0";
			}
		}
	}

	private static void classifyNumbers() {
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
}
