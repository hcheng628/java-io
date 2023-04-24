package us.hcheng.javaio.thread.classloader.chapter5;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class SimpleClassLoader extends ClassLoader {
	private static final String INTELLJ = "/Users/hongyu.cheng/Desktop/Cheng/Moka/out/production/Moka/";
	private static final String DESKTOP = "/Users/hongyu.cheng/Desktop/haha/";
	private static final String DEFAULT_DIR = INTELLJ;

	public SimpleClassLoader() {
		super();
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classFile = loadClassFile(name);
		//System.out.println(name);
		return super.defineClass(name, classFile, 0, classFile.length);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> ret = null;

		if (name.startsWith("java."))
			return ClassLoader.getSystemClassLoader().loadClass(name);

		ret = findLoadedClass(name);
		if (ret != null)
			return ret;

		try {
			ret = findClass(name);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (ret == null && getParent() != null)
			ret = getParent().loadClass(name);

		return ret;
	}
	private byte[] loadClassFile(String name) {
		String path = DEFAULT_DIR.concat(String.join(".", name.replace(".", "/"), "class"));
		System.out.println("path: " + path);
		try (FileInputStream fileInputStream = new FileInputStream(path);
		     ByteArrayOutputStream outputStream =  new ByteArrayOutputStream()) {
			for (int data = 0; (data = fileInputStream.read()) != -1; )
				outputStream.write(data);
			return outputStream.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}
}
