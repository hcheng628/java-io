package us.hcheng.javaio.learnhspedu.chapters.chapter19.writer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedCopyReaderToWriter {

	/**
	 * text based copy, unsafe binary based copy
	 */
	public static void main(String[] args) {
		String src = "/Users/nikkima/Desktop/a.txt";
		String dest = "/Users/nikkima/Desktop/aa.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(src));
		     BufferedWriter bw = new BufferedWriter(new FileWriter(dest))) {
			for (String line = null; (line = br.readLine()) != null; )
				bw.write(line);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
