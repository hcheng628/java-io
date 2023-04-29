package us.hcheng.javaio.thread.jcu.collections.custom;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleSkipList {
	private static final byte HEAD = (byte) -1;
	private static final byte DATA = (byte) 0;
	private static final byte TAIL = (byte) 1;
	private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();
	private int size, level;
	private Node head, tail;

	public SimpleSkipList() {
		this.head = new Node(HEAD);
		this.tail = new Node(TAIL);
		this.head.right = this.tail;
		this.tail.left = this.head;
	}

	public boolean isEmpty() {
		return this.size > 0;
	}

	public int size() {
		return this.size;
	}

	public boolean contains(int val) {
		Node n = findClosest(val);
		return n.value != null && n.value == val;
	}

	public Node findClosest(int val) {
		Node curr = this.head;

		for (; ; )
			if (curr.right.type != TAIL && curr.right.value <= val)
				curr = curr.right;
			else
				if (curr.down == null)
					break;
				else
					curr = curr.down;

		return curr;
	}

	public void dumpList() {
		System.out.println("******************************");
		Node curr = this.head;

		for (int currLevel = 0;currLevel <= this.level; currLevel++) {
			Node next = curr.down;

			System.out.print(currLevel + " out of " + this.level + " ");
			for (; curr.type != TAIL; curr = curr.right)
				System.out.print(curr + "->");
			System.out.print(curr);
			System.out.println();

			curr = next;
		}
		System.out.println("******************************\n");
	}

	public void add(int val) {
		Node newNode = new Node(val);
		Node leftNode = this.findClosest(val);
		setLeftAndRight(leftNode, newNode, leftNode.right);

		for (int currLevel = 0; RAND.nextDouble() < 0.5D; currLevel++) {
			if (currLevel >= this.level) {
				Node newHead = new Node(HEAD);
				Node newTail = new Node(TAIL);
				setUpAndDown(newHead, head);
				setUpAndDown(newTail, tail);
				newHead.right = newTail;
				newTail.left = newHead;

				head = newHead;
				tail = newTail;
				this.level++;
			}
			while (leftNode.left != null && leftNode.up == null)
				leftNode = leftNode.left;

			leftNode = leftNode.up;
			Node currNewNode = new Node(val);
			setUpAndDown(currNewNode, newNode);
			setLeftAndRight(leftNode, currNewNode, leftNode.right);
			newNode = currNewNode;
		}

		this.size++;
	}

	private void setUpAndDown(Node up, Node down) {
		up.down = down;
		down.up = up;
	}

	private void setLeftAndRight(Node left, Node mid, Node right) {
		mid.right = right;
		mid.left = left;
		left.right = mid;
		right.left = mid;
	}

	class Node {
		private Node up, left, down, right;
		private Integer value;
		private byte type;

		public Node(int value) {
			this.value = value;
			this.type = DATA;
		}

		public Node(byte type) {
			if (type != HEAD && type != TAIL)
				throw new IllegalArgumentException("Type can only be HEAD or TAIL");
			this.type = type;
		}

		@Override
		public String toString() {
			return this.type == DATA ? new StringBuilder().append("DATA[").append(this.value).append("]").toString()
					: new StringBuilder().append(this.type == HEAD ? "HEAD" : "TAIL").toString();
		}

	}

}
