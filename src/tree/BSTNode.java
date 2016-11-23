package tree;

public class BSTNode<E> {

	public E data;
	public BSTNode<E> left, right;

	public BSTNode(E data) {
		this.data = data;
	}

	public void display() {
		System.out.println(data);
	}
}
