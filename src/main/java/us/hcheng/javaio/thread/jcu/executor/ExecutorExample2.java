package us.hcheng.javaio.thread.jcu.executor;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ExecutorExample2 {

	public static void main(String[] args) {
		System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
		ExecutorService service = Executors.newWorkStealingPool();

		Collection<MyTask> callables = IntStream.range(0, 10)
				.mapToObj(i -> new MyTask(i))
				.collect(Collectors.toList());

		Collection<String> resSet = null;

		try {
			List<Future<String>> futures = service.invokeAll(callables);
			resSet = futures.stream().map(f -> {
				String ret = null;
				long start = System.currentTimeMillis();

				try {
					// ret = f.get();
					ret = f.get(3, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					System.err.println(" get InterruptedException: " + e);
				} catch (ExecutionException e) {
					System.err.println(" get ExecutionException: " + e);
				} catch (TimeoutException e) {
					System.err.println(" get TimeoutException: " + e);
				} finally {
					System.out.println("Result: " + ret + " === " + (System.currentTimeMillis() - start));
				}

				return ret;
			}).collect(Collectors.toList());
		} catch (InterruptedException ex) {
			System.err.println("invokeAll InterruptedException: " + ex);
		}

		if (resSet != null)
			System.out.println(resSet);
	}


	static class MyTask implements Callable<String> {
		private int id;
		public MyTask(int id) {
			this.id = id;
		}

		@Override
		public String call() throws Exception {
			Thread t = Thread.currentThread();
			SleepUtil.sleepSec(new Random().nextInt(10));
			String ret = String.join("-", t.getName(), String.join(":", "working on Task", String.valueOf(id)));
			System.err.println(ret);
			return ret;
		}
	}

}
