package us.hcheng.javaio.thread.jcu.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListClient {

	public static <T> CopyOnWriteArrayList<T> getInstance() {
		return new CopyOnWriteArrayList<>();
	}

}
