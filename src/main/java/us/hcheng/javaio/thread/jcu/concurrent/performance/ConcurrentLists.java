package us.hcheng.javaio.thread.jcu.concurrent.performance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConcurrentLists {

	public static void main(String[] args) {
		Map<String, Double> map = new TreeMap<>();

		IntStream.range(1, 10).forEach(numOfThreads ->
				Stream.of(true, false).forEach(yes ->
						Stream.of(new ConcurrentLinkedQueue<Long>(),
										Collections.synchronizedList(new ArrayList<Long>()),
										new CopyOnWriteArrayList<Long>())
								.forEach(collection -> doSimulate(collection, numOfThreads * 10, yes, map))));

		for (Map.Entry<String, Double> e : map.entrySet())
			System.err.println(e);
	}

	private static void doSimulate(Collection<Long> collection, int numOfThreads, boolean yes, Map<String, Double> infoMap) {
		String key = String.join(":", collection.getClass().getSimpleName(), String.valueOf(numOfThreads), String.valueOf(yes));
		List<Long> list = new ArrayList<>();
		IntStream.range(0, 5).forEach(i -> list.add(simulate(collection, numOfThreads, yes)));
		infoMap.put(key, list.stream().mapToDouble(Long::doubleValue).average().getAsDouble());
	}

	private static Long simulate(Collection<Long> collection, int numOfThreads, boolean isReading) {
		final int end = 10000;
		ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
		AtomicInteger count = new AtomicInteger(0);
		long start = System.currentTimeMillis();

		IntStream.range(0, end).forEach(i -> {
			if (count.getAndIncrement() < end)
				service.submit(() -> {
					collection.add(ThreadLocalRandom.current().nextLong());
					if (isReading)
						collection.contains(ThreadLocalRandom.current().nextInt());
				});
		});

		service.shutdown();
		try {
			service.awaitTermination(2, TimeUnit.HOURS);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		return System.currentTimeMillis() - start;
	}

}

/**
 * Size: 10000
 *      ConcurrentLinkedQueue       vs      CopyOnWriteArrayList      vs    SynchronizedRandomAccessList
 *  10      [3.0, 88.0]                         [69.6, 87.0]                        [2.6, 131.2]
 *  20      [3.0, 101.6]                        [61.6, 65.6]                        [2.2, 118.4]
 *  30      [2.8, 80.6]                         [67.4, 65.4]                        [3.0, 115.2]
 *  40      [3.8, 82.2]                         [60.0, 65.8]                        [3.6, 135.6]
 *  50      [3.8, 80.4]                         [56.6, 63.0]                        [5.4, 135.0]
 *  60      [4.6, 80.6]                         [58.2, 63.0]                        [4.2, 136.2]
 *  70      [5.0, 85.2]                         [57.2, 64.0]                        [4.8, 137.6]
 *  80      [5.4, 82.6]                         [60.6, 62.6]                        [5.2, 139.2]
 *  90      [6.2, 83.6]                         [60.2, 66.0]                        [5.6, 139.8]
 * ConcurrentLinkedQueue and SynchronizedRandomAccessList reads slow but write fast.
 * CopyOnWriteArrayList is good at both read and write but overall slower in write
 */
