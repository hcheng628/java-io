package us.hcheng.javaio.thread.jcu.atomic.chapter2.semaphore;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import us.hcheng.javaio.utils.SleepUtil;

public class SemaphoreExample1 {

	private final Semaphore fSemaphore = new Semaphore(3);

	private void lock() throws InterruptedException {
		this.fSemaphore.acquire();
	}

	private void unlock() {
		this.fSemaphore.release();
	}

	public static void main(String[] args) {
		SemaphoreExample1 lock = new SemaphoreExample1();
		IntStream.range(0, 3).forEach(each -> {
			new Thread(() -> {
				try {
					System.out.println(Thread.currentThread() + " Ready");
					lock.lock();
					SleepUtil.processing();
				} catch (InterruptedException e) {
					System.err.println(Thread.currentThread() + "\n" + e);
				} finally {
					System.out.println(Thread.currentThread() + " Complete");
					lock.unlock();
				}
			}).start();
		});
	}

}
