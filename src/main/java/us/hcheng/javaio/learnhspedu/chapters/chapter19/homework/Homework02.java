package us.hcheng.javaio.learnhspedu.chapters.chapter19.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Homework02 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/nikkima/Desktop/abc.js"));
		PrintStream writer = new PrintStream(System.out);

		for (String msg = null; (msg = br.readLine()) != null; ) {
			writer.println(new StringBuilder(msg).append(','));
			//System.out.println(new StringBuilder(msg).append(','));
		}
	}

}
