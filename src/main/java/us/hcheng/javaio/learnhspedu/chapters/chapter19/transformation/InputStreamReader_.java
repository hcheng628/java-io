package us.hcheng.javaio.learnhspedu.chapters.chapter19.transformation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputStreamReader_ {

	public static void main(String[] args) throws IOException {
		regular();
		buffer();
	}

	private static void regular() throws IOException{
		String src = "/Users/nikkima/Desktop/obj.txt";
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(src), "gbk");
		for (int data = -1; (data = inputStreamReader.read()) != -1; )
			System.out.println((char)data);

		inputStreamReader.close();
	}

	private static void buffer() throws IOException{
		String src = "/Users/nikkima/Desktop/obj.txt";
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(src), "gbk");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		for (String data = null; (data = bufferedReader.readLine()) != null; )
			System.out.println(data);

		inputStreamReader.close();
	}
}
