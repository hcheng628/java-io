package us.hcheng.javaio.thread.classloader.chapter5;

public class AppLoadSameClass {

	public static void main(String[] args) throws Exception {
		SimpleClassLoader classLoader = new SimpleClassLoader();
		ClassLoader loader = AppLoadSameClass.class.getClassLoader();

		Class<?> klass1 = classLoader.loadClass("com.cheng.ch4.app.SimpleObject");
		Class<?> klass2 = loader.loadClass("com.cheng.ch4.app.SimpleObject");
		System.out.println(klass1.hashCode() + " vs " + klass2.hashCode());

		Object obj1 = klass1.getConstructor().newInstance();
		klass1.getMethod("hey").invoke(obj1);

		Object obj2 = klass2.getConstructor().newInstance();
		klass2.getMethod("hey").invoke(obj2);
	}

}
