package us.hcheng.javaio.thread.classloader.chapter4.app;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class DecryptClassLoader extends ClassLoader {
	private static final String DEFAULT_DIR = "/Users/hongyu.cheng/Desktop/haha/";

	public DecryptClassLoader() {
		super();
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classFile = loadClassFile(name);
		return super.defineClass(name, classFile, 0, classFile.length);
	}

	private byte[] loadClassFile(String name) {
		String path = DEFAULT_DIR.concat(String.join(".", name.replace(".", "/"), "class"));
		try (FileInputStream fileInputStream = new FileInputStream(path);
		     ByteArrayOutputStream outputStream =  new ByteArrayOutputStream()) {
			for (int data = 0; (data = fileInputStream.read()) != -1; )
				outputStream.write(data ^ EncryptUtil.ENCRYPT_FACTOR);
			return outputStream.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

}
