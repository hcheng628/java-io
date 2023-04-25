package us.hcheng.javaio.thread.jcu.atomic.chapter2.condition;

import us.hcheng.javaio.utils.SleepUtil;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionExample1 {

    private static final ReentrantLock LOCK = new ReentrantLock(true);
    private static final Condition CONDITION = LOCK.newCondition();
    private static volatile boolean produced;
    private static volatile int data;

    public static void main(String[] args) {
        createConsumers(3);
        createProducers(3);
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
            while (produced)
                CONDITION.await();
            SleepUtil.sleep(1000);
            data++;
            System.out.println(t + "-Produced-" + data + "\n" + lockInfo());
            produced = true;
            CONDITION.signalAll();
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
            while (!produced)
                CONDITION.await();
            SleepUtil.sleep(2000);
            System.out.println(t + "-Consumed-" + data + "\n" + lockInfo());
            produced = false;
            CONDITION.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    private static String lockInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hold Count: ").append(LOCK.getHoldCount());
        sb.append("\nQueue Length: ").append(LOCK.getQueueLength());
        sb.append("\nHas Waiters: ").append(LOCK.hasWaiters(CONDITION));
        sb.append("\nHas Waiters: ").append(LOCK.getWaitQueueLength(CONDITION)).append("\n");
        return sb.toString();
    }

}
