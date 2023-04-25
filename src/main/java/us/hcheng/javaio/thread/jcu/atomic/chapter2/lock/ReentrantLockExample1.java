package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import us.hcheng.javaio.utils.SleepUtil;

public class ReentrantLockExample1 {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();

		IntStream.range(0, 3).forEach(i -> {
			new Thread(() -> {
				//threadSafeLock(lock);
				threadSafeSync();
			}).start();
		});
	}

	private static void threadSafeLock(ReentrantLock lock) {
		boolean locked = false;
		try {
			lock.lock();
			locked = true;
			System.out.println(Thread.currentThread() + "...");
			SleepUtil.sleep(3000);
		} finally {
			if (locked)
				lock.unlock();
			System.out.println(Thread.currentThread() + "... Done");
		}
	}

	private static void threadSafeSync() {
		synchronized (ReentrantLockExample1.class) {
			System.out.println(Thread.currentThread() + "...");
			SleepUtil.sleep(3000);
		}
	}

}
