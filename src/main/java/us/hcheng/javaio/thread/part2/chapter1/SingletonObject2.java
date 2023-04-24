package us.hcheng.javaio.thread.part2.chapter1;

public class SingletonObject2 {

    private static SingletonObject2 instance = null;

    private SingletonObject2(){}

    // not thread-safe
    public static SingletonObject2 instance() {
        if (instance == null)
            instance = new SingletonObject2();
        return instance;
    }

}
