package us.hcheng.javaio.thread.jcu.atomic.chapter2.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample1 {

	public static void main(String[] args) {
//		waitFor2();
//		mainWaitFor2();
		waitFor2Callback();
	}

	private static void waitFor2Callback() {
		CyclicBarrier barrier = new CyclicBarrier(2, () -> {
			System.out.println("ALL COMPLETE - Callback");
		});

		new Thread(()-> {
			try {
				System.out.println("T1 Ready");
				TimeUnit.SECONDS.sleep(1);
				barrier.await();
				System.out.println("T1 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		}).start();

		new Thread(()-> {
			try {
				System.out.println("T2 Ready");
				TimeUnit.SECONDS.sleep(3);
				barrier.await();
				System.out.println("T2 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		}).start();
	}

	private static void mainWaitFor2() {
		CyclicBarrier barrier = new CyclicBarrier(1);
		new Thread(()-> {
			try {
				System.out.println("T1 Ready");
				TimeUnit.SECONDS.sleep(1);
				barrier.await();
				System.out.println("T1 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		}).start();

		new Thread(()-> {
			try {
				System.out.println("T2 Ready");
				TimeUnit.SECONDS.sleep(3);
				barrier.await();
				System.out.println("T2 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		}).start();

		try {
			barrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("ALL COMPLETE");
		}
	}


	private static void waitFor2() {
		CyclicBarrier barrier = new CyclicBarrier(2);
		new Thread(()-> {
			try {
				System.out.println("T1 Ready");
				TimeUnit.SECONDS.sleep(1);
				barrier.await();
				System.out.println("T1 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		}).start();

		new Thread(()-> {
			try {
				System.out.println("T2 Ready");
				TimeUnit.SECONDS.sleep(3);
				barrier.await();
				System.out.println("T2 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		}).start();
	}

}
