package us.hcheng.javaio.thread.classloader.chapter2;

public class BootClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        String olderJava = "sum.boot.class.path";
        String newerJava = "jdk.boot.class.path.append";
        System.out.println(System.getProperty(newerJava));
        System.out.println(System.getProperty("java.ext.dirs"));

        Class<?> klass = Class.forName("us.hcheng.javaio.thread.classloader.chapter2.SimpleObject");
        System.out.println(klass.getClassLoader()); // AppClassLoader
        System.out.println(klass.getClassLoader().getParent()); // PlatformClassLoader
        System.out.println(klass.getClassLoader().getParent().getParent()); // null because of c++|c
    }

}
