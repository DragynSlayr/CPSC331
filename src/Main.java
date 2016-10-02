import java.util.Scanner;

public class Main {

	private static String integer1, integer2, decimal1, decimal2;
	private static String num1, num2;
	private static String larger;
	private static String sumInteger = "", sumDecimal = "",
			differenceInteger = "", differenceDecimal = "";

	public static void main(String[] args) {
		section1();
		section2();
		section3();
		section4();
		section5();
	}

	private static void section1() {
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

	private static void section2() {
		if (integer1.length() < integer2.length()) {
			larger = num2;
		} else if (integer1.length() > integer2.length()) {
			larger = num1;
		} else {
			short idx = 0;
			String l = "";
			while (idx < integer1.length()) {
				short a = Short.parseShort(integer1.charAt(idx) + "");
				short b = Short.parseShort(integer2.charAt(idx) + "");
				if (a < b) {
					l = num2;
					break;
				} else if (a > b) {
					l = num1;
					break;
				}
				idx++;
			}
			if (l.equals("")) {
				if (decimal1.length() < decimal2.length()) {
					larger = num2;
				} else if (decimal1.length() > decimal2.length()) {
					larger = num1;
				} else {
					idx = 0;
					l = "";
					while (idx < decimal1.length()) {
						short a = Short.parseShort(decimal1.charAt(idx) + "");
						short b = Short.parseShort(decimal2.charAt(idx) + "");
						if (a < b) {
							l = num2;
							break;
						} else if (a > b) {
							l = num1;
							break;
						}
						idx++;
					}
				}
			}

			larger = l;
		}
	}

	private static void section3() {
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

	private static void section4() {
	}

	private static void section5() {
		System.out.printf("%s %s\n", integer1, decimal1);
		System.out.printf("%s %s\n", integer2, decimal2);
		System.out.println("Results:");
		System.out.printf("Larger: %s\n", larger);
		System.out.printf("Sum: %s.%s\n", sumInteger, sumDecimal);
		System.out.printf("Difference: %s.%s\n", differenceInteger,
				differenceDecimal);
	}
}
