package tree;

import java.util.Iterator;
import java.util.LinkedList;

public class BinarySearchTree<E extends Comparable<E>> {

	BSTNode<E> root;
	int size;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	public boolean insertAll(E[] dataSet) {
		boolean returnValue = true;

		for (E data : dataSet) {
			boolean result = insert(data);
			if (!result) {
				returnValue = false;
			}
		}

		return returnValue;
	}

	public boolean insertAll(LinkedList<E> dataSet) {
		boolean returnValue = true;

		Iterator<E> i = dataSet.iterator();
		while (i.hasNext()) {
			boolean result = insert(i.next());
			if (!result) {
				returnValue = false;
			}
		}

		return returnValue;
	}

	public boolean insert(E data) {
		if (root == null) {
			root = new BSTNode<E>(data);
			size++;
			return true;
		} else {
			boolean result = insertRecursively(root, new BSTNode<E>(data));
			if (result) {
				size++;
			}
			return result;
		}
	}

	private boolean insertRecursively(BSTNode<E> root, BSTNode<E> data) {
		if (root == null) {
			root = data;
			return true;
		} else if (root.compareTo(data.data) > 0) {
			if (root.left == null) {
				root.left = data;
				return true;
			} else {
				return insertRecursively(root.left, data);
			}
		} else if (root.compareTo(data.data) < 0) {
			if (root.right == null) {
				root.right = data;
				return true;
			} else {
				return insertRecursively(root.right, data);
			}
		} else {
			return false;
		}
	}

	public void getElements(LinkedList<String> l) {
		String[] stringElements = toString().split(",");
		for (int i = 0; i < stringElements.length; i++) {
			l.add(stringElements[i]);
		}
	}

	public void getElements(String[] a) {
		String[] stringElements = toString().split(",");
		for (int i = 0; i < stringElements.length; i++) {
			a[i] = stringElements[i];
		}
	}

	public String toString() {
		String tree = displayRecursively(root, "");
		tree = tree.substring(0, tree.length() - 1);
		return tree;
	}

	private String displayRecursively(BSTNode<E> node, String s) {
		if (node != null) {
			s += displayRecursively(node.left, s) + node.data + "," + displayRecursively(node.right, s);
			return s;
		} else {
			return s;
		}
	}

	public boolean isEmpty() {
		return root == null;
	}
}
