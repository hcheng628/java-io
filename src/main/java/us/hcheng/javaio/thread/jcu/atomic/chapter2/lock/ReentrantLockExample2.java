package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ReentrantLockExample2 {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		List<Thread> threads = new ArrayList<>();

		IntStream.range(0, 3).forEach(i -> {
			//threads.add(new Thread(() -> threadSafeLockNoInterrupt(lock)));
			threads.add(new Thread(() -> threadSafeLock(lock)));
		});

		threads.get(0).start();
		SleepUtil.sleep(100);
		threads.get(1).start();
		threads.get(2).start();

		SleepUtil.sleep(500);
		threads.get(2).interrupt(); // cannot interrupt
	}

	private static void threadSafeLockNoInterrupt(ReentrantLock lock) {
		boolean locked = false;
		try {
			lock.lock();
			locked = true;
			System.out.println(Thread.currentThread() + "...");
			while (true)
				if (new Random().nextInt(100000000) == 1)
					break;
		} finally {
			if (locked)
				lock.unlock();
			System.out.println(Thread.currentThread() + "... Done: " + Thread.currentThread().isInterrupted());
		}
	}

	private static void threadSafeLock(ReentrantLock lock) {
		boolean locked = false;
		try {
			lock.lockInterruptibly();
			locked = true;
			System.out.println(Thread.currentThread() + "...");
			while (true)
				if (new Random().nextInt(100000000) == 1)
					break;
		} catch (InterruptedException e) {
			System.err.println(Thread.currentThread() + " InterruptedException...");
		} finally {
			if (locked)
				lock.unlock();
			System.out.println(Thread.currentThread() + "... Done: " + Thread.currentThread().isInterrupted());
		}
	}

}
