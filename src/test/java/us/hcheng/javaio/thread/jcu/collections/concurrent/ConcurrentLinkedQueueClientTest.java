package us.hcheng.javaio.thread.jcu.collections.concurrent;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.concurrent.ConcurrentLinkedQueueClient;

public class ConcurrentLinkedQueueClientTest {

	/**
	 * this is NOT a BLOCKING QUEUE!!!
	 */
	private static ConcurrentLinkedQueue<Long> queue;

	@BeforeAll
	static void init() {
		queue = ConcurrentLinkedQueueClient.getInstance();
	}

	@BeforeEach
	void setup() {
		IntStream.range(0, 10000).forEach(i -> queue.offer(System.nanoTime()));
	}

	@AfterEach
	void tearDown() {
		queue.clear();
	}

	@Test
	void isEmptyTest() {
		long start = System.nanoTime();
		while (!queue.isEmpty())
			assertNotNull(queue.poll());

		System.out.println("isEmptyTest: " + (System.nanoTime() - start));
	}

	@Test
	void sizeTest() {
		long start = System.nanoTime();
		while (queue.size() > 0)
			assertNotNull(queue.poll());

		System.out.println("sizeTest: " + (System.nanoTime() - start));
	}

}
