package us.hcheng.javaio.thread.jcu.executor;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import us.hcheng.javaio.utils.SleepUtil;

public class CompletableFutureExample1 {

	private static CountDownLatch latch;

	public static void main(String[] args) {
		//wiatDone();
		//callbackDone();

		List<Long> t1List = new ArrayList<>();
		List<Long> t2List = new ArrayList<>();
		IntStream.range(0, 10).forEach(i -> {
			t1List.add(blockingByPhaseProcess());
			t2List.add(nonblockingByPhaseProcess());
		});

		System.out.println(t1List.stream().mapToDouble(n -> n).average() + " vs " + t2List.stream().mapToDouble(n -> n).average());
	}

	private static void callbackDone() {
		CompletableFuture.runAsync(()-> {
				SleepUtil.processing();
		}).whenComplete((v, t) -> {
			System.out.println("Done");
		});

		SleepUtil.sleepSec(3);
	}

	private static void wiatDone() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		Future<?> future = service.submit(() -> {
			SleepUtil.processing();
		});

		while (!future.isDone())
			SleepUtil.sleep(50);
		System.out.println("Done");
	}

	private static long blockingByPhaseProcess() {
		long start = System.currentTimeMillis();
		latch = new CountDownLatch(10);
		ExecutorService service = Executors.newFixedThreadPool(3);
		List<Callable<Integer>> tasks = IntStream.range(0, 10).boxed()
				.map(i -> (Callable<Integer>) () -> doWork("FETCH"))
				.collect(toList());

		try {
			service.invokeAll(tasks).stream().map(f -> {
				try {
					return f.get();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).parallel().forEach(res -> {
				doWork("TRANSFORM");
			});
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return System.currentTimeMillis() - start;
	}

	private static long nonblockingByPhaseProcess() {
		long start = System.currentTimeMillis();
		latch = new CountDownLatch(10);
		IntStream.range(0, 1).boxed().forEach(i -> {
			CompletableFuture<Integer> phase1 = CompletableFuture.supplyAsync(() -> doWork("FETCH"));
			CompletableFuture<Void> phase2 = phase1.thenAccept(res -> doWork("TRANSFORM"));
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return System.currentTimeMillis() - start;
	}

	private static Integer doWork(String tag) {
		int ret = ThreadLocalRandom.current().nextInt(100);
		System.out.println("doWorking... " + ret + " ---" + tag);
		SleepUtil.sleep(ret);
		System.out.println("COMPLETE-doWorK: " + ret + " ---" + tag);
		if (tag.equals("TRANSFORM"))
			latch.countDown();
		return ret;
	}

}
