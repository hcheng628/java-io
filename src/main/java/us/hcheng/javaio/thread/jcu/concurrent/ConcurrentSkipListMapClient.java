package us.hcheng.javaio.thread.jcu.concurrent;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapClient {

	public static <K, V> ConcurrentSkipListMap<K, V> getInstance() {
		return new ConcurrentSkipListMap<>();
	}

}
