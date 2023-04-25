package us.hcheng.javaio.thread.jcu.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import us.hcheng.javaio.utils.SleepUtil;

public class ExecutorServiceExample2 {

	public static void main(String[] args) {
		//abortPolicyTest();
		//discardPolicyTest();
		//callerRunsPolicyTest();
		discardOldestPolicyTest();
	}

	/**
	 * Thread[pool-1-thread-2,5,main] Processing... 1
	 * Thread[pool-1-thread-3,5,main] Processing... 2
	 * Thread[pool-1-thread-1,5,main] Processing... 0
	 * Thread[pool-1-thread-3,5,main] Processing... 9
	 *
	 * 3 was queued then removed when 4 comes and
	 * when 5 comes it kicked off 4... after 10 jobs
	 * the pool service still executing 3 jobs and
	 * the only queue spot is the last job 9
	 * so 4 jobs total get executed.
	 */
	private static void discardOldestPolicyTest() {
		ExecutorService service = getService(3, new ThreadPoolExecutor.DiscardOldestPolicy());
		IntStream.range(0, 10).forEach(i -> {
			try {
				service.execute(() -> {
					SleepUtil.sleep(30);
					SleepUtil.processing(i);
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		stopService(service);
	}

	private static void callerRunsPolicyTest() {
		ExecutorService service = getService(3, new ThreadPoolExecutor.CallerRunsPolicy());
		IntStream.range(0, 10).forEach(i -> {
			try {
				service.execute(() -> {
					if (i < 4)
						SleepUtil.sleep(10);
					SleepUtil.processing(i);
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		stopService(service);
	}

	private static void discardPolicyTest() {
		ExecutorService service = getService(3, new ThreadPoolExecutor.DiscardPolicy());
		IntStream.range(0, 10).forEach(i -> {
			try {
				service.execute(() -> {
					SleepUtil.sleepSec(2);
					System.err.println("processing " + i + "...");
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		stopService(service);
	}

	private static void abortPolicyTest() {
		ExecutorService service = getService(3, new ThreadPoolExecutor.AbortPolicy());
		IntStream.range(0, 5).forEach(i -> {
			try {
				service.execute(() -> {
					SleepUtil.sleepSec(2);
					System.err.println("processing " + i + "...");
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		stopService(service);
	}


	private static ExecutorService getService(int size, RejectedExecutionHandler handler) {
		return new ThreadPoolExecutor(size, size,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1), handler);
	}

	private static void stopService(ExecutorService service) {
		System.out.println("===========START-TO-STOP============");
		service.shutdown();
		try {
			if (!service.awaitTermination(200, TimeUnit.SECONDS))
				service.shutdownNow();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("===========STOPPED============");
	}

}
