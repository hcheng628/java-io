package us.hcheng.javaio.thread.classloader.chapter5;

public class AppCastSameClass {

	public static void main(String[] args) throws Exception {
		SimpleClassLoader classLoader = new SimpleClassLoader();
		ClassLoader loader = AppCastSameClass.class.getClassLoader();
		Class<?> klass1 = classLoader.loadClass("com.cheng.ch5.EmptyClass");
		Class<?> klass2 = loader.loadClass("com.cheng.ch5.EmptyClass");
		System.out.println(klass1.hashCode() + " vs " + klass2.hashCode());

		Object obj1 = klass1.getConstructor().newInstance();
		klass1.getMethod("hey").invoke(obj1);

		Object obj2 = klass2.getConstructor().newInstance();
		klass2.getMethod("hey").invoke(obj2);


		//EmptyClass emptyClass = (EmptyClass) obj1;

		try {
			System.out.println("AppClassloader cast: " + ((EmptyClass) obj2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			System.out.println("SimpleClassLoader cast: " + ((EmptyClass) obj1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
