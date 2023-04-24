package us.hcheng.javaio;

import java.util.concurrent.Future;

public class Util {

	public static void alwaysOn() {
		try {
			Thread.currentThread().join();
		} catch (Exception ex){}
	}

	public static void throwException(String msg) {
		throw new RuntimeException(msg);
	}

	public static String futureInfo(Future future) {
		StringBuilder sb = new StringBuilder();

		try {
			Object info = future.get();
			sb.append('{').append(info).append("} --- ");
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		sb.append(future.toString().replace("java.util.concurrent.CompletableFuture", ""));
		return sb.toString();
	}

}
