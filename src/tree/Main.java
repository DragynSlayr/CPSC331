package tree;

public class Main {

	public static void main(String[] args) {
		/*
		BST b = new BST();
		b.insert(4);
		b.insert(5);
		b.insert(3);
		b.display(b.root);
		System.out.println();
		*/

		BST c = new BST();
		c.insertRecursively(c.root, 4);
		c.insertRecursively(c.root, 5);
		c.insertRecursively(c.root, 3);
		c.display(c.root);
	}
}
