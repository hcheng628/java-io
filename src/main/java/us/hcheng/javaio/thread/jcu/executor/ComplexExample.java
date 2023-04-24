package us.hcheng.javaio.thread.jcu.executor;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ComplexExample  {

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(1);
		CompletionService<Object> completionService = new ExecutorCompletionService<>(service);

		List<MyTask> tasks = IntStream.rangeClosed(1, 7).mapToObj(i -> new MyTask(i)).collect(Collectors.toList());
		tasks.forEach(t -> completionService.submit(Executors.callable(t)));

		SleepUtil.sleepSec(5);
		service.shutdownNow();
		tasks.stream().filter(each -> !each.success).forEach(System.err::println);

		SleepUtil.sleepSec(3);
		tasks.stream().filter(each -> each.success).forEach(System.out::println);
	}

	static class MyTask implements Runnable {
		final int id;
		boolean success;

		public MyTask(int id) {
			this.id = id;
		}

		public boolean isSuccess() {
			return this.success;
		}

		@Override
		public String toString() {
			return "MyTask{" +
					"id=" + id +
					", success=" + success +
					'}';
		}

		@Override
		public void run() {
			SleepUtil.processing();
			if (id % 3 == 0)
				throw new RuntimeException("RuntimeException ;-)");
			success = true;
		}

	}

}
