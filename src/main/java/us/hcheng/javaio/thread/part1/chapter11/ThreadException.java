package us.hcheng.javaio.thread.part1.chapter11;

import java.util.Arrays;

public class ThreadException {

	public static void main(String[] args) {
		Thread t = new Thread(()->{
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1_000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

				if (i == 3) {
					new T1().test();
					// throw new RuntimeException("Cheng: " + Thread.currentThread().getName());
				}
			}
		});

		t.start();

		t.setUncaughtExceptionHandler((thread, e) -> {
			System.out.println(thread + " has exception... and here is the message:\n" + e);
			Arrays.asList(thread.getStackTrace())
					.stream()
					.filter(element -> !element.isNativeMethod())
					.forEach(element -> {
						System.out.println(element.getClassName() + ":" + element.getMethodName() + ":" + element.getLineNumber());
					});
		});

	}

}
