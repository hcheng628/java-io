package us.hcheng.javaio.thread.jcu.atomic.chapter2.semaphore;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import us.hcheng.javaio.utils.SleepUtil;

public class SemaphoreExample2 {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(3);

		IntStream.range(0, 3).forEach(each -> {
			new Thread(() -> {
				String name = Thread.currentThread().getName();
				try {
					System.out.println(name + " Ready");
					semaphore.acquire(3);
					SleepUtil.processing();
				} catch (InterruptedException e) {
					System.err.println(name + "\n" + e);
				} finally {
					semaphore.release(100);     // This can lead to a mess -> too many permits
					System.out.println(name + " Complete");
				}
			}).start();
		});

		while (true) {
			SleepUtil.sleep(1000);
			StringBuilder sb = new StringBuilder("**********");
			sb.append("\nAvailablePermits: ").append(semaphore.availablePermits());
			sb.append("\nQueueLength: ").append(semaphore.getQueueLength());
			sb.append("\nHas Queued Threads: ").append(semaphore.hasQueuedThreads());
			sb.append("\nIs Fair: ").append(semaphore.isFair());
			sb.append("\n**********");
			System.out.println(sb);
		}
	}



}
