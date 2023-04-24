package us.hcheng.javaio.thread.jcu.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ExecutorExample1 {

	public static void main(String[] args) {
		//newCachedThreadPoolTest();
		//fixedThreadPoolTest();
		//singleThreadExecutorTest();
		newCachedThreadPoolTestCopy();
	}

	/**
	 * 0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()
	 *  This will auto-shutdown and suitable short-live running tasks
	 */
	private static void newCachedThreadPoolTest() {
		ExecutorService service = Executors.newCachedThreadPool();
		IntStream.range(0, 100).forEach(i -> {
			service.execute(new MyTask());
		});

		monitor((ThreadPoolExecutor) service);
		System.err.println("newCachedThreadPoolTest completed!");
	}

	private static void newCachedThreadPoolTestCopy() {
		ExecutorService service = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		IntStream.range(0, 6).forEach(i -> {
			service.execute(new MyTask());
		});

		//monitor((ThreadPoolExecutor) service);
		System.err.println("newCachedThreadPoolTest completed!");
	}

	/**
	 * 1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
	 */
	private static void singleThreadExecutorTest() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		IntStream.range(0, 10).forEach(i -> {
			service.execute(new MyTask());
		});
		System.err.println("singleThreadExecutorTest completed!");
	}

	/**
	 * nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
	 * This will NOT auto-shutdown and can be used for long-running tasks
	 */
	private static void fixedThreadPoolTest() {
		ExecutorService service = Executors.newFixedThreadPool(10);
		IntStream.range(0, 100).forEach(i -> {
			service.execute(new MyTask());
		});
		monitor((ThreadPoolExecutor) service);
		System.err.println("fixedThreadPoolTest completed!");
	}

	static class MyTask implements Runnable {
		@Override
		public void run() {
			SleepUtil.processing();
		}
	}

	private static void monitor(ThreadPoolExecutor executor) {
		new Thread(() -> {
			while (!(executor.isTerminated() || executor.isShutdown() || executor.isTerminating())) {
				SleepUtil.sleepSec(1);
				StringBuilder sb = new StringBuilder();
				sb.append("getCorePoolSize: " + executor.getCorePoolSize());
				sb.append("\ngetPoolSize: " + executor.getPoolSize());
				sb.append("\ngetActiveCount: " + executor.getActiveCount());
				sb.append("\ngetCompletedTaskCount: " + executor.getCompletedTaskCount());
				sb.append("\nisShutdown: " + executor.isShutdown());
				sb.append("\nisTerminated: " + executor.isTerminated());
				System.out.println("**********\n" + sb + "\n**********");
			}
			System.err.println("exit monitor...");
		}).start();
	}
}
