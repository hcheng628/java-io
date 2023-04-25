package us.hcheng.javaio.thread.jcu.collections;


import us.hcheng.javaio.thread.jcu.collections.entity.EmptyLinkedListException;
import us.hcheng.javaio.thread.jcu.collections.entity.Node;

public class PriorityLinkedList<E extends Comparable> {

	private Node<E> first;
	private int size;

	public static <E extends Comparable<E>> PriorityLinkedList<E> of(E... elements) {
		if (elements == null || elements.length < 0)
			throw new EmptyLinkedListException("use of to set an empty list!!!");

		PriorityLinkedList<E> ret = new PriorityLinkedList<>();
		for (E e : elements)
			ret.addFirst(e);

		return ret;
	}


	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public boolean contains(E e) {
		if (this.isEmpty())
			return false;

		for (Node<E> curr = this.first; curr != null; curr = curr.next)
			if (curr.getValue().equals(e))
				return true;

		return false;
	}

	public void addFirst(E e) {
		Node<E> newNode = new Node<>(e);

		if (this.isEmpty())
			this.first = newNode;
		else {
			Node<E> prev = null;
			Node<E> curr = this.first;

			while (curr != null && curr.getValue().compareTo(e) < 0) {
				prev = curr;
				curr = curr.next;
			}

			if (prev == null) {
				newNode.next = first;
				this.first = newNode;
			} else {
				prev.next = newNode;
				newNode.next = curr;
			}
		}
		this.size++;
	}

	public E removeFirst() {
		if (this.isEmpty())
			throw new EmptyLinkedListException("EMPTY list...");

		Node<E> fNode = first;
		first = fNode.next;
		fNode.next = null;
		this.size--;
		return fNode.getValue();
	}

	@Override
	public String toString() {
		if (this.isEmpty())
			return "[EMPTY]";

		StringBuilder sb = new StringBuilder("[");
		for (Node<E> curr = this.first; curr != null; curr = curr.next)
			sb.append(curr.getValue()).append("->");
		sb.append("NULL]");

		return sb.toString();
	}

}
