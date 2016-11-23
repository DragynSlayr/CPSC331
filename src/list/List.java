package list;

public abstract class List<E> {

	protected Node<E> head;
	protected int size;

	public List() {
		head = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public void display() {
		String s = "";
		Node<E> current = head;
		while (current != null) {
			s += current.data + " ";
			current = current.next;
		}
		System.out.println(s);
	}

	public Node<E> pop() {
		Node<E> current = head;
		head = head.next;
		size--;
		return current;
	}
	
	public abstract void push(E data);
}
