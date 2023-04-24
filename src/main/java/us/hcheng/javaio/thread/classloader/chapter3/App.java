package us.hcheng.javaio.thread.classloader.chapter3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String fileName = "SimpleObject";
        String fileDir = "/Users/nikkima/Desktop/tmp/us/hcheng/javaio/thread/classloader/chapter2/";
        String fileDir2 = "/Users/nikkima/Desktop/tmp/us/hcheng/javaio/thread/classloader/chapter22/";

        MyClassLoader parent = new MyClassLoader("Parent", fileDir);
        MyClassLoader child = new MyClassLoader("Child", fileDir2);


        Class klass = parent.loadClass(fileName);
        Object object = klass.getConstructor().newInstance();
        Method method = klass.getMethod("hello", null);
        method.invoke(object, null);

        System.out.println(klass.getClassLoader() + " === " +klass.getClassLoader().getName());
        System.out.println(klass.getClassLoader().getParent());

        Class childKlass = child.loadClass(fileName);
        System.out.println(childKlass.getClassLoader().getName() + " vs " + klass.getClassLoader().getName());
        System.out.println(childKlass.hashCode() + " vs " + klass.hashCode());
    }

}
