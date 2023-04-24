package us.hcheng.javaio.thread.jcu.atomic.chapter2.semaphore;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SleepUtil {

	public static void processing() {
		processing("");
	}

	public static void processing(Object obj) {
		try {
			System.out.println(Thread.currentThread() + " Processing... " + obj);
			TimeUnit.SECONDS.sleep(new Random().nextInt(5));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sleep(int mSeconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(mSeconds);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sleepSec(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
