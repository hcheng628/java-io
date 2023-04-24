package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.collections.entity.Person;
import java.util.NoSuchElementException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is very much like ArrayBlockingQueue but
 * 1. Size is dynamic
 * 2. Sorted per Comparable
 */
class PriorityBlockingQueueClientTest {

    private static PriorityBlockingQueue<Person> blockingQueue;

    @BeforeAll
    static void init() {
        blockingQueue = PriorityBlockingQueueClient.getInstance();
    }

    @AfterEach
    void tearDown() {
        blockingQueue.clear();
    }

    /**
     * add() and put() calls offer()
     * if no exceptions, it will always return true
     */
    @Test
    void addsNPutNofferTest() {
        blockingQueue.add(new Person("Moka", 4));
        blockingQueue.put(new Person("Cheng", 33));

        assertEquals(2, blockingQueue.size());

        blockingQueue.addAll(Stream.of(new Person("Apple", 33),
                new Person("ThinkPad", 50)).toList());

        assertEquals(4, blockingQueue.size());

        assertThrows(NullPointerException.class, () -> {
            blockingQueue.offer(null);
        });
    }

    @Test
    void peekNPollTest() {
        blockingQueue.add(new Person("Moka", 4));
        blockingQueue.put(new Person("Cheng", 33));
        blockingQueue.addAll(Stream.of(new Person("Apple", 33),
                new Person("ThinkPad", 50)).toList());

        assertEquals("Moka", blockingQueue.peek().getName());
        assertEquals("Moka", blockingQueue.poll().getName());

        assertEquals(3, blockingQueue.size());
    }

    @Test
    void removesTest() {
        blockingQueue.add(new Person("Moka", 4));
        blockingQueue.put(new Person("Cheng", 33));
        blockingQueue.addAll(Stream.of(new Person("Apple", 33),
                new Person("ThinkPad", 50)).toList());

        assertTrue(blockingQueue.remove(new Person("Moka", 4)));
        assertFalse(blockingQueue.remove(new Person("moka", 5)));
        assertTrue(blockingQueue.removeAll(
                Stream.of(new Person("Moka", 4),
                        new Person("Apple", 33)).
                        toList()));

        assertEquals(2, blockingQueue.size());
        assertTrue(Integer.MAX_VALUE - blockingQueue.remainingCapacity() < 5);
    }

    /**
     * element() calls peek() and if null,
     * NoSuchElementException is thrown
     */
    @Test
    void elementTest() {
        Person moka = new Person("Moka", 4);
        blockingQueue.add(moka);
        blockingQueue.put(new Person("Cheng", 33));
        blockingQueue.addAll(Stream.of(new Person("Apple", 33),
                new Person("ThinkPad", 50)).toList());

        assertEquals(moka, blockingQueue.element());
        blockingQueue.clear();

        assertThrows(NoSuchElementException.class, () -> {
            blockingQueue.element();
        });
    }

}
