package us.hcheng.javaio.thread.jcu.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ExecutorServiceExample1 {

	public static void main(String[] args) {
		//isTerminatedTest();
		//isTerminatingTest();
		//runnableExceptionHandling();
		runnableExceptionHandlingTemplate();
	}

	private static void runnableExceptionHandlingTemplate() {
		ExecutorService service = Executors.newFixedThreadPool(3);

		IntStream.range(0, 10).forEach(i -> {
			service.submit(new MyTask(i) {
				@Override
				protected void init() {
					System.out.println(i + " init...");
				}

				@Override
				protected void execute() {
					System.out.println(i + " execute...");
					if (i % 3 == 0)
						System.out.println(1 / 0);
				}

				@Override
				protected void done() {
					System.out.println(i + " done...");
				}

				@Override
				protected void error(Throwable cause) {
					StringBuilder sb = new StringBuilder();
					sb.append("\n**********");
					sb.append("\n!!!ERROR!!!Job ID: ").append(id);
					sb.append("\nCause:\n").append(cause.getCause());
					sb.append("\nMessage:\n").append(cause.getMessage());
					sb.append("\nStackTrace:\n").append(cause.getStackTrace());
					sb.append("\n**********");
					System.err.println(sb);
				}
			});
		});

		service.shutdown();
	}

	private abstract static class MyTask implements Runnable {
		protected final int id;
		private MyTask(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			init();
			try {
				execute();
			} catch (Exception ex) {
				error(ex);
			} finally {
				done();
			}
		}

		protected abstract void init();
		protected abstract void execute();
		protected abstract void done();
		protected abstract void error(Throwable cause);
	}

	/**
	 * service.submit() will NOT trigger setUncaughtExceptionHandler
	 * service.execute() will trigger setUncaughtExceptionHandler
	 *
	 * Quotes from Experts
	 * Exceptions which are thrown by tasks submitted to ExecutorService#submit
	 * get wrapped into an ExcecutionException and are rethrown by the Future.get() method.
	 * This is, because the executor considers the exception as part of the result of the task.
	 *
	 * If you however submit a task via the execute() method which originates
	 * from the Executor interface, the UncaughtExceptionHandler is notified.
	 */
	private static void runnableExceptionHandling() {
		ExecutorService service = Executors.newFixedThreadPool(3, r -> {
			Thread ret = new Thread(r);
			ret.setUncaughtExceptionHandler((t, e) -> {
				System.err.println(t + " === " + e);
			});
			return ret;
		});

		IntStream.range(0, 5).forEach(i -> service.execute(() -> {
			SleepUtil.processing();
			if (i % 3 == 0)
				System.out.println(1/0);
			System.out.println(String.join(String.valueOf(i), "Job ", " Completed"));
		}));

		service.shutdown();
	}

	/**
	 * isTerminating:true|isTerminated:false|isShutdown:true
	 * Thread[pool-1-thread-1,5,main] Processing...
	 * isTerminating:false|isTerminated:true|isShutdown:true
	 */
	private static void isTerminatingTest() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		executor.submit(() -> {
			SleepUtil.processing();
		});
		executor.shutdown();

		Boolean isTerminated = null;
		Boolean isTerminating = null;
		Boolean isShutdown = null;
		for (int i = 0; i < 30; i++) {
			if (isTerminating == null || isTerminating != executor.isTerminating() || isTerminated != executor.isTerminated()|| isShutdown != executor.isShutdown() ) {
				isTerminating = executor.isTerminating();
				isTerminated = executor.isTerminated();
				isShutdown = executor.isShutdown();
				StringBuilder sb = new StringBuilder();
				sb.append("isTerminating:").append(executor.isTerminating());
				sb.append("|isTerminated:").append(executor.isTerminated());
				sb.append("|isShutdown:").append(executor.isShutdown());
				System.err.println(sb);
			}
			SleepUtil.sleep(300);
		}
	}

	private static void isTerminatedTest() {
		ExecutorService service = Executors.newFixedThreadPool(1);
		service.submit(() -> SleepUtil.processing());
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());

		service.shutdown();
		SleepUtil.sleepSec(1);
		// isShutdown is true after shutdown() but isTerminated will take some time
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());

		try {
			service.submit(() -> System.out.println("task after service.shutdown"));
		} catch (RejectedExecutionException ex) {
			System.out.println("this is expected ;-)");
		}
	}

}
