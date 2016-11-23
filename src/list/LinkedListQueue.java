package list;

public class LinkedListQueue<E> extends List<E> {

	public void push(E data) {
		Node<E> newNode = new Node<E>(data);
		if (head == null) {
			head = newNode;
		} else {
			Node<E> current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = newNode;
		}
		size++;
	}
}
