package us.hcheng.javaio.thread.jcu.collections.entity;

public class Node<T> {
	private T value;
	public Node<T> next;

	public Node(T value) {
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
