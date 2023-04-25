package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import us.hcheng.javaio.utils.SleepUtil;

public class ReentrantLockExample3 {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		List<Thread> threads = new ArrayList<>();
		IntStream.range(0, 3).forEach(i -> {
			threads.add(new Thread(() -> threadSafeLock(lock)));
		});
		threads.forEach(Thread::start);
		SleepUtil.sleep(1000);
		//threads.get(2).interrupt();
	}

	private static void threadSafeLockNoWait(ReentrantLock lock) {
		Thread t = Thread.currentThread();
		if (lock.tryLock()) {
			try {
				System.out.println(t.getName() + "...");
				while (true)
					if (new Random().nextInt(100000000) == 1)
						break;
			} finally {
				lock.unlock();
				System.out.println(t + "... Done: " + Thread.currentThread().isInterrupted());
			}
		} else {
			System.err.println("Not doing any work... :-)");
		}
	}

	private static void threadSafeLock(ReentrantLock lock) {
		Thread t = Thread.currentThread();
		try {
			if (lock.tryLock(1000, TimeUnit.DAYS)) {
				try {
					if (t.getName().contains("1")) {
						lock.tryLock(); // hold count will be 2
					}
					System.out.println(t + "... Hold Count: " + lock.getHoldCount());
					if (t.getName().contains("2")) {
						System.out.println("throw new RuntimeException(\"haha\");");
						throw new RuntimeException("haha");
					}

					while (true)
						if (new Random().nextInt(100000000) == 1)
							break;
				} finally {
					lock.unlock();
					if (t.getName().contains("1"))
						lock.unlock();
					System.out.println(t + "... Done and unlocked thread info: " + Thread.currentThread().isInterrupted());
				}
			} else {
				System.out.println("CHENG ELSE");
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			System.err.println("InterruptedException ...");
		} catch (Exception ex) {
			System.err.println("Exception ..." + ex);
		}

		// System.err.println("Not doing any work... :-)");
	}


}
