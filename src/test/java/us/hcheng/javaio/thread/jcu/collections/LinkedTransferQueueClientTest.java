package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedTransferQueueClientTest {

    private static LinkedTransferQueue<Integer> queue;

    @BeforeAll
    static void init() {
        queue = LinkedTransferQueueClient.getInstance();
    }

    @AfterEach
    void tearDown() {
        queue.clear();
    }

    /**
     * add(), put(), and offer() call xfer[ASYNC]
     * transfer() calls xfer[SYNC]
     */
    @Test
    void insertsTest() throws InterruptedException {
        assertFalse(queue.tryTransfer(1));

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            try {
                assertEquals(1, queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);
        service.shutdown();

        queue.transfer(1);

        service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            try {
                queue.tryTransfer(10, 2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);
        service.shutdown();
        assertEquals(10, queue.take());
    }

    @Test
    void removesTest() {
        IntStream.range(0, 5).forEach(i -> {
            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            service.schedule(() -> {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 1, TimeUnit.SECONDS);
        });

        SleepUtil.sleepSec(2);
        assertTrue(queue.hasWaitingConsumer());

        /**
         * this number is an estimate not accurate!
         */
        System.out.println("WaitingConsumerCount: " + queue.getWaitingConsumerCount());
        assertTrue(queue.getWaitingConsumerCount() > 3);
        IntStream.range(0, 5).forEach(i -> assertTrue(queue.tryTransfer(i)));

        assertTrue(queue.isEmpty());
    }

    @Test
    void transferTest() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();

        IntStream.range(0, 2).forEach(i -> {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        list.add(queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        });

        SleepUtil.sleepSec(1);

        IntStream.range(0, 3).forEach(i -> {
            new Thread(() -> putStuff()).start();
            Thread t = new Thread(() -> transferStuff(100 * (i + 1)));
            t.setDaemon(true);
            t.start();
        });

        while (list.size() != 6 * 3)
            SleepUtil.sleepSec(1);

        assertTrue(queue.isEmpty());
    }

    @Test
    void sizeTest() throws InterruptedException {
        int size = 10;
        List<Thread> tasks = new ArrayList<>();

        IntStream.range(0, size).forEach(i -> {
            Thread t = new Thread(() -> transferStuff(i * 10));
            t.setDaemon(true);
            tasks.add(t);
        });
        tasks.forEach(t -> t.start());

        SleepUtil.sleepSec(3);
        assertEquals(size, queue.size());
    }


    void putStuff() {
        IntStream.range(0, 5).forEach(i -> {
            queue.put(ThreadLocalRandom.current().nextInt(100));
        });
    }

    void transferStuff(int val) {
        try {
            queue.transfer(val);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
