package tree;

import java.util.Iterator;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

		Integer[] elements = { 10, 3, 20, 5, 2, 15, 30 };
		tree.insertAll(elements);

		System.out.println(tree);

		LinkedList<String> sorted = new LinkedList<String>();
		tree.getElements(sorted);

		Iterator<String> i = sorted.iterator();
		while (i.hasNext()) {
			System.out.printf("%d ", Integer.parseInt(i.next()));
		}
	}
}
