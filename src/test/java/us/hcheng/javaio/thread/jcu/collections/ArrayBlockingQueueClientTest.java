package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Spliterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class ArrayBlockingQueueClientTest {

    private static final int SIZE = 5;
    private static ArrayBlockingQueue<Integer> blockingQueue;

    @BeforeAll
    static void init() {
        blockingQueue = ArrayBlockingQueueClient.getInstance(SIZE);
    }

    @AfterEach
    void tearDown() {
        blockingQueue.clear();
    }

    /**
     * add():
     * IllegalStateException throw when queue is full
     * add() calls offer()
     */
    @Test
    void addTest() {
        IntStream.range(0, SIZE).forEach(i -> {
            assertTrue(blockingQueue.add(i));
        });

        assertThrows(IllegalStateException.class, () -> {
            blockingQueue.add(100);
        });

        assertEquals(5, blockingQueue.size());
    }

    /**
     * offer():
     * returns false when queue is full
     */
    @Test
    void offerTest() {
        IntStream.range(0, SIZE).forEach(i -> {
            assertTrue(blockingQueue.offer(i));
        });

        assertFalse(blockingQueue.offer(100));
        assertEquals(5, blockingQueue.size());
    }

    /**
     * Inserts the specified element at the tail of this queue,
     * waiting for space to become available if the queue is full.
     *
     * !!!BLOCKING!!!
     */
    @Test
    void putTest() throws InterruptedException {
        IntStream.range(0, SIZE).forEach(i -> {
            try {
                blockingQueue.put(i);
            } catch (InterruptedException ex){}
        });
        assertEquals(5, blockingQueue.size());

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            Integer val = null;
            try {
                val = blockingQueue.take();
            } catch (InterruptedException e) {}

            assertEquals(0, val);
        }, 2, TimeUnit.SECONDS);

        blockingQueue.put(13);
        assertEquals(5, blockingQueue.size());
    }

    /**
     * If full IllegalStateException will be thrown as add()
     */
    @Test
    void addAllTest() {
        assertThrows(IllegalStateException.class, () -> {
            blockingQueue.addAll(Stream.of(1, 2, 3, 4, 5, 6).toList());
        });
        assertEquals(5, blockingQueue.size());
    }

    @Test
    void clearTest() {
        assertEquals(SIZE, blockingQueue.remainingCapacity());

        assertThrows(IllegalStateException.class, () -> {
            blockingQueue.addAll(Stream.of(1, 2, 3, 4, 5, 6).toList());
        });
        assertEquals(5, blockingQueue.size());
        assertEquals(0, blockingQueue.remainingCapacity());

        blockingQueue.clear();
        assertEquals(0, blockingQueue.size());
    }

    @Test
    void pollTest() {
        assertNull(blockingQueue.poll());

        blockingQueue.addAll(Stream.of(1, 2, 3, 4, 5).toList());

        IntStream.range(0, SIZE).forEach(i -> {
            assertNotNull(blockingQueue.poll());
        });

        assertNull(blockingQueue.poll());
        assertNull(blockingQueue.peek());
    }

    @Test
    void pollTimeoutTest() {
        assertNull(blockingQueue.poll());

        Executors.newScheduledThreadPool(1)
                .schedule(() -> blockingQueue.add(100), 1, TimeUnit.SECONDS);

        assertNull(blockingQueue.peek());
        assertNull(blockingQueue.poll());

        int res = 0;
        try {
            res = blockingQueue.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException ex){}
        assertEquals(100, res);
    }

    @Test
    void removesTest() {
        IntStream.range(0, SIZE).forEach(i -> blockingQueue.add(i));
        assertTrue(blockingQueue.remove(1));
        blockingQueue.add(3);

        assertTrue(blockingQueue.remove(3));
        assertTrue(blockingQueue.remove(3));

        assertFalse(blockingQueue.remove(100));

        assertEquals(0, blockingQueue.remove());
        assertEquals(2, blockingQueue.size());

        assertTrue(blockingQueue.removeIf(integer -> integer == 2 || integer == 4));
        assertEquals(0, blockingQueue.size());

        blockingQueue.add(3);
        blockingQueue.add(6);
        blockingQueue.add(9);

        assertFalse(blockingQueue.removeAll(Stream.of(1).toList()));
        assertTrue(blockingQueue.removeAll(Stream.of(3, 9).toList()));
        assertEquals(1, blockingQueue.size());
    }

    @Test
    void drainTosTest() {
        IntStream.range(0, SIZE).forEach(i -> blockingQueue.add(i));
        Collection<Integer> list = new ArrayList<>();
        int res = blockingQueue.drainTo(list);
        assertEquals(5, res);
        assertEquals(5, list.size());

        blockingQueue.addAll(list);
        System.out.println(blockingQueue);

        res = blockingQueue.drainTo(list, 2);
        assertEquals(2, res);

        assertEquals(2, blockingQueue.poll());
        assertEquals(2, blockingQueue.size());

        res = blockingQueue.drainTo(list, 100);
        assertEquals(2, res);
    }


    @Test
    void spliteratorTest() {
        IntStream.range(0, SIZE).forEach(i -> blockingQueue.add(i));
        System.out.println(blockingQueue);

        Spliterator<Integer> spliterator = blockingQueue.spliterator();
        System.out.println(spliterator.estimateSize());
    }

}
