package list;

public class Node<E> {

	public E data;
	public Node<E> next;

	public Node(E data) {
		this.data = data;
	}

	public void display() {
		System.out.println(data);
	}
}
