package us.hcheng.javaio.thread.jcu.collections.custom;

import us.hcheng.javaio.thread.jcu.collections.entity.EmptyLinkedListException;
import us.hcheng.javaio.thread.jcu.collections.entity.Node;

public class LinkedList<E> {

	private Node<E> first;
	private int size;

	public static <E> LinkedList<E> of(E... elements) {
		if (elements == null || elements.length < 0)
			throw new EmptyLinkedListException("use of to set an empty list!!!");

		LinkedList<E> ret = new LinkedList<>();
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

	/**
	 * 1:
	 * first
	 * [x] -> null   [new]
	 *
	 * 2:
	 *         first
	 * [new] -> [x] -> null
	 *
	 * 3:
	 * first
	 * [new] -> [x] -> null
	 *
	 * @param e
	 */
	public void addFirst(E e) {
		if (this.isEmpty()) {
			this.first = new Node<>(e);
		} else {
			Node<E> newNode = new Node<>(e);
			newNode.next = this.first;
			this.first = newNode;
		}
		this.size++;
	}

	/**
	 * 1:
	 * first
	 * [x] -> [y] ->  null
	 *
	 * 2:
	 * first
	 * [x]  [y] ->  null
	 *
	 * 3:
	 *      first
	 * [x]  [y] ->  null
	 *
	 * @param e
	 */
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
