package us.hcheng.javaio.thread.jcu.atomic.chapter2.condition;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;
import java.util.stream.IntStream;

public class ConditionExample3Sync {
    private static final Object MONITOR = new Object();
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
        synchronized (MONITOR) {
            try {
                while (produced)
                    MONITOR.wait();
                SleepUtil.sleep(1000);
                data++;
                System.out.println(t + "-Produced-" + data);
                produced = true;
                MONITOR.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumeData() {
        Thread t = Thread.currentThread();
        synchronized (MONITOR) {
            try {
                while (!produced)
                    MONITOR.wait();
                SleepUtil.sleep(2000);
                System.err.println(t + "-Consumed-" + data);
                produced = false;
                MONITOR.notifyAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
