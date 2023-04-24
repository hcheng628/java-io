package us.hcheng.javaio.thread.jcu.atomic.chapter2.cyclicbarrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample3 extends CountDownLatch {

	private final Runnable runnable;

	public CyclicBarrierExample3(int count, Runnable runnable) {
		super(count);
		this.runnable = runnable;
	}

	@Override
	public void countDown() {
		super.countDown();
		if (this.getCount() == 0) {
			runnable.run();
		}
	}

	public static void main(String[] args) {
		CyclicBarrierExample3 barrier = new CyclicBarrierExample3(2, () -> {
			System.out.println("ALL DONE!");
		});

		new Thread(() -> {
			try {
				System.out.println("T1 Ready");
				TimeUnit.SECONDS.sleep(1);
				barrier.countDown();
				barrier.await();
				System.out.println("T1 Complete");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				System.out.println("T2 Ready");
				TimeUnit.SECONDS.sleep(6);
				barrier.countDown();
				barrier.await();
				System.out.println("T2 Complete");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start();
	}
}
