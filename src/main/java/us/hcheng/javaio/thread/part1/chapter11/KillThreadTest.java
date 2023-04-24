package us.hcheng.javaio.thread.part1.chapter11;

public class KillThreadTest {


	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("calling notifyAndReleaseRes ..." + Thread.currentThread().getName());
			try {
				notifyAndReleaseRes();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}));

		new Thread(()->{
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1_000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

				if (i == 3 && new Object() == new Object())
					throw new RuntimeException("Cheng: " + Thread.currentThread().getName());
			}
		}).start();
	}

	private static void notifyAndReleaseRes() throws InterruptedException {
		System.out.println("Admin should get this email and text...");
		Thread.sleep(1_000);
		System.out.println("All res have been released: socket, connections, ...");
	}
}
