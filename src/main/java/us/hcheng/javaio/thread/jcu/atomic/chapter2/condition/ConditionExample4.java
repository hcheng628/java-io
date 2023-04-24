package us.hcheng.javaio.thread.jcu.atomic.chapter2.condition;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionExample4 {

    private static final ReentrantLock LOCK = new ReentrantLock(true);
    private static final Condition PRODUCER_CONDITION = LOCK.newCondition();
    private static final Condition CONSUMER_CONDITION = LOCK.newCondition();
    private static final int MAX_QUEUE_SIZE = 100;
    private static final LinkedList<Long> QUEUE = new LinkedList<>();

    public static void main(String[] args) {
        createConsumers(12);
        createProducers(6);
    }

    private static void createConsumers(int size) {
        IntStream.range(0, size).forEach(i -> {
            new Thread(() -> {
                while (true)
                    produceData();
            }).start();
        });
    }

    private static void createProducers(int size) {
        IntStream.range(0, size).forEach(i -> {
            new Thread(() -> {
                while (true)
                    consumeData();
            }).start();
        });
    }

    private static void produceData() {
        Thread t = Thread.currentThread();
        try {
            LOCK.lock();
            while (QUEUE.size() >= MAX_QUEUE_SIZE)
                PRODUCER_CONDITION.await();
            SleepUtil.sleep(10);
            long data = System.currentTimeMillis();
            QUEUE.addLast(data);
            System.out.println(t.getName().charAt(7) + "-Produced-" + data + "\n" + lockInfo(CONSUMER_CONDITION));
            CONSUMER_CONDITION.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    private static void consumeData() {
        Thread t = Thread.currentThread();
        try {
            LOCK.lock();
            while (QUEUE.isEmpty())
                CONSUMER_CONDITION.await();
            SleepUtil.sleep(1500);
            long data = QUEUE.removeFirst();
            System.err.println(t.getName().charAt(7) + "-Consumed-" + data + "\n" + lockInfo(CONSUMER_CONDITION));
            PRODUCER_CONDITION.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    private static String lockInfo(Condition condition) {
        StringBuilder sb = new StringBuilder();
        sb.append("QUEUE Size: ").append(QUEUE.size());
        sb.append("\nHold Count: ").append(LOCK.getHoldCount());
        sb.append("\nQueue Length: ").append(LOCK.getQueueLength());
        sb.append("\nHas Waiters: ").append(LOCK.hasWaiters(condition));
        sb.append("\nHas Waiters: ").append(LOCK.getWaitQueueLength(condition)).append("\n");
        return sb.toString();
    }

}
