package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Random rand = new Random();
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		LinkedList<Integer> elements = new LinkedList<Integer>();
		int num = 100;
		for (int i = 0; i < num; i++) {
			elements.add(rand.nextInt(num * 2) - num);
		}
		tree.insertAll(elements);

		LinkedList<String> sorted = new LinkedList<String>();
		tree.getElements(sorted);

		String s = "";
		int count = 0;
		Iterator<String> i = sorted.iterator();
		while (i.hasNext()) {
			s += i.next() + " ";
			count++;
			if (count == 10) {
				count = 0;
				s += "\n";
			}
		}
		System.out.println(s);
	}
}
