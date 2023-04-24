package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleSkipList {
	protected final static byte HEAD_BIT = (byte) -1;
	protected final static byte DATA_BIT = (byte) 0;
	protected final static byte TAIL_BIT = (byte) 1;

	private final static ThreadLocalRandom RAND = ThreadLocalRandom.current();

	private int size;
	private int level;
	private Node head;
	private Node tail;


	public SimpleSkipList() {
		this.head = new Node.Builder().setHead().build();
		this.tail = new Node.Builder().setTail().build();
		this.head.right = this.tail;
		this.tail.left = this.head;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void add(Integer val) {
		Node newNode = new Node.Builder().setData().setValue(val).build();
		Node nearNode = this.findClosed(val);

		newNode.left = nearNode;
		newNode.right = nearNode.right;
		nearNode.right = newNode;
		newNode.right.left = newNode;

		for (int currLevel = 0; currLevel <= this.level; currLevel++) {
			Node newUpNode = new Node.Builder().setData().setValue(val).build();
			if (currLevel >= this.level && RAND.nextDouble() < 0.5D) {
				Node topHead = new Node.Builder().setHead().build();
				Node topTail = new Node.Builder().setTail().build();

				this.head.up = topHead;
				topHead.down = this.head;
				this.tail.up = topTail;
				topTail.down = this.tail;
				this.head = topHead;
				this.tail = topTail;

				topHead.right = newUpNode;
				newUpNode.left = topHead;
				newUpNode.right = topTail;
				topTail.left = newUpNode;
				newUpNode.down = newNode;
				newNode.up = newUpNode;
				this.level++;
				break;
			} else {
				Node upLeft = newNode.left.up;
				if (upLeft != null) {
					/*
					System.out.println("newNode.left: " + newNode.left);
					System.out.println("newNode.left.up: " + newNode.left.up);
					System.out.println("upLeft.left: " + upLeft.left + " upLeft: " + upLeft + " upLeft.right: " + upLeft.right);
					*/
					newUpNode.left = upLeft;
					newUpNode.right = upLeft.right;
					upLeft.right = newUpNode;
					newUpNode.right.left = newUpNode;
					newUpNode.down = newNode;
					newNode.up = newUpNode;
				} else
					break;
			}

			newNode = newUpNode;
		}

		this.size++;
	}

	public Node get(Integer val) {
		Node n = this.findClosed(val);
		return n.value == val ? n : null;
	}

	public boolean contains(Integer val) {
		Node n = this.findClosed(val);
		return n.value == val;
	}

	public Node findClosed(Integer val) {
		Node curr = this.head;

		while (true) {
			//System.out.println("NOW CURR: " + curr + " curr.right: " + curr.right + " COMING-VAL: " + val);
			if (curr.right.type != TAIL_BIT && curr.right.value <= val) {
				//System.out.println("GOING RIGHT");
				curr = curr.right;
			} else {
				if (curr.down == null)
					break;
				else {
					//System.out.println("GOING DOWN");
					curr = curr.down;

					//this.dumpSkipList();
				}
			}
		}

		return curr;
	}

	public void dumpSkipList() {
		Node currHead = this.head;
		int l = 0;

		while (currHead != null) {
			Node nextHead = currHead.down;

			System.out.print("level " + l + " out of " + this.level + " head->");
			while (currHead.type != TAIL_BIT) {
				if (currHead.type == DATA_BIT)
					System.out.print(currHead.value + "->");
				currHead = currHead.right;
			}
			System.out.print("tail\n");
			currHead = nextHead;
			l++;
		}
	}
}

class Node {
	protected Node up, left, down, right;
	protected Integer value;
	protected byte type;

	private Node(Builder builder) {
		this.value = builder.value;
		this.type = builder.type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.type == SimpleSkipList.DATA_BIT)
			sb.append("DATA[").append(this.value).append("]");
		else if (this.type == SimpleSkipList.HEAD_BIT)
			sb.append("HEAD");
		else
			sb.append("TAIL");

		return sb.toString();
	}

	public static class Builder {
		private Integer value;
		private byte type;

		public Builder setValue(Integer value) {
			this.value = value;
			return this;
		}

		public Builder setHead() {
			this.type = SimpleSkipList.HEAD_BIT;
			return this;
		}

		public Builder setTail() {
			this.type = SimpleSkipList.TAIL_BIT;
			return this;
		}

		public Builder setData() {
			this.type = SimpleSkipList.DATA_BIT;
			return this;
		}

		public Node build() {
			return new Node(this);
		}
	}

}
