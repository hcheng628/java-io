package us.hcheng.javaio.learnhspedu.chapters.chapter23.reflection;

import us.hcheng.javaio.learnhspedu.chapters.chapter23.entity.Cat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class Reflection02 {

    private static final int END = 100000000;

    public static void main(String[] args) {
        run(v -> Reflection02.regularCall());
        run(v -> Reflection02.reflectionCall());
        run(v -> Reflection02.reflectionNoCheckCall());
    }

    public static void run(Consumer<Void> consumer) {
        long start = System.nanoTime();
        consumer.accept(null);
        System.out.println(System.nanoTime() - start);
    }

    /**
     * 100x faster than reflectionCall(), on apple M1 pro processor, Oracle 17 JDK
     */
    public static void regularCall() {
        Cat cat = new Cat();
        for (int i = 0; i < END; i++)
            cat.hi();
    }

    public static void reflectionCall() {
        try {
            Class<?> klass = Class.forName("us.hcheng.javaio.learnhspedu.chapters.chapter23.entity.Cat");
            Cat cat = (Cat) klass.getDeclaredConstructor().newInstance();
            Method hi = klass.getDeclaredMethod("hi");
            for (int i = 0; i < END; i++)
                hi.invoke(cat);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            System.err.println("I dont care but want to list all the exception types...");
        }
    }

    /**
     * setAccessible(true) makes it 2x faster, on apple M1 pro processor, Oracle 17 JDK
     */
    public static void reflectionNoCheckCall() {
        try {
            Class<?> klass = Class.forName("us.hcheng.javaio.learnhspedu.chapters.chapter23.entity.Cat");
            Cat cat = (Cat) klass.getDeclaredConstructor().newInstance();
            Method hi = klass.getDeclaredMethod("hi");
            hi.setAccessible(true);
            for (int i = 0; i < END; i++)
                hi.invoke(cat);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            System.err.println("I dont care but want to list all the exception types...");
        }
    }

}
