package list;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		stackTest();
		System.out.println();
		queueTest();
	}

	private static void stackTest() {
		LinkedListStack<Object> l = new LinkedListStack<Object>();
		l.push(1);
		l.display();

		l.push("g");
		l.display();

		l.push(3);
		l.display();

		l.push(4.5);
		l.display();

		l.pop();
		l.display();

		l.pop();
		l.display();

		l.pop();
		l.display();
	}

	private static void queueTest() {
		LinkedListQueue<Object> l = new LinkedListQueue<Object>();
		l.push(1);
		l.display();

		l.push("g");
		l.display();

		l.push(3);
		l.display();

		l.push(4.5);
		l.display();

		l.pop();
		l.display();

		l.pop();
		l.display();

		l.pop();
		l.display();
	}
}
