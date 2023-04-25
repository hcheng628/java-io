package us.hcheng.javaio.thread.jcu.collections;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeClient {

	public static <T> LinkedBlockingDeque<T> getInstance() {
		return new LinkedBlockingDeque<>();
	}

}
