package us.hcheng.javaio.thread.jcu.collections.concurrent;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import us.hcheng.javaio.thread.jcu.concurrent.CopyOnWriteArrayListClient;

public class CopyOnWriteArrayListClientTest {

	private static CopyOnWriteArrayList<Long> list;

	@BeforeAll
	static void init() {
		list = CopyOnWriteArrayListClient.getInstance();
	}

	@Test
	void addsPerformanceTest() {
		final int size = 10000;

		long t1 = addBulk(new ArrayList<>(), size);
		long t2 = addBulk(list, size);

		System.out.println("Time:\n" + t1 + "[ArrayList]\n" + t2 + "[CopyOnWriteArrayList]");

		assertTrue(t1 < t2);
	}

	long addBulk(List<Long> list, int size) {
		long start = System.nanoTime();

		IntStream.range(0, size).forEach(i -> {
			list.add(System.nanoTime());
		});

		return System.nanoTime() - start;
	}

}
