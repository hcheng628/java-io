package us.hcheng.javaio.thread.classloader.chapter1;

public class LoaderClass {

    public static void main(String[] args) {
        MyClass myClass1 = new MyClass();
        MyClass myClass2 = new MyClass();
        MyClass myClass3 = new MyClass();

        System.out.println(myClass1.getClass() == myClass2.getClass());
        System.out.println(myClass1.getClass() == myClass3.getClass());
    }


    static class User {
        public static int x = 0;

        static {
            x = 12;
            System.out.println(x);
            // System.out.println(noReadValue);
            noReadValue = 16;
        }

        public static int noReadValue = 10;

        public int value = 15;
    }

}


