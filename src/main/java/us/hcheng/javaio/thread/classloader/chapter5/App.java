package us.hcheng.javaio.thread.classloader.chapter5;

public class App {

	public static void main(String[] args) throws Exception {
		SimpleClassLoader classLoader = new SimpleClassLoader();
		Class<?> klass = classLoader.loadClass("com.cheng.ch4.app.SimpleObject");
		System.out.println(klass.getClassLoader());

		Object obj = klass.getConstructor().newInstance();
		klass.getMethod("hey").invoke(obj);

		Class<?> klass2 = classLoader.loadClass("com.cheng.ch4.app.SimpleObject");

		System.out.println(klass.hashCode() + " vs " + klass2.hashCode());
	}

}
