package us.hcheng.javaio.thread.jcu.concurrent.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class JDKMaps {

	public static void main(String[] args) throws InterruptedException {
		simulate(new Hashtable<>(), 5, false);
		simulate(new Hashtable<>(), 5, true);
		System.out.println("**********");

		simulate(Collections.synchronizedMap(new Hashtable<>()), 5, false);
		simulate(Collections.synchronizedMap(new Hashtable<>()), 5, true);
		System.out.println("**********");

		simulate(new ConcurrentHashMap<>(), 5, false);
		simulate(new ConcurrentHashMap<>(), 5, true);
		System.out.println("**********");
	}

	private static void simulate(Map<Integer, String> map, int numOfThreads, boolean isReading) throws InterruptedException {
		final int end = 10000000;
		ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
		List<Long> durations = new ArrayList<>();

		IntStream.range(0, 5).forEach(idx -> {
			long start = System.currentTimeMillis();

			IntStream.range(0, end).forEach(i -> {
				service.submit(() -> {
					map.put(ThreadLocalRandom.current().nextInt(), String.valueOf(ThreadLocalRandom.current().nextInt()));
					if (isReading)
						map.get(ThreadLocalRandom.current().nextInt());
				});
			});

			durations.add(System.currentTimeMillis() - start);
		});

		service.shutdown();
		service.awaitTermination(2, TimeUnit.HOURS);

		double avg = durations.stream().mapToLong(Long::longValue).sum() / durations.size();
		System.out.println(map.getClass().getSimpleName() + " took " + avg + " --- " + isReading);
	}

}
