package us.hcheng.javaio.thread.jcu.collections.custom;

public class MyQueue<E> {

    private static class Node<E> {
        private E val;
        private Node<E> next;

        public E getVal() {
            return val;
        }

        public Node(E val) {
            this.val = val;
        }

        public void setVal(E val) {
            this.val = val;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }

    }


    private int size;
    private Node<E> first, last;

    private final boolean threadSafe;
    private final Object lock = new Object();

    public MyQueue() {
        this(false);
    }

    public MyQueue(boolean threadSafe) {
        this.threadSafe = threadSafe;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size < 1;
    }

    public void addLast(E val) {
        if (threadSafe)
            synchronized (lock) {
                addLast0(val);
            }
        else
            addLast0(val);
    }

    private void addLast0(E val) {
        Node n = new Node(val);

        if (this.size == 0)
            first = n;
        else
            last.next = n;
        last = n;
        this.size++;
    }

    public E removeFirst() {
        if (threadSafe)
            synchronized (lock) {
                return removeFirst0();
            }
        else
            return removeFirst0();
    }

    private E removeFirst0() {
        if (this.isEmpty())
            return null;

        E ret = first.val;

        first = first.next;
        if (this.size == 1)
            last = null;

        this.size--;
        return ret;
    }

    public E peekFirst() {
        return this.last == null ? null : this.last.val;
    }

    public E peekLast() {
        return this.first == null ? null : this.first.val;
    }

    public void clear() {
        if (threadSafe)
            synchronized (lock) {
                clear0();
            }
        else
            clear0();
    }

    private void clear0() {
        this.first = this.last = null;
        this.size = 0;
    }

    public boolean isThreadSafe() {
        return this.threadSafe;
    }

}
