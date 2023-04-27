package us.hcheng.javaio.thread.jcu.concurrent;

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

	static class Info {
		int threadCount;
		double ms;
		boolean reading;

		public Info(int threadCount, double ms, boolean reading) {
			this.threadCount = threadCount;
			this.ms = ms;
			this.reading = reading;
		}

		@Override
		public String toString() {
			return new StringBuilder("[numOfThreads:").append(threadCount)
					.append(", reading: ").append(reading ? "yes" : "no")
					.append(", ms: ").append(ms).append("]").toString();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Map<String, Info> map = new TreeMap<>();

		IntStream.range(1, 10).forEach(numOfThreads ->
				Stream.of(true, false).forEach(yes ->
						Stream.of(new ConcurrentHashMap<>(), new ConcurrentSkipListMap<>())
								.forEach(m -> doSimulate(m, numOfThreads * 10, yes, map))));

		for (Map.Entry<String, Info> e : map.entrySet())
			System.err.println(e);
	}

	private static void doSimulate(Map map, int numOfThreads, boolean yes, Map<String, Info> infoMap) {
		String key = String.join(":", map.getClass().getSimpleName(), String.valueOf(numOfThreads), String.valueOf(yes));
		List<Double> list = new ArrayList<>();
		IntStream.range(0, 5).forEach(i -> list.add(simulate(map, numOfThreads, yes).ms));
		double avg = list.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		infoMap.put(key, new Info(numOfThreads, avg, yes));
	}

	private static Info simulate(Map<Integer, String> map, int numOfThreads, boolean isReading) {
		final int end = 1000000;
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

		return new Info(numOfThreads, System.currentTimeMillis() - start, isReading);
	}

}
