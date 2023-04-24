package us.hcheng.javaio.thread.jcu.collections.entity;

import lombok.Data;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleSkipList {

    private static final byte HEAD = (byte) -1;
    private static final byte DATA = (byte) 0;
    private static final byte TAIL = (byte) 1;

    private int level;
    private int size;
    private Node head;
    private Node tail;
    private Random random;

    public SimpleSkipList() {
        this.head = new Node(HEAD);
        this.tail = new Node(TAIL);
        head.right = tail;
        tail.left = head;
        this.random = new Random(System.currentTimeMillis());
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean contains(int val) {
        Node n = this.findClosest(val);
        return n.getVal() == val;
    }

    public void add(int element) {
        Node nearNode = this.findClosest(element);
        Node newNode = new Node(element);
        newNode.left = nearNode;
        newNode.right = nearNode.right;
        nearNode.right.left = newNode;
        nearNode.right = newNode;

        int currentLevel = 0;
        while (random.nextDouble() < 0.5d) {
            if (currentLevel >= level) {
                level++;

                Node dumyHead = new Node(HEAD);
                Node dumyTail = new Node(TAIL);
                dumyHead.right = dumyTail;
                dumyHead.down = head;
                head.up = dumyHead;

                dumyTail.left = dumyHead;
                dumyTail.down = tail;
                tail.up = dumyTail;

                head = dumyHead;
                tail = dumyTail;
            }

            while ((nearNode != null) && nearNode.up == null) {
                nearNode = nearNode.left;
            }
            nearNode = nearNode.up;

            Node upNode = new Node(element);
            upNode.left = nearNode;
            upNode.right = nearNode.right;
            upNode.down = newNode;

            nearNode.right.left = upNode;
            nearNode.right = upNode;

            newNode.up = upNode;
            newNode = upNode;
            currentLevel++;
        }

        size++;
    }

    private Node findClosest(int val) {
        Node curr = this.head;

        for (;;)
            if (curr.right.getTYPE() == DATA && curr.right.getVal() <= val)
                curr = curr.right;
            else
                if (curr.down == null)
                    break;
                else
                    curr = curr.down;

        return curr;
    }

    public void dumpList() {
        Node curr = this.head;

        for (int currLevel = 0; currLevel <= level; currLevel++) {
            Node next = curr.down;

            System.out.print(currLevel + " ouf of " + this.level + "[HEAD| ");
            for (; curr.right.TYPE != TAIL; curr = curr.right)
                System.out.print(curr.right.val + "->");
            System.out.print("|TAIL]");
            System.out.println();

            curr = next;
        }
    }

    @Data
    class Node {
        private Integer val;
        private final byte TYPE;
        private Node up, left, down, right;

        public Node(Integer val) {
            this.val = val;
            this.TYPE = DATA;
        }

        public Node(byte type) {
            if (type != HEAD && type != TAIL)
                throw new IllegalArgumentException("It could only be HEAD or TAIL");
            this.TYPE = type;
        }
    }

    public static void main(String[] args) {
        SimpleSkipList skipList = new SimpleSkipList();
        skipList.add(10);
        //skipList.dumpList();

        skipList.add(1);
        //skipList.dumpList();

        for (int i = 0; i < 50; i++)
            skipList.add(ThreadLocalRandom.current().nextInt(100));

        System.out.println(skipList.contains(10) && skipList.contains(1));
        skipList.dumpList();
    }
}
