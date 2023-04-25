package us.hcheng.javaio.thread.jcu.executor;

import static us.hcheng.javaio.utils.Util.alwaysOn;
import static us.hcheng.javaio.utils.Util.futureInfo;
import static us.hcheng.javaio.utils.Util.throwException;
import us.hcheng.javaio.utils.SleepUtil;
import java.util.concurrent.CompletableFuture;


public class CompletableFutureExample3 {

	public static void main(String[] args) {
		//whenCompletes();
		//thenApplies();
		//handles();
		//toCompletableFuture();
		//accepts();
		runs();
		alwaysOn();
	}

	/**
	 * Nothing will be executed inside thenRun and thenRunAsync
	 * if prior step had an exception
	 */
	private static void runs() {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing();
			if (7 == 8)
				throwException("supplyAsync exception");

			System.out.println("Done return Moka ;-)");
			return "Moka";
		});

		/**
		 * thenRun and thenRunAsync both NON-BLOCKING
		 * thenRun: that same thread executes it
		 * thenRunAsync: a threads from the pool executes it(could be that threads or main)
		 */
		CompletableFuture<Void> future2 = future1.thenRunAsync(() -> {
			if (7 == 8)
				throwException("supplyAsync exception");
			SleepUtil.processing();
			System.out.println("thenRun or thenRunAsync completed ;-)");
		});

		System.out.println("NON-BLOCKING");
		System.out.println(String.join("\n", futureInfo(future1), futureInfo(future2)));
	}

	private static void accepts() {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing();
			if (7 == 8)
				throwException("supplyAsync exception");
			return "Moka";
		});

		/**
		 * thenAccept and thenAcceptAsync both NON-BLOCKING
		 * thenAccept: that same thread executes it
		 * thenAcceptAsync: a threads from the pool executes it(could be that threads or main)
		 */
		CompletableFuture<Void> future2 = future1.thenAcceptAsync((res) -> {
			System.err.println("thenAcceptAsync got: " + res);
			if (7 == 8)
				throwException("supplyAsync exception");
			SleepUtil.processing();
		});

		System.out.println("NON-BLOCKING");
		System.out.println(String.join("\n", futureInfo(future1), futureInfo(future2)));
	}

	/**
	 * Returns this CompletableFuture.
	 */
	private static void toCompletableFuture() {
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 5);
		CompletableFuture<Integer> future2 = future1.toCompletableFuture();
		System.out.println(String.join("\n", futureInfo(future1), String.valueOf(future1 == future2)));
	}

	/**
	 * handle and handleAsync could handle exceptions occurred
	 * at previous steps and return a [Completed normally]
	 */
	private static void handles() {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing();
			if (7 == 8)
				throwException("supplyAsync exception");
			return "Moka";
		});

		/**
		 * handle and handleAsync both NON-BLOCKING
		 * handle: that same thread executes it
		 * handleAsync: a threads from the pool executes it(could be that threads or main)
		 */
		CompletableFuture<Integer> future2 = future1.handleAsync((res, t) -> {
			SleepUtil.processing();
			return 100;
		});

		System.out.println("NON-BLOCKING");
		System.out.println(String.join("\n", futureInfo(future1), futureInfo(future2)));
	}

	private static void thenApplies() {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing();
			System.out.println("supplyAsync...");
			if (7 == 8)
				throwException("supplyAsync exception");
			return "Moka";
		});

		/**
		 * thenApply and thenApplyAsync both NON-BLOCKING
		 * thenApply: that same thread executes it
		 * thenApplyAsync: a threads from the pool executes it(could be that threads or main)
		 */
		CompletableFuture<Integer> future2 = future1.thenApplyAsync(str -> {
			SleepUtil.processing();
			System.out.println("thenApplyAsync...");
			if (7 == 8)
				throwException("thenApply exception");
			return str.length();
		});

		System.out.println("NON-BLOCKING");
		System.out.println(String.join("\n", futureInfo(future1), futureInfo(future2)));
	}


	private static void whenCompletes() {
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			SleepUtil.processing();
			if (7 == 8)
				throwException("supplyAsync exception");
			return "Moka";
		});

		CompletableFuture<String> future2 = future1.whenComplete((v, t) -> {
			SleepUtil.processing();
			if (7 == 8)
				throwException("whenComplete exception");
			System.err.println("v: " + v + " t: " + t);
		});

		/**
		 * whenComplete and whenCompleteAsync both NON-BLOCKING
		 * whenComplete: that same thread executes it
		 * whenCompleteAsync: a threads from the pool executes it(could be that threads or main)
		 */
		System.out.println("NON-BLOCKING");
		System.out.println(String.join("\n", futureInfo(future1), futureInfo(future2)));
	}


}
