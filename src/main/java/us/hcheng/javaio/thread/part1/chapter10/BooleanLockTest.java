package us.hcheng.javaio.thread.part1.chapter10;

import java.util.stream.IntStream;

public class BooleanLockTest {

	public static void main(String[] args) throws InterruptedException {
		Lock lock = new BooleanLock();

		IntStream.rangeClosed(1, 1000).sorted().forEach(
				name -> {
					new Thread(()->{
						try {
							lock.lock(10l);
						} catch (InterruptedException e) {
							System.err.println(e);
						} catch (Lock.TimeoutException e) {
							System.err.println(e);
						}

						System.out.println(name + " is working... in run...");
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}

						lock.unlock();
					}, String.join(":", "Thread", String.valueOf(name))).start();
				});

		lock.unlock();
		System.out.println("Done...");

		// lock.getBlockedThread().add(new Thread());

		System.out.println(lock.getBlockedSize());

		System.out.println(lock.getBlockedSize());
	}
}
