package us.hcheng.javaio.thread.jcu.atomic.chapter1.entity;

public class CustomObject {

	private static int staticI;
	private int regularI;

	static {
		staticI = 1;
		System.out.println("static...");
	}

	public CustomObject() {
		regularI = 1;
		System.out.println("constructor...");
	}

	public static int getStaticI() {
		return staticI;
	}

	public int getRegularI() {
		return regularI;
	}

}
