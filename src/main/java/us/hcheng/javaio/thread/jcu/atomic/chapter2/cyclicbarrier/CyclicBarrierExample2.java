package us.hcheng.javaio.thread.jcu.atomic.chapter2.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierExample2 {

	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(4);

		new Thread(()-> {
			try {
				System.out.println("T0 Ready");
				barrier.await();
				System.out.println("T0 Complete");
			} catch (Exception e) {
				System.err.println("T0 " + e + "\n" + barrierInfo(barrier));
			}
		}).start();

		new Thread(()-> {
			try {
				System.out.println("T1 Ready");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("T1 " + "\n" + barrierInfo(barrier));
				barrier.await(500, TimeUnit.MILLISECONDS);
				System.out.println("T1 Complete");
			} catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
				System.err.println("T1 " + e + "\n" + barrierInfo(barrier));
			}
		}).start();

		Thread t2 = new Thread(()-> {
			try {
				System.out.println("T2 Ready");
				TimeUnit.SECONDS.sleep(2);
				barrier.await();
				System.out.println("T2 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				System.err.println("T2 " + e + "\n" + barrierInfo(barrier));
			}
		});
		t2.start();

		new Thread(()-> {
			try {
				System.out.println("T3 Ready");
				TimeUnit.SECONDS.sleep(1);
				barrier.await();
				System.out.println("T3 Complete");
			} catch (InterruptedException | BrokenBarrierException e) {
				System.err.println("T3 " + e + "\n" + barrierInfo(barrier));
			}
		}).start();

		//TimeUnit.MILLISECONDS.sleep(1100);
		//t2.interrupt();
		//barrier.reset();

		TimeUnit.SECONDS.sleep(6);
		System.out.println("Final: \n" + barrierInfo(barrier));
	}

	private static String barrierInfo(CyclicBarrier barrier) {
		return "Parties: " + barrier.getParties() +
				"\nisBroken: " + barrier.isBroken() + "\nNumberWaiting: " +
				barrier.getNumberWaiting();
	}

}
