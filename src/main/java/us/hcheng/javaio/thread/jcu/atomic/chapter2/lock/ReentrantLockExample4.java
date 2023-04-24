package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import java.util.concurrent.locks.ReentrantLock;

import com.cheng.jcu.ch2.semaphore.SleepUtil;

public class ReentrantLockExample4 {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();

		Thread t1 = new Thread(()->{
			try {
				lock.lockInterruptibly();
				while (!Thread.interrupted()) {
					System.out.println(Thread.interrupted());
				}
				lock.unlock();
			} catch (InterruptedException e) {
				System.out.println("T1 InterruptedException");
			}
		});

		Thread t2 = new Thread(()->{
			try {
				lock.lockInterruptibly();
				System.out.println("T2 ...");
				lock.unlock();
			} catch (InterruptedException e) {
				System.out.println("T2 InterruptedException");

			}
		});

		t1.start();
		SleepUtil.sleep(500);
		t2.start();
		SleepUtil.sleep(500);
		t1.interrupt();
		System.out.println("t1.interrupt()");
	}




}
