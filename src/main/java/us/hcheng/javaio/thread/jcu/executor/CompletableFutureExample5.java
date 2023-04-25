package us.hcheng.javaio.thread.jcu.executor;

import static us.hcheng.javaio.utils.Util.alwaysOn;
import static us.hcheng.javaio.utils.Util.futureInfo;
import static us.hcheng.javaio.utils.Util.throwException;
import us.hcheng.javaio.utils.SleepUtil;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;


public class CompletableFutureExample5 {

	public static void main(String[] args) {
		//getNow();
		//complete();
		//join();
		//completeExceptionally();
		//obtrudeException();
		errorHandling();

		alwaysOn();
	}

	private static void errorHandling() {
		System.out.println(errorHandlingHelper().join());
	}

	private static CompletableFuture errorHandlingHelper() {
		System.out.println("ENTER errorHandlingHelper at " + System.currentTimeMillis());

		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing();
			return "Moka";
		});

		CompletableFuture<Void> f2 = f1.thenApply(res -> {
			if (1 + 1 == 2)
				throwException("WOWOW...");
			System.out.println("thenApply processing at " + System.currentTimeMillis());
			SleepUtil.processing();
			return 100;
		}).exceptionally(t -> {
			System.out.println("this is t: " + t);
			return 666;
		}).thenAccept(System.err::println);

		new Thread(() -> {
			SleepUtil.sleepSec(10);
			System.out.println(futureInfo(f2));
		}).start();

		System.out.println("EXIT errorHandlingHelper at " + System.currentTimeMillis());
		return f1;
	}


	private static void obtrudeException() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			if (1 + 1 != 2)
				throwException("supplyAsync");
			int timer = ThreadLocalRandom.current().nextInt(5);
			System.out.println("processing " + timer + " at supplyAsync");
			SleepUtil.sleepSec(timer);
			return "Moka";
		});


		SleepUtil.sleepSec(5);
		System.out.println(futureInfo(f1));
		f1.obtrudeException(new IllegalStateException("aloha!"));

		SleepUtil.sleepSec(1);
		System.out.println(futureInfo(f1));

		SleepUtil.sleepSec(1);
		f1.obtrudeValue("Hey");
		System.out.println(futureInfo(f1));

		/**
		 * CompletableFuture status can change from time to time
		 * {Moka} --- @1be6f5c3[Completed normally]
		 * @1be6f5c3[Completed exceptionally: java.lang.IllegalStateException: aloha!]
		 * {Hey} --- @1be6f5c3[Completed normally]
		 */
	}

	/**
	 * This is a race that if completeExceptionally got executed before
	 * CompletableFuture is completed then it will be [Completed exceptionally]
	 */
	private static void completeExceptionally() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			if (1 + 1 != 2)
				throwException("supplyAsync");
			int timer = ThreadLocalRandom.current().nextInt(10);
			System.out.println("processing " + timer + " at supplyAsync");
			SleepUtil.sleepSec(timer);
			return "Moka";
		});

		int timer = ThreadLocalRandom.current().nextInt(10);
		System.out.println("processing " + timer + " at completeExceptionally");
		SleepUtil.sleepSec(timer);

		f1.completeExceptionally(new IllegalStateException("should be completed by now!"));
		System.out.println(futureInfo(f1));
	}

	private static void join() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			if (1 + 1 == 2)
				throwException("supplyAsync");
			SleepUtil.processing("supplyAsync");
			SleepUtil.sleepSec(10);
			return "Moka";
		});

		boolean makeItComplete = false;
		if (makeItComplete)
			SleepUtil.processing();

		try {
			System.out.println("f1.join(): " + f1.join());
		} catch (Exception ex){
			ex.printStackTrace();
		}
		System.out.println(futureInfo(f1));
	}

	private static void complete() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			if (1 + 1 == 2)
				throwException("supplyAsync");
			SleepUtil.processing("supplyAsync");
			SleepUtil.sleepSec(10);
			return "Moka";
		});

		new Thread(()->{
			try {
				System.out.println("f1.get(): " + f1.get());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).start();

		try {
			boolean makeItComplete = false;
			if (makeItComplete)
				SleepUtil.processing();

			boolean res = f1.complete("Pot");
			System.out.println("res: " + res);
			System.out.println(f1.get());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(futureInfo(f1));
	}

	/**
	 * getNow will return valueIfAbsent if it is not yet completed
	 * otherwise, it will return the original result
	 */
	private static void getNow() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			if (1 + 1 == 2)
				throwException("supplyAsync");
			//SleepUtil.processing("supplyAsync");
			return "Moka";
		});

		try {
			boolean makeItComplete = true;
			if (makeItComplete)
				SleepUtil.processing();

			String res = f1.getNow("Pot");
			System.out.println("res: " + res);
			System.out.println(f1.get());
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
