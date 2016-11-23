package applications;

import java.util.Scanner;

import list.LinkedListStack;

public class Main {
	public static void main(String[] args) {
		// studentStackProblem();

		Scanner keyb = new Scanner(System.in);
		System.out.print("Enter a phrase: ");
		String word = keyb.nextLine();
		keyb.close();

		// palindromeProblemArray(word);
		palindromeProblemStack(word);
		// palindromeProblemQueue(word);
	}

	private static void palindromeProblemStack(String word) {
		String in = word.replace(" ", "");

		LinkedListStack<Character> cs = new LinkedListStack<Character>();
	}

	private static void palindromeProblemArray(String word) {
		String in = word.replace(" ", "");

		char[] ca = new char[(int) Math.ceil(in.length() / 2.0)];
		for (int i = 0; i < ca.length; i++) {
			ca[i] = in.charAt(i);
		}

		int j = 0;
		boolean isPalindrome = true;
		for (int i = in.length() - 1; i > ca.length - 1; i--) {
			if (ca[j] == in.charAt(i) && isPalindrome) {
				j++;
			} else {
				isPalindrome = false;
			}
		}

		System.out.printf("\"%s\" %s a palindrome", word, (isPalindrome) ? "is" : "is not");
	}

	private static void studentStackProblem() {
		LinkedListStack<String> stack = new LinkedListStack<String>();
		Scanner keyb = new Scanner(System.in);
		String result = "Success";

		do {
			System.out.print("Enter ID: ");
			String in = keyb.nextLine();
			if (in.startsWith("C")) {
				stack.push(in.substring(1));
			} else {
				String top = stack.pop().data;
				if (!top.equals(in.substring(1))) {
					result = "Failure";
				}
			}
		} while (!stack.isEmpty());

		System.out.println(result);

		keyb.close();
	}
}
