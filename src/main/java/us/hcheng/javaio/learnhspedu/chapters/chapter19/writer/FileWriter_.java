package us.hcheng.javaio.learnhspedu.chapters.chapter19.writer;

import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;

public class FileWriter_ {

	@Test
	public void writeFile01() {
		String dest = "/Users/nikkima/Desktop/b.txt";
		try (FileWriter fileWriter  = new FileWriter(dest)) {
			fileWriter.write(97); // should be a
			fileWriter.write(System.lineSeparator());

			fileWriter.write("I LOVE MOKA");
			fileWriter.write(System.lineSeparator());

			fileWriter.write("Shijiazhuang", 0, 6);
			fileWriter.write(System.lineSeparator());

			fileWriter.write(new char[]{'程', '弘', '宇'});
			fileWriter.write(System.lineSeparator());

			fileWriter.write(new char[]{'程', '弘', '宇'}, 1, 2);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
