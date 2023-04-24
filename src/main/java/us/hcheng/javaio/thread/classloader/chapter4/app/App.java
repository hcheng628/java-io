package us.hcheng.javaio.thread.classloader.chapter4.app;

import java.lang.reflect.InvocationTargetException;

public class App {

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		/*
		// 1st
		String src = "/Users/hongyu.cheng/Desktop/haha/moka.txt";
		String dest = "/Users/hongyu.cheng/Desktop/haha/encrypt.txt";
		EncryptUtil.doEncrypt(src, dest);

		// 2nd
		String src = "/Users/hongyu.cheng/Desktop/haha/encrypt.txt";
		String dest = "/Users/hongyu.cheng/Desktop/haha/plain.txt";
		EncryptUtil.doEncrypt(src, dest);
		*/

		/*
		String src = "/Users/hongyu.cheng/Desktop/haha/com/cheng/ch4/app/SimpleObject.class";
		String dest = "/Users/hongyu.cheng/Desktop/haha/com/cheng/ch4/app/SimpleObject2.class";
		EncryptUtil.doEncrypt(src, dest);
		*/

		DecryptClassLoader classLoader = new DecryptClassLoader();
		Class<?> klass = classLoader.loadClass("com.cheng.ch4.app.SimpleObject");

		Object object = klass.getConstructor().newInstance();
		klass.getMethod("hey", null).invoke(object, null);
	}

}
