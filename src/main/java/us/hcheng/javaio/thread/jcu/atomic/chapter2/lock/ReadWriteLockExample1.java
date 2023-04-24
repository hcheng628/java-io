package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ReadWriteLockExample1 {
	private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	private final static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	private final static List<Long> data = new ArrayList<>(Arrays.asList(13L));

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(ReadWriteLockExample1::read);
		Thread t2 = new Thread(ReadWriteLockExample1::read);

		t1.start();
		SleepUtil.sleep(500);
		t2.start();

		t1.join();
		t2.join();
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void write() {
		try {
			writeLock.lock();
			SleepUtil.sleep(5000);
			System.out.println("...");
			data.add(System.currentTimeMillis());
		} finally {
			writeLock.unlock();
		}
	}

	public static void read() {
		try {
			readLock.lock();
			SleepUtil.sleep(3000);
			data.forEach(System.out::println);
		} finally {
			readLock.unlock();
		}
	}
}
