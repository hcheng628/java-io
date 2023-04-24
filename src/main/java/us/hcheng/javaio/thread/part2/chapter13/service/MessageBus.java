package us.hcheng.javaio.thread.part2.chapter13.service;

import us.hcheng.javaio.thread.part2.chapter13.entity.Job;

import java.util.LinkedList;

public class MessageBus<T> implements IMessageBus {

    private final LinkedList<Job<T>> queue;
    private static final int DEFAULT_SIZE_LIMIT = 100;
    private final int maxSize;

    public MessageBus() {
        this(DEFAULT_SIZE_LIMIT);
    }

    public MessageBus(int size) {
        this.maxSize = size;
        queue = new LinkedList<>();
    }

    @Override
    public Job take() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty())
                queue.wait();
            Job ret = queue.removeFirst();
            queue.notifyAll();
            return ret;
        }
    }

    @Override
    public void put(Job job) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() + 1 >= maxSize)
                queue.wait();
            queue.addLast(job);
            queue.notifyAll();
        }
    }

    public int getSizeLimit() {
        return this.maxSize;
    }

    public int currQueueSize() {
        synchronized (queue) {
            return queue.size();
        }
    }

    @Override
    public String toString() {
        return "MessageBus{" +
                "queue=" + queue +
                '}';
    }
}
