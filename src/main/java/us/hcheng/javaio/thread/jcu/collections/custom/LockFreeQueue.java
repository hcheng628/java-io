package us.hcheng.javaio.thread.jcu.collections.custom;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class LockFreeQueue<E> {

    private static class Node<E> {
        private E val;
        private Node<E> next;

        public Node(E val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val == null ? "NULL" : val.toString();
        }

    }

    private AtomicInteger size;
    private AtomicReference<Node<E>> head, tail;

    public LockFreeQueue() {
        size = new AtomicInteger();
        head = new AtomicReference<>();
        tail = new AtomicReference<>();

        Node<E> dummy = new Node<>(null);
        head.set(dummy);
        tail.set(dummy);
    }

    public void addLast(E val) {
        if (val == null)
            throw new IllegalArgumentException("cannot insert null values");

        Node<E> newNode = new Node<>(val);
        Node<E> currTail = tail.getAndSet(newNode);
        currTail.next = newNode;
        size.incrementAndGet();
    }

    public E removeFirst() {
        E ret = null;
        Node<E> curr = null;

        do {
            curr = head.get();
            if (curr.next != null) {
                ret = curr.next.val;
            } else {
                ret = null;
                break;
            }
        } while (!this.head.compareAndSet(curr, curr.next));

        if (ret != null)
            size.decrementAndGet();

        return ret;
    }

    public boolean isEmpty() {
        return size.get() == 0;
    }

    public static void main(String[] args) throws InterruptedException {
        final ConcurrentHashMap<Long, Object> data = new ConcurrentHashMap<>();
        final LockFreeQueue<Long> queue = new LockFreeQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 5).boxed().map(l -> (Runnable) () -> {
            int counter = 0;
            while ((counter++) <= 10) {
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.addLast(System.nanoTime());
            }
        }).forEach(service::submit);

        IntStream.range(0, 5).boxed().map(l -> (Runnable) () -> {
            int counter = 10;
            while (counter >= 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Long value = queue.removeFirst();
                if (value == null) continue;
                counter--;
                System.out.println(value);
                data.put(value, new Object());
            }
        }).forEach(service::submit);

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);

        System.out.println(data.size());
        System.out.println("=================");
        System.out.println(data.keys());
    }
}
