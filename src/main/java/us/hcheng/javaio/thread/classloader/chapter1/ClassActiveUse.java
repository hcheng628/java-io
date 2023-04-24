package us.hcheng.javaio.thread.classloader.chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassActiveUse {

    static {
        System.err.println("ClassActiveUse init...");
    }

    public static void main(String[] args) throws ClassNotFoundException {

        /*
        // Read static value from Interface|Class
        System.out.println(MyInterface.value);
        System.out.println(MyClass.value);
        */


        // Set static value of a Class
        // MyClass.value = 10;

        // Invoke static method
        // MyClass.hey();

        // Reflection
        // Class<?> klass = Class.forName("us.hcheng.javaio.thread.classloader.chapter1.MyClass");

        // Parent class will be loaded when child is loaded
        // new Child();

        /*
        // This will not trigger parent and child to load
        System.out.println(Child.childValue);
        System.out.println(Child.parentValue);
        System.out.println(MyClass.parentValue);
         */

        /*

        // This will only trigger parent to load
        System.out.println(Child.value);
        Child.hey();
         */

        /*
        // This will not trigger class to load
        MyClass[] myClasses = new MyClass[5];
        List<MyClass> myClassList = new ArrayList<>();
        */

        // This will trigger the load
        System.out.println(Child.randValue);
    }

}

interface MyInterface {
    int value = 6;
}

class MyClass {
    public static int value = 16;
    public final static int parentValue = 66;

    public final static int randValue = new Random().nextInt(100);

    static {
        System.out.println("static block MyClass");
    }

    public static void hey() {
        System.out.println("hey");
    }
}

class Child extends MyClass {

    public final static int childValue = 33;

    static {
        System.out.println("static block Child");
    }

}