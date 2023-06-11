package us.hcheng.javaio.learnhspedu.chapters.chapter19.transformation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class OutputStreamWriter_ {

	public static void main(String[] args) throws IOException {
		String dest = "/Users/nikkima/Desktop/obj.txt";
		FileOutputStream fileOutputStream = new FileOutputStream(dest);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
		outputStreamWriter.write("程弘宇 来自河北石家庄");
		outputStreamWriter.close();
	}

}
