package us.hcheng.javaio.thread.part1.chapter11;

import java.util.Arrays;

public class T2 {

	public void test() {
		System.out.println("test...");
		Arrays.asList(Thread.currentThread().getStackTrace())
				.stream()
				.filter(element -> !element.isNativeMethod())
				.forEach(element -> {
					System.out.println(element.getClassName() + ":" + element.getMethodName() + ":" + element.getLineNumber());
				});

		int abs = 30/0;
	}

}
