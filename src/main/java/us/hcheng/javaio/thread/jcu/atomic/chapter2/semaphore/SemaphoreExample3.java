package us.hcheng.javaio.thread.jcu.atomic.chapter2.semaphore;

import java.util.concurrent.Semaphore;
import us.hcheng.javaio.utils.SleepUtil;

public class SemaphoreExample3 {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(1);

		Thread t1 = new Thread(() -> {
			String name = Thread.currentThread().getName();
			try {
				semaphore.acquire();
				System.out.println(name + " got permit!");
				SleepUtil.sleep(2000);
			} catch (Exception e) {
				System.err.println(name + "\n" + e);
			} finally {
				semaphore.release();     // This can lead to a mess -> too many permits
				System.out.println(name + " Complete and now Available Permits: " + semaphore.availablePermits() + " " + Thread.currentThread().isInterrupted());
			}
		}, "T1");
		t1.start();
		SleepUtil.sleep(100);

		Thread t2 = new Thread(() -> {
			String name = Thread.currentThread().getName();
			try {
				semaphore.acquireUninterruptibly();
				System.out.println(name + " got permit!");
			} catch (Exception e) {
				System.err.println(name + "\n" + e);
			} finally {
				semaphore.release();     // This can lead to a mess -> too many permits
				System.out.println(name + " Complete and now Available Permits: " + semaphore.availablePermits() + " " + Thread.currentThread().isInterrupted());
			}
		}, "T2");
		t2.start();

		SleepUtil.sleep(500);
		t2.interrupt();
		System.out.println("availablePermits: " + semaphore.availablePermits());
	}

}
