package us.hcheng.javaio.learnhspedu.chapters.chapter19.outputstream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;

public class FileCopy {

	@Test
	public void copyFile01() {
		String src = "/Users/nikkima/Desktop/a.txt";
		String dest = "/Users/nikkima/Desktop/a_copy.txt";
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;

		try {
			fileInputStream = new FileInputStream(src);
			fileOutputStream = new FileOutputStream(dest);
			for (int data = -1; (data = fileInputStream.read()) != -1; )
				fileOutputStream.write(data);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException e) {}

			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {}
		}
	}

	@Test
	public void copyFile02() {
		boolean success = true;
		final String src = "/Users/nikkima/Desktop/a.txt";
		final String dest = "/Users/nikkima/Desktop/a_copy.txt";
		final byte[] buffer = new byte[8];

		try (FileInputStream fileInputStream = new FileInputStream(src);
		     FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
			for (int len = -1; (len = fileInputStream.read(buffer)) != -1; )
				fileOutputStream.write(buffer, 0, len);
		} catch (IOException ex) {
			ex.printStackTrace();
			success = false;
		}

		System.out.println("拷贝" + (success ? "成功" : "失败"));
	}

}
