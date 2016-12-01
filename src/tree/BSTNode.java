package tree;

public class BSTNode<E extends Comparable<E>> implements Comparable<E> {

	public E data;
	public BSTNode<E> left, right;

	public BSTNode(E data) {
		this.data = data;
	}

	public String toString() {
		return data.toString();
	}

	@Override
	public int compareTo(E node) {
		return data.compareTo(node);
	}
}
