package us.hcheng.javaio.thread.jcu.collections;

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

		for (;;)
			if (curr.right.type != TAIL && curr.right.value <= val)
				curr = curr.right;
			else
				if (curr.down == null)
					break;
				else
					curr = curr.down;

		//System.out.println("findClosest: " + curr + " right: " + curr.right);
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
		Node leftNode = this.findClosest(val);
		Node newNode = new Node(val);
		newNode.left = leftNode;
		newNode.right = leftNode.right;
		newNode.right.left = newNode;
		leftNode.right = newNode;

		int currLevel = 0;
		for (Node upNewNode; RAND.nextDouble() < 0.3D; newNode = upNewNode) {
			currLevel++;
			upNewNode = new Node(val);
			upNewNode.down = newNode;
			newNode.up = upNewNode;

			if (currLevel > this.level) {
				Node newHead = new Node(HEAD);
				newHead.down = this.head;
				this.head.up = newHead;

				Node newTail = new Node(TAIL);
				newTail.down = this.tail;
				this.tail.up = newTail;

				upNewNode.left = newHead;
				upNewNode.right = newTail;
				newHead.right = upNewNode;
				newTail.left = upNewNode;

				this.head = newHead;
				this.tail = newTail;
				this.level++;
				break;
			}

			while (leftNode.left != null && leftNode.up == null)
				leftNode = leftNode.left;

			Node upLeft = leftNode.up;
			upNewNode.right = upLeft.right;
			upNewNode.left = upLeft;
			upLeft.right.left = upNewNode;
			upLeft.right = upNewNode;
		}

		this.size++;
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
