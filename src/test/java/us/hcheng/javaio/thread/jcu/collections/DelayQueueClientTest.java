package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.collections.entity.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class DelayQueueClientTest {

    private static DelayQueue<Container<String>> queue;

    @BeforeAll
    static void init() {
        queue = DelayQueueClient.getInstance();
    }

    @AfterEach
    void tearDown() {
        queue.clear();
    }

    /**
     * put() and add() both call offer()
     */
    @Test
    void insertsTest() {
        Container<String> jordan = Container.of("Jordan", 1965);
        queue.add(Container.of("Cheng", 1989));
        queue.offer(jordan);
        queue.put(Container.of("Moka", 2020));
        assertEquals(3, queue.size());

        /**
         * if no expired elements are available in the queue,
         * this method returns the element that will expire next
         */
        assertEquals(jordan, queue.peek());
    }

    @Test
    void removesTest() throws InterruptedException {
        Container<String> jordan = Container.of("Jordan", 1965);
        Container<String> moka = Container.of("Moka", 2020);
        Container<String> cheng = Container.of("Cheng", 11989);
        queue.add(cheng);
        queue.offer(jordan);
        queue.put(moka);

        assertTrue(queue.remove(moka));
        assertEquals(2, queue.size());

        /**
         * element is not ready yet
         *
         * remove() calls poll() and throws NoSuchElementException
         * when empty or nothing is ready
         */
        assertThrows(NoSuchElementException.class, () -> queue.remove());

        assertEquals(jordan, queue.poll(2, TimeUnit.SECONDS));

        /**
         * only take() will wait till it gets it ;-)
         */
        assertEquals(cheng, queue.take());
        assertTrue(queue.isEmpty());

        queue.offer(Container.of("Haha", -1));
        assertEquals(Container.of("Haha", -1), queue.take());
    }


    @Test
    void iteratorTest() throws InterruptedException {
        Container<String> jordan = Container.of("Jordan", 1965);
        Container<String> moka = Container.of("Moka", 2020);
        Container<String> cheng = Container.of("Cheng", 1989);
        queue.add(cheng);
        queue.offer(jordan);
        queue.put(moka);
        assertEquals(3, queue.size());

        /**
         * Iterator order might not the real order by getDelay()
         * only take() populates the list is the real correct order
         */
        List<Container<String>> list = new ArrayList<>();
        Iterator<Container<String>> it = queue.iterator();
        while (it.hasNext())
            list.add(it.next());

        assertEquals(3, list.size());

        for (int i = 0; i < 3; i++)
            if (i == 0)
                assertEquals(jordan, queue.take());
            else if (i == 1)
                assertEquals(cheng, queue.take());
            else
                assertEquals(moka, queue.take());
    }

}
