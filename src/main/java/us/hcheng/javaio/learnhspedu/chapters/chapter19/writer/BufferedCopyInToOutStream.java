package us.hcheng.javaio.learnhspedu.chapters.chapter19.writer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedCopyInToOutStream {

	/**
	 * Since stream copy(read and write) is done in the lower(byte) level,
	 * it will work both for binary and text based file copies
	 */
	public static void main(String[] args) {
		String src = "/Users/nikkima/Desktop/movie.txt";
		String dest = "/Users/nikkima/Desktop/movie_.txt";

		try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(src));
		     BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dest))) {
			byte[] buffer = new byte[512];
			for (int len = -1; (len = inputStream.read(buffer)) != -1; )
				outputStream.write(buffer, 0, len);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
