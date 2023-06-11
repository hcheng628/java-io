package us.hcheng.javaio.learnhspedu.chapters.chapter19.homework;

import java.io.File;
import java.io.IOException;

public class Homework01 {

	public static void main(String[] args) throws IOException {
		File dir = new File("/Users/nikkima/Desktop/mytemp");
		if (!dir.exists())
			dir.mkdir();

		if (!dir.exists())
			return;

		File hello = new File(dir, "hello.txt");
		System.out.println(hello.exists() ? "hello is there" : "create hello.txt " + hello.createNewFile());
	}

}
