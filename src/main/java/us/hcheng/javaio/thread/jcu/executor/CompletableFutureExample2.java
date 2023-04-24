package us.hcheng.javaio.thread.jcu.executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class CompletableFutureExample2 {

	public static void main(String[] args) {
		//supplyAsync();

		//thenAcceptAndAsyncDiff1();
		//thenAcceptAndAsyncDiff2();

		//runAsync();
		//complete();

		//anyOf();
		//allOf();

		constructor();

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static void constructor() {
		// [Not completed]
		CompletableFuture<String> cfString = new CompletableFuture<>();

		//[Completed normally]
		CompletableFuture<String> cfStringFact = CompletableFuture.supplyAsync(() -> {
			SleepUtil.sleepSec(1);
			return "";
		}).thenApply(res -> new StringBuilder(res).append("Moka").toString());

		new Thread(() -> {
			SleepUtil.sleepSec(1);
			cfString.complete("Cheng");
		}).start();

		try {
			System.out.println("GET -> " + CompletableFuture.anyOf(cfString, cfStringFact).get());
			System.out.println(cfString.isDone() + " " + cfString + " *** " + cfString.get() + " ***");
			System.out.println(cfStringFact.isDone() + " " + cfStringFact + " *** " + cfStringFact.get() + " ***");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void allOf() {
		Future<?> future = CompletableFuture.anyOf(
				CompletableFuture.runAsync(() -> {
					SleepUtil.sleepSec(10);
				}).whenCompleteAsync((v, t) -> {
					System.out.println(System.currentTimeMillis() +" *** null -> " + v + " " + Thread.currentThread());
				}),
				CompletableFuture.supplyAsync(() ->{
					SleepUtil.sleepSec(10);
					return "Moka";
				} ).whenCompleteAsync((v, t) -> {
					System.out.println(System.currentTimeMillis() +" *** Moka -> " + v + " " + Thread.currentThread());
				}),
				CompletableFuture.supplyAsync(() ->{
					return 1;
				}).whenComplete((v, t) -> {
					System.out.println(System.currentTimeMillis() +" *** 1 Complete -> " + v + " " + Thread.currentThread());
				})
		);

		try {
			System.out.println(System.currentTimeMillis() + "future.isDone(): " + future.isDone());
			System.out.println(System.currentTimeMillis() + "future.get(): " + future.get());
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private static void anyOf() {
		/**
		 * in this code below whenCompleteAsync == whenComplete from
		 * thread execution view
		 */
		Future<?> future = CompletableFuture.anyOf(
				CompletableFuture.runAsync(() -> {
					SleepUtil.sleepSec(10);
				}).whenCompleteAsync((v, t) -> {
					System.out.println(System.currentTimeMillis() +" *** null -> " + v + " " + Thread.currentThread());
				}),
				CompletableFuture.supplyAsync(() ->{
					SleepUtil.sleepSec(10);
					return "Moka";
				} ).whenCompleteAsync((v, t) -> {
					System.out.println(System.currentTimeMillis() +" *** Moka -> " + v + " " + Thread.currentThread());
				}),
				CompletableFuture.supplyAsync(() ->{
					return 1;
				}).whenComplete((v, t) -> {
					System.out.println(System.currentTimeMillis() +" *** 1 Complete -> " + v + " " + Thread.currentThread());
				})
		);

		try {
			System.out.println(System.currentTimeMillis() + "future.isDone(): " + future.isDone());
			System.out.println(System.currentTimeMillis() + "future.get(): " + future.get());
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private static void complete() {
		CompletableFuture<String> cf1 = CompletableFuture.completedFuture("thenAccept");
		Future<Void> future = cf1.thenAccept(res -> {
			System.err.println(Thread.currentThread() + " got "  + res);
		});

		try {
			System.err.println("future.get(): " + future.get());
		} catch (Exception ex) {}

		CompletableFuture<String> cf2 = CompletableFuture.completedFuture("thenAcceptAsync");
		cf2.thenAcceptAsync(res -> {
			System.err.println(Thread.currentThread() + " got "  + res);
		});
	}

	private static void runAsync() {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			SleepUtil.processing();
			System.err.println(Thread.currentThread() + " runAsync!");
		});

		/**
		 * whenComplete和whenCompleteAsync 的区别是
		 * whenComplete: 是执行当前任务的线程继续执行whenComplete的任务。
		 * whenCompleteAsync: 是把whenCompleteAsync的任务继续提交给线程池来进行执行
		 *
		 * Example when doing: whenCompleteAsync
		 *
		 * Thread[ForkJoinPool.commonPool-worker-19,5,main] runAsync!
		 * Thread[ForkJoinPool.commonPool-worker-5,5,main] whenComplete!
		 *
		 * Example when doing: whenComplete
		 * Thread[ForkJoinPool.commonPool-worker-19,5,main] runAsync!
		 * Thread[ForkJoinPool.commonPool-worker-19,5,main] whenComplete!
		 */
		future.whenComplete((v, t) -> {
			System.err.println(Thread.currentThread() + " whenComplete!");
			System.out.println(v);
			System.out.println(t);
		});

		System.out.println("********** COMPLETE runAsync **********");
	}

	private static void supplyAsync() {
		CompletableFuture.supplyAsync(() -> {
			int ret = ThreadLocalRandom.current().nextInt(20);
			System.out.println("before ret: " + ret);
			System.out.println("after ret: " + ret);
			return ret;
		}).thenAcceptAsync(res -> {
			System.out.println("res: " + res);
		}).thenAcceptBoth(CompletableFuture.supplyAsync(Object::new)
				.thenAcceptAsync(obj -> {
					System.out.println("before obj: " + obj);
					SleepUtil.sleepSec(2);
					System.out.println("after obj: " + obj);
				}), (a, b) -> {
			System.out.println("both jobs are done");
		});
	}

	private static void thenAcceptAndAsyncDiff1() {
		CompletableFuture<Integer> future = CompletableFuture.completedFuture(13);
		System.out.println("NON-BLOCKING");

		/** Example 1: CompletedFuture
		 * if future is completed when at calling thenAcceptAsync or thenAccept
		 * thenAccept will BLOCK            by Thread[main,5,main]
		 * thenAcceptAsync will NOT BLOCK   by Thread[ForkJoinPool.commonPool-worker-19,5,main]
		 */
		future.thenAcceptAsync(res -> {
			SleepUtil.sleepSec(10);
			System.out.println("res: " + res + " by " + Thread.currentThread());
		});

		System.out.println("---END---");
	}

	private static void thenAcceptAndAsyncDiff2() {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			int ret = ThreadLocalRandom.current().nextInt(20);
			System.err.println(Thread.currentThread() + " supplyAsync!~");
			System.out.println("ret: " + ret);
			return ret;
		});
		System.out.println("NON-BLOCKING");

		/** Example 2: Have a timer before the call so future is completed
		 * if future is completed when at calling thenAcceptAsync or thenAccept
		 * thenAccept will BLOCK
		 * thenAcceptAsync will NOT BLOCK
		 *
		 * With SleepUtil.sleepSec(1);
		 * supplyAsync - Thread[ForkJoinPool.commonPool-worker-19,5,main]
		 * thenAccept - Thread[main,5,main]
		 *
		 * VS
		 * Without SleepUtil.sleepSec(1);
		 * supplyAsync - Thread[ForkJoinPool.commonPool-worker-19,5,main]
		 * thenAccept - Thread[ForkJoinPool.commonPool-worker-19,5,main]
		 */
		SleepUtil.sleepSec(1);
		future.thenAccept(res -> {
			SleepUtil.sleepSec(5);
			System.out.println("res: " + res);
			System.err.println(Thread.currentThread());
		});

		System.out.println("---END---");
	}

}
