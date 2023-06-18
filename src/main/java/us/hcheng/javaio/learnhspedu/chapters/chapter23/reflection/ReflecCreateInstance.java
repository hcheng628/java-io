package us.hcheng.javaio.learnhspedu.chapters.chapter23.reflection;

import java.lang.reflect.Constructor;

public class ReflecCreateInstance {

    /**
     *
     * Declared keyword implies to return all constructors,
     * the other only return public constructors
     */
    private static void getConstructorsVSDeclaredConstructors(Class<User> userClass) {
        int count = 1;
        for (Constructor constructor: userClass.getConstructors()) {
            System.out.println("*** " + count + " ***");
            for (Class c : constructor.getParameterTypes())
                System.out.println(c);
            System.out.println("*** " + count + " ***");
            count++;
        }

        count = 1;
        for (Constructor constructor: userClass.getDeclaredConstructors()) {
            System.out.println("*** " + count + " ***");
            for (Class c : constructor.getParameterTypes())
                System.out.println(c);
            System.out.println("*** " + count + " ***");
            count++;
        }
    }

    private static void getConstructorVSDeclaredConstructor(Class<User> userClass) {
        try {
            Constructor<User> constructor = userClass.getConstructor();
            System.out.println(constructor + " no exception occurs :-)");
        } catch (NoSuchMethodException ex) {}

        try {
            userClass.getConstructor(float.class);
        } catch (NoSuchMethodException ex) {
            System.err.println("NoSuchMethodException because getConstructor can only access public constructors");
        }

        try {
            Constructor<User> userConstructor = userClass.getDeclaredConstructor(float.class);
            System.out.println("I can get this getDeclaredConstructor :-)");
            try {
                //User user = userConstructor.newInstance("12.3f");      // IllegalArgumentException
                //User user = userConstructor.newInstance(12.3d);        // IllegalArgumentException
                User user =  userConstructor.newInstance(12.3f);
            } catch (Exception ex) {}
        } catch (NoSuchMethodException ex) {}

        try {
            Constructor<User> userConstructor = userClass.getDeclaredConstructor(int.class, String.class);
            System.out.println("I can get this getDeclaredConstructor :-)");
            try {
                userConstructor.newInstance(12, "moka");
            } catch (Exception ex) {
                System.err.println("IllegalAccessException i can get this userConstructor but this is private i cannot access it");
            }
        } catch (NoSuchMethodException ex) {}

        try {
            Constructor<User> userConstructor = userClass.getDeclaredConstructor(int.class, String.class);
            System.out.println("I can get this getDeclaredConstructor :-)");
            try {
                userConstructor.setAccessible(true);
                userConstructor.newInstance(12, "moka");
                System.err.println("i can invoke it because i now setAccessible(true)");
            } catch (Exception ex) {}
        } catch (NoSuchMethodException ex) {}
    }

    public static void main(String[] args) {
        getConstructorsVSDeclaredConstructors(User.class);
        getConstructorVSDeclaredConstructor(User.class);
    }

}

class User {
    private int age = 10;
    private String name = "韩顺平教育";

    public User() {//无参 public
    }

    User(float f) {
        System.err.println("User(float aaa)");
    }

    public User(String name) {
        this.name = name;
        System.err.println("public User(String name)");
    }

    private User(int age, String name) {
        this.age = age;
        this.name = name;
        System.err.println("private User(int age, String name)");
    }

    public String toString() {
        return "User [age=" + age + ", name=" + name + "]";
    }
}