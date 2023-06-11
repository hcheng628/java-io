package us.hcheng.javaio.learnhspedu.chapters.chapter19.inputstream;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;

public class FileInputStream_ {

	@Test
	public void readFile01() {
		String filePath = "/Users/nikkima/Desktop/a.txt";
		FileInputStream fileInputStream = null;
		int data = 0;

		try {
			fileInputStream = new FileInputStream(filePath);
			while ( (data = fileInputStream.read()) != -1)
				System.out.println(String.join(":", String.valueOf(data), String.valueOf((char) data)));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException e) {}
		}
	}

	@Test
	public void readFile02() {
		String filePath = "/Users/nikkima/Desktop/a.txt";
		FileInputStream fileInputStream = null;
		byte[] buffer = new byte[8];

		try {
			fileInputStream = new FileInputStream(filePath);
			for (int len = -1; (len = fileInputStream.read(buffer)) != -1; )
				System.out.println(new String(buffer, 0, len));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException e) {}
		}
	}

}
