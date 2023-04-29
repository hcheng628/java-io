package us.hcheng.javaio.thread.jcu.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LinkedBlockingDequeClientTest {

	private static LinkedBlockingDeque<Integer> queue;
	private final int SIZE = 10;

	@BeforeAll
	static void init() {
		queue = LinkedBlockingDequeClient.getInstance();
	}

	@AfterEach
	void tearDown() {
		queue.clear();
	}

	/**
	 * add() calls addLast
	 */
	@Test
	void insertsTest() {
		IntStream.range(0, SIZE).forEach(i -> queue.addFirst(i));
		assertEquals(SIZE, queue.size());

		IntStream.range(0, SIZE).map(i -> SIZE - i - 1)
				.forEach(i -> assertEquals(i, queue.remove()));

		assertTrue(queue.isEmpty());
	}


	/**
	 * remove() calls removeFirst
	 */
	@Test
	void removesTest() {
		IntStream.range(0, SIZE).forEach(i -> queue.add(i));
		assertEquals(SIZE, queue.size());
		IntStream.range(0, SIZE).map(i -> SIZE - i - 1)
				.forEach(i -> assertEquals(i, queue.removeLast()));

		assertTrue(queue.isEmpty());

		assertNull(queue.poll());
		assertThrows(NoSuchElementException.class, () -> {
			queue.removeFirst();
		});
	}

	@Test
	void takeTest() throws InterruptedException {
		IntStream.range(0, SIZE).forEach(i -> queue.add(i));

		while (!queue.isEmpty())
			assertEquals(SIZE - 1, queue.takeFirst() + queue.takeLast());

		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		long start = System.currentTimeMillis();
		service.schedule(() -> {
			queue.offer(100);
		}, 1, TimeUnit.SECONDS);
		service.shutdown();

		assertEquals(100, queue.takeFirst());
		assertTrue(System.currentTimeMillis() - start >= 1000);
	}

}
