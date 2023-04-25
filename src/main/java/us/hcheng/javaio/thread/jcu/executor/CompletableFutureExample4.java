package us.hcheng.javaio.thread.jcu.executor;

import static us.hcheng.javaio.utils.Util.alwaysOn;
import static us.hcheng.javaio.utils.Util.futureInfo;
import static us.hcheng.javaio.utils.Util.throwException;
import us.hcheng.javaio.utils.SleepUtil;
import java.util.concurrent.CompletableFuture;


public class CompletableFutureExample4 {

	public static void main(String[] args) {
		//thenAcceptBoths();
		//acceptEithers();
		//runAfterBoths();
		//runAfterEithers();
		//composes();
		combines();

		alwaysOn();
	}

	/**
	 * If one or both of the CompletableFutures are [Completed exceptionally]
	 * composes will be [Completed exceptionally]
	 */
	private static void combines() {
		CompletableFuture<String> f1c = CompletableFuture.completedFuture("Moka");
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync1");
			if (7 == 8)
				throwException("supplyAsync1");
			return "Moka";
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync2");
			return 100;
		});
		CompletableFuture<Integer> f2c = CompletableFuture.completedFuture(100);
		/**
		 * thenCombine vs thenCombineAsync
		 *
		 * thenCombine is BLOCKING and executing by main thread only when
		 * both CompletableFutures are completed.
		 *
		 * Otherwise, it will be executed by a thread from the pool
		 * and NON-BLOCKING [thenComposeAsync]
		 */

		CompletableFuture<Integer> f3 = f1c.thenCombineAsync(f2c, (res1, res2) -> {
			String s = "thenCombine --- [" + res1 + ", " + res2 + "]";
			SleepUtil.processing(s);
			return s.length();
		});

		System.out.println("NON-BLOCKING");
		System.err.println("(f1): " + futureInfo(f1));
		System.err.println("(f2): " + futureInfo(f2));
		System.err.println("(f3): " + futureInfo(f3));
	}

	/**
	 * If one or both of the CompletableFutures are [Completed exceptionally]
	 * composes will be [Completed exceptionally]
	 */
	private static void composes() {
		CompletableFuture<String> f1c = CompletableFuture.completedFuture("Moka");
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync1");
			if (7 != 8)
				throwException("supplyAsync1");
			return "Moka";
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync2");
			return 100;
		});

		/**
		 * thenCompose vs thenComposeAsync
		 *
		 * thenCompose is BLOCKING and executing by main thread only when
		 * both CompletableFutures are completed.
		 *
		 * Otherwise, it will be executed by a thread from the pool
		 * and NON-BLOCKING [thenComposeAsync]
		 */

		CompletableFuture<String> f3 = f1.thenCompose(res -> CompletableFuture.completedFuture(helper(res)));

		System.out.println("NON-BLOCKING");
		System.err.println("(f1): " + futureInfo(f1));
		System.err.println("(f2): " + futureInfo(f2));
		System.err.println("(f3): " + futureInfo(f3));
	}

	private static String helper(String s) {
		SleepUtil.processing("helper");
		return "***" + s + "***";
	}

	/**
	 * One or both CompletableFutures are [Completed exceptionally]
	 * cannot impact runAfterEithers to [Completed normally]
	 */
	private static void runAfterEithers() {
		CompletableFuture<String> f1C = CompletableFuture.completedFuture("Haha");
		CompletableFuture<String> f2C = CompletableFuture.completedFuture("WOW");
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			//SleepUtil.processing("supplyAsync1");
			if (7 != 8)
				throwException("supplyAsync1");
			return "Moka";
		});
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
			if (7 != 8)
				throwException("supplyAsync1");
			SleepUtil.processing("supplyAsync2");
			return "Pot";
		});

		/**
		 * runAfterEither vs runAfterEitherAsync
		 *
		 * runAfterEither if one of them is completed, runAfterEither is executed
		 * by main and this is BLOCKING.
		 * Otherwise, this is completed by a thread from the pool.[runAfterEitherAsync]
		 */

		CompletableFuture<Void> f3 = f1C.runAfterEitherAsync(f2, () -> {
			SleepUtil.processing("runAfterEither(s)...");
		});

		System.out.println("NON-BLOCKING");
		System.err.println("(f1): " + futureInfo(f1));
		System.err.println("(f2): " + futureInfo(f2));
		System.err.println("(f3): " + futureInfo(f3));
	}

	/**
	 * runAfterBoth(s) will [completed normally] only when
	 * both CompletableFutures are [completed normally]
	 */
	private static void runAfterBoths() {
		CompletableFuture<String> f1C = CompletableFuture.completedFuture("Haha");
		CompletableFuture<Integer> f2C = CompletableFuture.completedFuture(100);

		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync1");
			if (7 != 8)
				throwException("supplyAsync1");

			return "Moka";
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync2");
			return 100;
		});

		/**
		 * runAfterBoth vs runAfterBothAsync
		 *
		 * runAfterBoth only BLOCKING when both are completed,
		 * and this is executed by main thread, otherwise a thread
		 * from the pool.
		 *
		 * runAfterBothAsync is always NON-BLOCKING
		 */
		CompletableFuture<Void> f3 = f1.runAfterBoth(f2C, () -> {
			SleepUtil.processing("runAfterBoth...");
		});

		System.out.println("NON-BLOCKING");
		System.err.println("(f1): " + futureInfo(f1));
		System.err.println("(f2): " + futureInfo(f2));
		System.err.println("(f3): " + futureInfo(f3));
	}

	/**
	 * If one of them has exception, if the exception one returned 1st,
	 * this will be an exception. Otherwise, acceptEither(s) can [complete normally]
	 *
	 * Data Type of the two CompletableFutures has to be the same
	 */
	private static void acceptEithers() {
		CompletableFuture<String> f1C = CompletableFuture.completedFuture("Haha");
		CompletableFuture<String> f2C = CompletableFuture.completedFuture("WOW");

		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			//SleepUtil.processing("supplyAsync1");
			if (7 != 8)
				throwException("supplyAsync1");
			return "Moka";
		});
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync2");
			return "Pot";
		});

		/**
		 * acceptEither vs acceptEitherAsync
		 *
		 * acceptEither
		 * if any of the two is completed, main thread will execute acceptEither
		 * and it is BLOCKING. Otherwise, this is same as acceptEitherAsync
		 *
		 * acceptEitherAsync:
		 * NON-BLOCKING, find a thread from the pool to execute acceptEitherAsync
		 */

		CompletableFuture<Void> f3 = f1.acceptEitherAsync(f2, s -> {
			SleepUtil.processing("acceptEither --- " + s + " ---");
		});

		System.out.println("NON-BLOCKING");
		System.err.println("(f1): " + futureInfo(f1));
		System.err.println("(f2): " + futureInfo(f2));
		System.err.println("(f3): " + futureInfo(f3));
	}

	/**
	 * If one of them has exception it will be an exception
	 * Data Type of the two CompletableFutures can be different
	 */
	private static void thenAcceptBoths() {
		CompletableFuture<String> f1C = CompletableFuture.completedFuture("Haha");
		CompletableFuture<Integer> f2C = CompletableFuture.completedFuture(100);

		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync1");
			if (7 == 8)
				throwException("supplyAsync1");

			return "Moka";
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing("supplyAsync2");
			return 100;
		});

		/**
		 * thenAcceptBoth vs thenAcceptBothAsync
		 *
		 * *** When both CompletableFutures are completed ***
		 * thenAcceptBoth will block the current thread execution
		 * and executed by this thread
		 *
		 * *** When one or both CompletableFutures are NOT completed ***
		 * thenAcceptBoth and thenAcceptBothAsync will get executed
		 * by a thread from the pool and is not blocking the current
		 * caller thread of thenAcceptBoth|thenAcceptBothAsync
		 */
		CompletableFuture<Void> f3 = f1C.thenAcceptBoth(f2C, (s, i) -> {
			SleepUtil.processing("thenAcceptBoth");
			System.out.println("s:" + s + " i: " + i);
		});

		System.out.println("NON-BLOCKING");
		System.err.println("(f1): " + futureInfo(f1));
		System.err.println("(f2): " + futureInfo(f2));
		System.err.println("(f3): " + futureInfo(f3));
	}
}
