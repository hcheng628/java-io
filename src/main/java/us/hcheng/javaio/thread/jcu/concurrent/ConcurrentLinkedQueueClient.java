package us.hcheng.javaio.thread.jcu.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueClient {

	public static <T> ConcurrentLinkedQueue<T> getInstance() {
		return new ConcurrentLinkedQueue<>();
	}

}
