package us.hcheng.javaio.thread.jcu.concurrent.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConcurrentSkipListMapVSConcurrentHashMap {

	public static void main(String[] args) throws InterruptedException {
		Map<String, Double> map = new TreeMap<>();

		IntStream.range(1, 10).forEach(numOfThreads ->
				Stream.of(true, false).forEach(yes ->
						Stream.of(new ConcurrentHashMap<>(), new ConcurrentSkipListMap<>())
								.forEach(m -> doSimulate(m, numOfThreads * 10, yes, map))));

		for (Map.Entry<String, Double> e : map.entrySet())
			System.err.println(e);
	}

	private static void doSimulate(Map map, int numOfThreads, boolean yes, Map<String, Double> infoMap) {
		String key = String.join(":", map.getClass().getSimpleName(), String.valueOf(numOfThreads), String.valueOf(yes));
		List<Long> list = new ArrayList<>();
		IntStream.range(0, 5).forEach(i -> list.add(simulate(map, numOfThreads, yes)));
		infoMap.put(key, list.stream().mapToDouble(Long::doubleValue).average().getAsDouble());
	}

	private static Long simulate(Map<Integer, String> map, int numOfThreads, boolean isReading) {
		final int end = 10000;
		ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
		AtomicInteger count = new AtomicInteger(0);
		long start = System.currentTimeMillis();

		IntStream.range(0, end).forEach(i -> {
			if (count.getAndIncrement() < end)
				service.submit(() -> {
					map.put(ThreadLocalRandom.current().nextInt(), String.valueOf(ThreadLocalRandom.current().nextInt()));
					if (isReading)
						map.get(ThreadLocalRandom.current().nextInt());
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

	/**
	 * Size : 10000
	 *
	 *      ConcurrentHashMap        vs          ConcurrentSkipListMap
	 * 10       [7.6, 18.2]                         [8.4, 15.2]
	 * 20       [4.4, 4.6]                          [6.8, 10.0]
	 * 30       [5.0, 5.2]                          [6.8, 11.2]
	 * 40       [7.4, 4.6]                          [6.4, 7.4]
	 * 50       [5.4, 5.6]                          [7.4, 8.2]
	 * 60       [5.4, 5.8]                          [7.2, 9.2]
	 * 70       [6.6, 6.2]                          [8.4, 9.0]
	 * 80       [6.2, 6.8]                          [8.4, 10.0]
	 * 90       [7.2, 6.8]                          [8.6, 10.0]
	 *
	 * Looks like ConcurrentHashMap is generally faster in both read and write.
	 * But my test set is not stat good enough to make this conclusion ;-)
	 */

}
