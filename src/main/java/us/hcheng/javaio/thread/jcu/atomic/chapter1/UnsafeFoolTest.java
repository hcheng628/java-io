package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import sun.misc.Unsafe;
import us.hcheng.javaio.thread.jcu.atomic.chapter1.entity.CustomObject;

public class UnsafeFoolTest {

	public static void main(String[] args) throws Exception {
		Unsafe unsafe = getUnsafe();
		/*
		CustomObject.class.newInstance();   // both static block and constructor
		Class.forName("com.cheng.jcu.ch1.entity.CustomObject");     // just static block

		CustomObject o = (CustomObject) unsafe.allocateInstance(CustomObject.class);    // just static block
		System.out.println(o.getClass());
		System.out.println(o.getClass().getClassLoader());
		*/

		// setObjectFieldValue(unsafe);

		// unsafeLoadClass(unsafe);

		System.out.println(sizeOf(unsafe, new Person()));
	}

	public static long sizeOf(Unsafe unsafe, Object o) throws Exception {
		Set<Field> fields = new HashSet<>();

		for (Class<?> klass = o.getClass(); klass != null; klass = klass.getSuperclass()) {
			Field[] fieldArr = klass.getDeclaredFields();
			for (Field f : fieldArr)
				if ((f.getModifiers() & Modifier.STATIC) == 0)
					fields.add(f);
				else {
					System.out.println("skipping field: " + f);
				}
		}

		for (Field f : fields) {
			long offset = unsafe.objectFieldOffset(f);
			System.out.println(f + " offset: " + offset);
		}

		return 0;
	}

	public static void setObjectFieldValue(Unsafe unsafe) throws Exception {
		CustomObject o = (CustomObject) unsafe.allocateInstance(CustomObject.class);    // just static block
		Field regularField = CustomObject.class.getDeclaredField("regularI");
		Field staticField = CustomObject.class.getDeclaredField("staticI");

		unsafe.putInt(o, unsafe.objectFieldOffset(regularField), 33);           // even this field is private
		// unsafe.putInt(CustomObject.class, unsafe.objectFieldOffset(staticField), 33); // Did not get static field to work...
		System.out.println(o.getRegularI() + " vs " + CustomObject.getStaticI());
	}

	public static Unsafe getUnsafe() {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		} catch (Exception ex) {
			return null;
		}
	}

}
