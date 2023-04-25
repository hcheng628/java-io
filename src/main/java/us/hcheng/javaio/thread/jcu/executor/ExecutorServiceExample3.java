package us.hcheng.javaio.thread.jcu.executor;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import us.hcheng.javaio.utils.SleepUtil;

public class ExecutorServiceExample3 {

	public static void main(String[] args) {
		//activeCountTest();
		//allowCoreThreadTimeOutTest();
		//removeJobTest();
		//prestartCoreThreadTest();
		//prestartAllCoreThreadsTest();
		threadPoolAdviceTest();
	}


	/**
	 * Thread lazy init per request
	 */
	private static void activeCountTest() {
		ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

		IntStream.range(0, 5).forEach(i -> {
			service.submit(() -> {
				SleepUtil.processing();
				System.out.println(i);
			});
			SleepUtil.sleep(300);
			System.out.println("i: " + i + " === ActiveCount: " + service.getActiveCount() + " vs " + service.getCorePoolSize());
		});

		SleepUtil.sleepSec(10);
		IntStream.range(0, 5).forEach(i -> {
			SleepUtil.sleep(300);
			System.out.println("i: " + i + " === ActiveCount: " + service.getActiveCount() + " vs " + service.getCorePoolSize());
		});
	}

	/**
	 * This newFixedThreadPool will auto-shutdown:
	 * 		service.setKeepAliveTime(10L, TimeUnit.SECONDS);
	 * 		service.allowCoreThreadTimeOut(true);
	 */
	private static void allowCoreThreadTimeOutTest() {
		ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

		service.setKeepAliveTime(10L, TimeUnit.SECONDS);
		service.allowCoreThreadTimeOut(true);

		IntStream.range(0, 5).forEach(i -> {
			service.submit(() -> {
				SleepUtil.processing();
				System.out.println(i);
			});
		});
		System.out.println("********** allowCoreThreadTimeOutTest **********");
	}


	private static void removeJobTest() {
		ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
		IntStream.range(0, 5).forEach(i -> {
			service.submit(() -> {
				SleepUtil.processing();
			});
		});

		Runnable job = () -> System.out.println("I should not be seen in the console");
		service.execute(job);
		service.remove(job);
		System.out.println("********** removeJobTest **********");
	}

	private static void prestartCoreThreadTest() {
		ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		IntStream.range(0, 5).forEach(i -> {
			System.out.println(i + " Before ActiveCount: " + service.getActiveCount());
			boolean success = service.prestartCoreThread();
			System.out.println(i + " After ActiveCount: " + service.getActiveCount() + " " + success);
		});

		ThreadPoolExecutor service2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

		IntStream.range(0, 2).forEach(i -> {
			System.out.println(i + " Before ActiveCount: " + service2.getActiveCount());
			int success = service2.prestartAllCoreThreads();
			System.out.println(i + " After ActiveCount: " + service2.getActiveCount() + " " + success);
		});
	}

	private static void prestartAllCoreThreadsTest() {
		ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
		IntStream.range(0, 3).forEach(i -> {
			System.out.println(i + " Before ActiveCount: " + service.getActiveCount());
			int success = service.prestartAllCoreThreads();
			System.out.println(i + " After ActiveCount: " + service.getActiveCount() + " " + success);
		});
	}

	private static void threadPoolAdviceTest() {
		int nThreads = 3;
		ThreadPoolExecutor service = new MyExecutorService(nThreads, nThreads,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		IntStream.range(0, 5).forEach(i -> {
			service.execute(new Task(i));
		});
	}

	static class Task implements Runnable {
		int id;
		Date date;
		public Task(int id) {
			this.id = id;
			this.date = new Date();
		}

		@Override
		public void run() {
			if (id % 3 == 0)
				System.out.println(1 / 0);
			else
				System.out.println("Task [" + id + ", " + this.date + "]");
		}
	}

	static class MyExecutorService extends ThreadPoolExecutor {

		public MyExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}

		@Override
		protected void beforeExecute(Thread t, Runnable r) {
			System.out.println("beforeExecute: " + t + " " + ((Task) r).date + " === " + ((Task) r).id);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			if (t == null) {
				System.out.println("afterExecute: " + ((Task) r).date + " === " + ((Task) r).id);
			} else {
				System.err.println("afterExecute: " + t + " \n" + ((Task) r).date);
			}
		}
	}

}
