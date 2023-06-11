package us.hcheng.javaio.learnhspedu.chapters.chapter19.printstream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStream_ {

	public static void main(String[] args) throws IOException {
		OutputStream out = System.out;
		out.write("HDFSA".getBytes());
		out.close();

		new PrintStream(System.out).println("Moka");

		PrintStream printFile = new PrintStream("/Users/nikkima/Desktop/a.txt");
		printFile.write("I want a burger".getBytes());
		System.setOut(printFile);
		System.out.println("Moka");

		BufferedOutputStream bufferedInputStream = new BufferedOutputStream(printFile);
		bufferedInputStream.write("Haha hehe".getBytes());
		bufferedInputStream.close();
	}

}
