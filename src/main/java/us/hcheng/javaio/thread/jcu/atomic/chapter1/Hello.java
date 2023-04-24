package us.hcheng.javaio.thread.jcu.atomic.chapter1;

public class Hello {
	static {
		System.loadLibrary("hello");
	}

	private native void hi();

	public static void main(String[] args) {
		new Hello().hi();
	}

}
