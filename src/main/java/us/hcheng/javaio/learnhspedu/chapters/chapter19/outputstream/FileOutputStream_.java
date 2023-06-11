package us.hcheng.javaio.learnhspedu.chapters.chapter19.outputstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

public class FileOutputStream_ {

	/**
	 * the parent directory must be present
	 */
	@Test
	public void writeFile01() {
		String filePath = "/Users/nikkima/Desktop/a.txt";
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filePath);
			fileOutputStream.write('M');
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {}
		}
	}

	@Test
	public void writeFile02() {
		String filePath = "/Users/nikkima/Desktop/a.txt";
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filePath, true);
			String msg = "石家庄SFLS";
			fileOutputStream.write(msg.getBytes(StandardCharsets.UTF_8), 0, 3 * 3);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {}
		}
	}

}
