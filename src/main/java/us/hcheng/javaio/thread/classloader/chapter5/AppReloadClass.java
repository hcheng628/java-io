package us.hcheng.javaio.thread.classloader.chapter5;

public class AppReloadClass {

	public static void main(String[] args) throws Exception {
//		SimpleClassLoader classLoader = new SimpleClassLoader();
//		Class<?> klass1 = classLoader.loadClass("com.cheng.ch4.app.SimpleObject");
//		Class<?> klass2 = classLoader.loadClass("com.cheng.ch4.app.SimpleObject");

		ClassLoader loader = AppReloadClass.class.getClassLoader();

		System.out.println(loader);
		Class<?> klass1 = loader.loadClass("com.cheng.ch4.app.SimpleObject");
		Class<?> klass2 = loader.loadClass("com.cheng.ch4.app.SimpleObject");
		System.out.println(klass1.hashCode() + " vs " + klass2.hashCode());

//		System.out.println(klass.getClassLoader());
//		Object obj = klass.getConstructor().newInstance();
//		klass.getMethod("hey").invoke(obj);
	}

}
