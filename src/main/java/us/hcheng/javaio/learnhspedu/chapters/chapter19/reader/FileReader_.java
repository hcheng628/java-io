package us.hcheng.javaio.learnhspedu.chapters.chapter19.reader;

import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class FileReader_ {

	@Test
	public void readFile01() {
		String src = "/Users/nikkima/Desktop/a.txt";
		try (FileReader fileReader  = new FileReader(src)) {
			for (int data = -1; (data = fileReader.read()) != -1; )
				System.out.println((char)data);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void readFile02() {
		String src = "/Users/nikkima/Desktop/a.txt";
		char[] buffer = new char[8];

		try (FileReader fileReader  = new FileReader(src)) {
			for (int len = -1; (len = fileReader.read(buffer)) != -1; )
				System.out.println(new String(buffer, 0, len));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
