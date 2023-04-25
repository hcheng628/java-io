package us.hcheng.javaio.thread.jcu.atomic.chapter2.semaphore;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import us.hcheng.javaio.utils.SleepUtil;

public class SemaphoreExample4 {

	public static void main(String[] args) {
		drainPermitsAPI();
		getQueuedThreadsAPI();
		shouldNotRelease();
	}

	/**
	 * In this case T2 exit without getting the permit but still released one in finally
	 * block and it caused T3 to execute without waiting for T1 to finish.
	 */
	private static void shouldNotRelease() {
		final Semaphore semaphore = new Semaphore(1);

		new Thread(()->{
			try {
				semaphore.acquire();
				SleepUtil.sleep(30000);
			} catch (Exception ex) {
				System.out.println(Thread.currentThread().getName() + " " + ex);
			} finally {
				semaphore.release();
				System.out.println(Thread.currentThread().getName() + " finally release availablePermits: " + semaphore.availablePermits());

			}
		}, "T1").start();
		SleepUtil.sleep(500);

		Thread t2 = new Thread(() -> {
			try {
				semaphore.acquire();
				SleepUtil.sleep(30000);
			} catch (Exception ex) {
				System.out.println(Thread.currentThread().getName() + " " + ex);
			} finally {
				semaphore.release();
				System.out.println(Thread.currentThread().getName() + " finally release availablePermits: " + semaphore.availablePermits());
			}
		}, "T2");

		new Thread(()->{
			try {
				semaphore.acquire();
				SleepUtil.sleep(30000);
			} catch (Exception ex) {
				System.out.println(Thread.currentThread().getName() + " " + ex);
			} finally {
				semaphore.release();
				System.out.println(Thread.currentThread().getName() + " finally release availablePermits: " + semaphore.availablePermits());
			}
		}, "T3").start();

		t2.start();
		SleepUtil.sleep(500);
		t2.interrupt();
	}

	private static void drainPermitsAPI() {
		final Semaphore semaphore = new Semaphore(5);
		Thread t1 = new Thread(() -> {
			String name = Thread.currentThread().getName();
			int permits = 0;
			try {
				permits = semaphore.drainPermits();
				System.out.println(name + " got permit!");
				SleepUtil.sleep(2000);
			} catch (Exception e) {
				System.err.println(name + "\n" + e);
			} finally {
				semaphore.release(permits);
				System.out.println(name + " Complete and now Available Permits: " + semaphore.availablePermits() + " " + Thread.currentThread().isInterrupted());
			}
		}, "T1");
		t1.start();

		SleepUtil.sleep(500);

		Thread t2 = new Thread(() -> {
			String name = Thread.currentThread().getName();
			int permits = 0;
			try {
				permits = semaphore.drainPermits();
				System.out.println(name + " got permit!");
				SleepUtil.sleep(2000);
			} catch (Exception e) {
				System.err.println(name + "\n" + e);
			} finally {
				semaphore.release(permits);
				System.out.println(name + " Complete and now Available Permits: " + semaphore.availablePermits() + " " + Thread.currentThread().isInterrupted());
			}
		}, "T2");
		t2.start();
	}


	private static void getQueuedThreadsAPI() {
		MySemaphore semaphore = new MySemaphore(1);

		IntStream.range(0, 10).forEach(i -> {
			new Thread(() -> {
				try {
					semaphore.acquire();
					SleepUtil.sleep(3000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					semaphore.release();
				}
			}).start();
		});

		IntStream.range(0, 10).forEach(i -> {
			SleepUtil.sleep(1000);
			System.out.println("**********");
			System.out.println("\n" + (i+1) + " size: " + (semaphore.getQueuedThreads().size()) + ":");
			for (Thread t : semaphore.getQueuedThreads()) {
				System.out.println(t);
			}
			System.out.println("**********");
		});
	}


	static class MySemaphore extends Semaphore {
		public MySemaphore(int permits) {
			super(permits);
		}

		public Collection<Thread> getQueuedThreads() {
			return super.getQueuedThreads();
		}
	}
}
