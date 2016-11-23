package search;

public class Main {

	public static void main(String[] args) {
		BST tree = new BST();
		tree.insert(3);
		tree.insert(5);
		tree.insert(1);
		
		tree.display(tree.root);
	}

}
