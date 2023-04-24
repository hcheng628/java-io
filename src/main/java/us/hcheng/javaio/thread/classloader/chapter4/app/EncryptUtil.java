package us.hcheng.javaio.thread.classloader.chapter4.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class EncryptUtil {
	public static final byte ENCRYPT_FACTOR = (byte) 0xf1;

	public static void doEncrypt(String src, String dest) {
		try (FileInputStream fileInputStream = new FileInputStream(src);
		     FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
			for (int data = 0; (data = fileInputStream.read()) != -1; )
				fileOutputStream.write(data ^ ENCRYPT_FACTOR);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
