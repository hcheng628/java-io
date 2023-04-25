package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.utils.SleepUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

class SynchronousQueueClientTest {

    private static SynchronousQueue<Integer> queue;

    @BeforeAll
    static void init() {
        queue = SynchronousQueueClient.getInstance();
    }

    /**
     * In fact, clear does not do anything as there is no
     * storage allocated for this queue[transfer! remember?]
     */
    @AfterEach
    void tearDown() {
        queue.clear();
    }

    @Test
    void insertsTest() throws InterruptedException {
        assertThrows(NullPointerException.class, () -> {
            queue.put(null);
        });

        assertFalse(queue.offer(1));

        setConsumer(1);
        SleepUtil.sleepSec(2);
        assertTrue(queue.offer(1));

        setConsumer(1);
        SleepUtil.sleepSec(2);
        assertTrue(queue.add(2));

        setConsumer(1);
        SleepUtil.sleepSec(2);
        queue.put(3);

        assertEquals(0, queue.size());
        assertEquals(0, queue.remainingCapacity());
    }

    @Test
    void removesTest() throws InterruptedException {
        /**
         * always null
         */
        assertNull(queue.peek());

        assertThrows(NoSuchElementException.class, () -> {
            /**
             * calls peek() which is always null
             * so NoSuchElementException is thrown
             */
            queue.element();
        });

        assertTrue(queue.isEmpty());

        int val = 100;
        setProducer(1, val);
        assertEquals(val, queue.poll(2, TimeUnit.SECONDS));
        assertNull(queue.poll());

        /**
         * always returns false
         */
        assertFalse(queue.remove(new Object()));

        assertThrows(NoSuchElementException.class, () -> {
            queue.remove();
        });

        val = 200;
        setProducer(0, val);
        SleepUtil.sleepSec(1);
        assertEquals(val, queue.remove());

        val = 300;
        setProducer(0, val);
        SleepUtil.sleepSec(1);
        assertEquals(val, queue.take());
    }

    /**
     * contains() methods always returns false
     */
    @Test
    void containsTest() {
        assertFalse(queue.contains(1));
        assertFalse(queue.contains(List.of()));
    }

    @Test
    void drainToTest() {
        int end = 10;
        List<Integer> list = new ArrayList<>();

        IntStream.range(0, end).forEach(i -> {
            setProducer(0, i);
        });

        /**
         * in multiple threads putting case, \
         * drainTo() it can get more than one
         *
         */
        IntStream.range(0, end).forEach(i -> {
            SleepUtil.sleepSec(1);
            queue.drainTo(list, 3);
        });

        assertEquals(end, list.size());
    }

    void setConsumer(int amt) {
        Executors.newScheduledThreadPool(1).schedule(() -> {
            try {
                System.out.println(String.join(":",
                        "Consumed", String.valueOf(queue.take())));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, amt, TimeUnit.SECONDS);
    }

    void setProducer(int amt, int val) {
        Executors.newScheduledThreadPool(1).schedule(() -> {
            try {
                queue.put(val);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, amt, TimeUnit.SECONDS);
    }

}
