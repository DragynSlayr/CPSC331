package list;

public class LinkedListStack<E> extends List<E> {

	public void push(E data) {
		Node<E> newNode = new Node<E>(data);
		if (head == null) {
			head = newNode;
		} else {
			newNode.next = head;
			head = newNode;
		}
		size++;
	}
}
