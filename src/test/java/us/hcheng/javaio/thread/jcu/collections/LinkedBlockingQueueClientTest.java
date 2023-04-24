package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

class LinkedBlockingQueueClientTest {

    private final static int SIZE = 5;
    private static LinkedBlockingQueue<Integer> blockingQueue;
    private static LinkedBlockingQueue<Integer> blockingQueueSized;

    @BeforeAll
    static void init() {
        blockingQueue = LinkedBlockingQueueClient.getInstance();
        blockingQueueSized = LinkedBlockingQueueClient.getInstance(SIZE);
    }

    @AfterEach
    void tearDown() {
        blockingQueue.clear();
        blockingQueueSized.clear();
    }

    @Test
    void insertsTest() throws InterruptedException {
        IntStream.range(0, SIZE).forEach(i -> {
            assertTrue(blockingQueue.add(i));
            assertTrue(blockingQueueSized.add(i));
        });

        assertEquals(5, blockingQueue.size());
        assertEquals(5, blockingQueueSized.size());

        /**
         * when full
         * add() throws IllegalStateException
         * offer() just returns false
         */
        assertTrue(blockingQueue.add(100));
        assertThrows(IllegalStateException.class, () -> {
            blockingQueueSized.add(100);
        });

        assertTrue(blockingQueue.offer(100));
        assertFalse(blockingQueueSized.offer(100));

        Executors.newScheduledThreadPool(1).schedule(() -> {
            try {
                blockingQueueSized.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);

        blockingQueue.put(100);
        blockingQueueSized.put(100);

        assertEquals(SIZE, blockingQueueSized.size());
        assertTrue(blockingQueue.size() > SIZE);
    }


    @Test
    void deletes() throws InterruptedException {
        assertThrows(NoSuchElementException.class, () -> {
            blockingQueueSized.element();
        });

        IntStream.range(0, SIZE).forEach(i -> {
            assertTrue(blockingQueue.add(i));
            assertTrue(blockingQueueSized.add(i));
        });
        assertEquals(blockingQueueSized.element(), blockingQueue.peek());

        IntStream.range(0, SIZE - 3).forEach(i -> {
            assertEquals(blockingQueueSized.poll(), blockingQueue.poll());
        });
        assertEquals(blockingQueueSized.size(), blockingQueue.size());

        blockingQueueSized.clear();
        blockingQueue.clear();
        assertEquals(blockingQueueSized.size(), blockingQueue.size());

        Executors.newScheduledThreadPool(1).schedule(() -> {
            blockingQueueSized.offer(100);
        }, 1, TimeUnit.SECONDS);
        assertEquals(100, blockingQueueSized.take());
    }

}
